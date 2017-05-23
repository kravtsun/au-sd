#include "cli_word_count_command.h"
#include <cctype>
#include <iostream>

namespace cli {

WordCountCommand::WordCountCommand(std::istream &is, std::ostream &os, const Command::ParamsListType &params)
    : InteractiveCommand(is, os, params)
{}

std::string WordCountCommand::name() const
{
    return "wc";
}

void WordCountCommand::init_run(const Environment &env)
{
    (void)env;
    lines_total_count_ = 0;
    words_total_count_ = 0;
    chars_total_count_ = 0;

    lines_count_ = 0;
    words_count_ = 0;
    chars_count_ = 0;
}

static bool iswhitespace(char c)
{
    // additional checking in case of Unicode.
#ifdef _MSC_VER
    return (c >= -1 && c <= 255 && isblank(c));
#else
    return static_cast<bool>(isblank(c));
#endif
}

void WordCountCommand::step(std::string &&line)
{
    lines_count_++;
    chars_count_ += line.size();
    bool word_started = false;
    for (auto const &c : line)
    {
        if (word_started && iswhitespace(c))
        {
            word_started = false;
        }
        else if (!word_started && !iswhitespace(c))
        {
            words_count_++;
            word_started = true;
        }
    }
}

void WordCountCommand::end_file_step(const std::string &filename)
{
    chars_count_ += lines_count_;
    lines_total_count_ += lines_count_;
    words_total_count_ += words_count_;
    chars_total_count_ += chars_count_;

    print_characteristics(lines_count_, words_count_, chars_count_, filename);
    lines_count_ = 0;
    words_count_ = 0;
    chars_count_ = 0;
}

void WordCountCommand::end_run(Environment &env)
{
    (void)env;
    if (filenames_.empty())
    {
        print_characteristics(lines_count_, words_count_, chars_count_, "");
    }
    else if (filenames_.size() > 1)
    {
        print_characteristics(lines_total_count_ , words_total_count_, chars_total_count_, "total");
    }
}

void WordCountCommand::print_characteristics(size_t lines_count, size_t words_count, size_t chars_count, const std::string &label)
{
    os_ << lines_count << "\t" << words_count << "\t" << chars_count << "\t" << label << std::endl;
}

} // namespace cli
