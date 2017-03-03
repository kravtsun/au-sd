#include "cli_word_count_command.h"
#include <cctype>
#include <iostream>

CLIWordCountCommand::CLIWordCountCommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : CLIInteractiveCommand(is, os, params)
{}

std::string CLIWordCountCommand::name() const
{
    return "wc";
}

void CLIWordCountCommand::init_run(const CLIEnvironment &env)
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
    return (c >= -1 && c <= 255 && isblank(c));
}

void CLIWordCountCommand::step(std::string &&line)
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

void CLIWordCountCommand::end_file_step(const std::string &filename)
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

void CLIWordCountCommand::end_run(CLIEnvironment &env)
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

void CLIWordCountCommand::print_characteristics(size_t lines_count, size_t words_count, size_t chars_count, const std::string &label)
{
    os_ << lines_count << "\t" << words_count << "\t" << chars_count << "\t" << label << std::endl;
}
