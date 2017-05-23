#include "cli_grep_command.h"
#include "cli_exception.h"

#ifdef __linux__
#pragma GCC diagnostic push
#pragma GCC diagnostic ignored "-Wunused-parameter"
#endif
#include "args.hxx"
#ifdef __linux__
#pragma GCC diagnostic pop
#endif
#include <regex>

namespace cli {

GrepCommand::GrepCommand(std::istream &is, std::ostream &os, const Command::ParamsListType &params)
    : InteractiveCommand(is, os, params)
{
    args::ArgumentParser parser("Grep command");
    args::HelpFlag help(parser, "help", "Show help", {'h', "help"});
    args::Flag case_insensitive_flag(parser, "case", "Case insensitive search.", {'i'});
    args::Flag words_only_flag(parser, "words", "Search words only.", {'w'});
    args::ValueFlag<unsigned> lines_after_flag(parser, "after_lines",
                                               "Suffix each line of output "
                                               "with the 1-based line number "
                                               "within its input file.", {'A'}, 0);
    args::Positional<std::string> pattern(parser, "pattern", "Pattern for matching: "
                                                             "regular expression in egrep format");
    args::PositionalList<std::string> files(parser, "files", "Files to search for a pattern");

    try {
        parser.ParseArgs(params);
    }
    catch (args::Help)
    {
        std::ostringstream os;
        parser.Help(os);
        throw FinishedCommandException(os.str(), true);
    }
    catch (args::Error & e)
    {
        throw CommandException(name(), e.what());
    }
    InteractiveCommand::set_filenames(files.Get());
    regex_params_.set_ignore_case(args::get(case_insensitive_flag));
    regex_params_.set_words_only(args::get(words_only_flag));
    regex_params_.set_pattern(args::get(pattern));
    regex_params_.build();

    state_.after_lines_ = lines_after_flag.Get();
}

std::string cli::GrepCommand::name() const
{
    return "grep";
}

void cli::GrepCommand::init_run(const cli::Environment &)
{
    state_.reset();
}

void cli::GrepCommand::step(std::string &&line)
{
    bool print = state_.lines_left_ > 0;
    state_.lines_left_ = std::max(state_.lines_left_ - 1, 0);
    if (std::regex_search(line, regex_params_.get_regex(), regex_params_.get_match_flags()))
    {
        state_.lines_left_ = state_.after_lines_;
        print = true;
    }
    if (print)
    {
        // probable bug in Windows and OSX environments?
        os_ << line << '\n';
    }
}

void cli::GrepCommand::end_file_step(const std::string &)
{
    init_run(Environment());
}

void cli::GrepCommand::end_run(cli::Environment &)
{
    os_ << std::flush;
}

GrepCommand::RegexParameters::RegexParameters()
    : match_flags_(std::regex_constants::match_default)
    , ignore_case_(false)
    , words_only_(false)
{}

void GrepCommand::RegexParameters::build()
{
    std::regex::flag_type regex_flags = default_regex_flags();
    if (ignore_case_)
    {
        regex_flags |= std::regex_constants::icase;
    }
    std::string regex_pattern = pattern_;
    if (words_only_)
    {
        regex_pattern = default_regex_word_beginning()
                + regex_pattern
                + default_regex_word_ending();
    }
    try {
        regex_ = std::regex{regex_pattern, regex_flags};
    }
    catch (std::exception &e) {
        throw CommandException("grep", "failed to create regex matcher");
    }
}

void GrepCommand::RegexParameters::set_ignore_case(bool ignore_case)
{
    ignore_case_ = ignore_case;
}

void GrepCommand::RegexParameters::set_words_only(bool words_only)
{
    words_only_ = words_only;
}

void GrepCommand::RegexParameters::set_pattern(const std::string &pattern)
{
    pattern_ = pattern;
}

std::regex_constants::match_flag_type GrepCommand::RegexParameters::get_match_flags() const
{
    return match_flags_;
}

std::regex GrepCommand::RegexParameters::get_regex() const
{
    return regex_;
}

} // namespace cli.
