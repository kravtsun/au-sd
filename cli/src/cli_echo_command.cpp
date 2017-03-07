#include "cli_echo_command.h"

namespace cli {

EchoCommand::EchoCommand(std::istream &is, std::ostream &os, const Command::ParamsListType &params)
    : Command(is, os, params)
{}

int EchoCommand::run(Environment &env)
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

std::string EchoCommand::name() const
{
    return "echo";
}

}
