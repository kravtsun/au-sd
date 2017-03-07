#pragma once
#ifndef CLI_CAT_COMMAND_H
#define CLI_CAT_COMMAND_H

#include "cli_interactive_command.h"

namespace cli {

/**
 * @brief The CatCommand class
 * implements "cat" bash command.
 * Provided with list of filenames tries to print contents of
 * each of them.
 * In other case it just echoes user's input (or the output received through pipe
 * from the previous command).
 */
class CatCommand : public InteractiveCommand
{
public:
    /**
     * @brief CatCommand - the only constructor.
     * @param is - input stream.
     * @param os - output stream.
     * @param params_ list of parameters.
     */
    CatCommand(std::istream &is, std::ostream &os, const ParamsListType &params_);

    /**
     * @brief name: helper function in order to identify Command object.
     * @return name of the command implemented.
     */
    std::string name() const override;

private:
    void init_run(const Environment &env) override;
    void step(std::string &&line) override;
    void end_file_step(const std::string &filename) override;
    void end_run(Environment &env) override;
};

} // namespace cli

#endif // CLI_CAT_COMMAND_H
