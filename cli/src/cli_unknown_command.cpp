#include "cli_unknown_command.h"
#include "cli_exception.h"
#include <cstdlib>

namespace cli {

CLIUnknownCommand::CLIUnknownCommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : CLICommand(is, os, params)
{}

int CLIUnknownCommand::run(CLIEnvironment &env)
{
    (void)env;
    std::string call_string = "";

    for (auto const &s : params_)
    {
        call_string += s + ' ';
    }
    return system(call_string.c_str());
}

std::string CLIUnknownCommand::name() const
{
    throw CLINotImlementedException("name() for subprocess CLICommand.");
}

} // namespace cli
