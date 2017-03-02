#ifndef CLI_EXIT_COMMAND_H
#define CLI_EXIT_COMMAND_H

#include "cli_command.h"

class CLIExitCommand : public CLICommand
{
public:
    CLIExitCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    int run(CLIEnvironment &env) override;

    std::string name() const override
    {
        return "exit";
    }
private:
    int exit_code_;
};

#endif // CLI_EXIT_COMMAND_H
