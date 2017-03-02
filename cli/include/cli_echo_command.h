#ifndef CLIECHOCOMMAND_H
#define CLIECHOCOMMAND_H

#include "cli_command.h"

class CLIEchoCommand : public CLICommand
{
public:
    CLIEchoCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    int run(CLIEnvironment &env) override;

    std::string name() const override;
};

#endif // CLIECHOCOMMAND_H
