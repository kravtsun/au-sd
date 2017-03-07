#include "cli_exit_command.h"
#include "cli_exception.h"
#include "cli_parser.h"

namespace cli {

ExitCommand::ExitCommand(std::istream &is, std::ostream &os, const Command::ParamsListType &params)
    : Command(is, os, params)
{
    if (params.empty())
    {
        exit_code_ = 0;
    }
    else
    {
        try
        {
            exit_code_ = Parser(params.front()).parse_integer();
        }
        catch (ParseException &e)
        {
            std::string msg = "unable to parse into integer exit code: ";
            msg += e.what();
            throw CommandException("exit",  msg);
        }
    }
}

int ExitCommand::run(Environment &env)
{
    (void)env;
    throw ExitException(exit_code_);
}

std::string ExitCommand::name() const
{
    return "exit";
}

} // namespace cli
