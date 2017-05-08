#include "cli_unknown_command.h"
#include "cli_exception.h"
#include <cstdlib>

namespace cli {

UnknownCommand::UnknownCommand(std::istream &is, std::ostream &os, const Command::ParamsListType &params)
    : Command(is, os, params)
{}

int UnknownCommand::run(Environment &env)
{
    (void)env;
    std::string call_string = "";

    for (auto const &s : params_)
    {
        call_string += s + ' ';
    }
    return system(call_string.c_str());
}

std::string UnknownCommand::name() const
{
    throw NotImlementedException("name() for subprocess Command.");
}

} // namespace cli
