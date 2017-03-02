#ifndef CLI_CAT_COMMAND_H
#define CLI_CAT_COMMAND_H

#include "cli_command.h"

class CLICatCommand : public CLICommand
{
public:
    CLICatCommand(std::istream &is, std::ostream &os, const ParamsListType &params_);

    std::string name() const override;

    int run(CLIEnvironment &env) override;

private:
    std::vector<std::string> filenames_;
};

#endif // CLI_CAT_COMMAND_H
