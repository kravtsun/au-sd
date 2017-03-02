#ifndef CLI_CAT_COMMAND_H
#define CLI_CAT_COMMAND_H

#include "cli_interactive_command.h"

class CLICatCommand : public CLIInteractiveCommand
{
public:
    CLICatCommand(std::istream &is, std::ostream &os, const ParamsListType &params_);

    std::string name() const override;

private:
    void init_run(const CLIEnvironment &env) override;
    void step(std::string &&line) override;
    void end_file_step(const std::string &filename) override;
    void end_run(CLIEnvironment &env) override;
};

#endif // CLI_CAT_COMMAND_H
