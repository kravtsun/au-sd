#ifndef CLI_INTERACTIVE_COMMAND_H
#define CLI_INTERACTIVE_COMMAND_H

#include "cli_command.h"

/**
 * @brief The CLIInteractiveCommand class
 * base class for commands working with files -
 * and only if not presented with them - turning into
 * interactive mode.
 * Template pattern.
 */
class CLIInteractiveCommand : public CLICommand
{
public:
    CLIInteractiveCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    int run(CLIEnvironment &env) override;

    virtual std::string name() const = 0;

protected:
    std::vector<std::string> filenames_;

    virtual void init_run(const CLIEnvironment &env) = 0;
    virtual void step(std::string &&line) = 0;
    virtual void end_file_step(const std::string &filename) = 0;
    virtual void end_run(CLIEnvironment &env) = 0;
};

#endif // CLI_INTERACTIVE_COMMAND_H
