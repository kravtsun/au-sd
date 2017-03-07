#pragma once
#ifndef CLI_WORD_COUNT_COMMAND_H
#define CLI_WORD_COUNT_COMMAND_H

#include "cli_interactive_command.h"

namespace cli {

/**
 * @brief The WordCountCommand class command object
 * which counts number of lines, words and characters
 * in input stream (if no arguments specified) or for
 * any file in a parameters' list.
 */
class WordCountCommand : public InteractiveCommand
{
public:
    /**
     * @brief WordCountCommand usual command object constructor.
     * @param is input stream.
     * @param os output stream.
     * @param params command line parameters.
     */
    WordCountCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief name helper method for identifying command object.
     * @return name of command (like as if it was run in usual Bash).
     */
    std::string name() const override;

private:
    size_t lines_count_;
    size_t words_count_;
    size_t chars_count_;

    size_t lines_total_count_;
    size_t words_total_count_;
    size_t chars_total_count_;

    virtual void init_run(const Environment &env) override;
    virtual void step(std::string &&line) override;
    virtual void end_file_step(const std::string &filename) override;
    virtual void end_run(Environment &env) override;

    void print_characteristics(size_t lines_count, size_t words_count, size_t chars_count, const std::string &label);
};

} // namespace cli

#endif // CLI_WORD_COUNT_COMMAND_H
