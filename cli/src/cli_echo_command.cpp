#include "include/cli_echo_command.h"

CLIEchoCommand::CLIEchoCommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : CLICommand(is, os, params)
{}

int CLIEchoCommand::run(CLIEnvironment &env)
{
    (void)env;
    for (auto it = params_.cbegin(); it != params_.cend(); ++it)
    {
        os_ << *it;
        if (it + 1 != params_.cend())
        {
            os_ << ' ';
        }
    }
    os_ << std::endl;
    return 0;
}

std::string CLIEchoCommand::name() const
{
    return "echo";
}
