#ifndef CLI_WORD_COUNT_COMMAND_H
#define CLI_WORD_COUNT_COMMAND_H

#include "cli_interactive_command.h"

class CLIWordCountCommand : public CLIInteractiveCommand
{
public:
    CLIWordCountCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    std::string name() const override;

private:
    size_t lines_count_;
    size_t words_count_;
    size_t chars_count_;

    size_t lines_total_count_;
    size_t words_total_count_;
    size_t chars_total_count_;

    virtual void init_run(const CLIEnvironment &env) override;
    virtual void step(std::string &&line) override;
    virtual void end_file_step(const std::string &filename) override;
    virtual void end_run(CLIEnvironment &env) override;

    void print_characteristics(size_t lines_count, size_t words_count, size_t chars_count, const std::string &label);
};

#endif // CLI_WORD_COUNT_COMMAND_H
