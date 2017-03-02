#ifndef CLI_UNKNOWN_COMMAND_H
#define CLI_UNKNOWN_COMMAND_H

#include "cli_command.h"

class CLIUnknownCommand : public CLICommand
{
public:
    CLIUnknownCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    int run(CLIEnvironment &env) override;

    std::string name() const override;

private:
};

#endif // CLI_UNKNOWN_COMMAND_H
