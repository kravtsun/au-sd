#include "cli_exit_command.h"
#include "cli_exception.h"
#include "cli_parser.h"

CLIExitCommand::CLIExitCommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : CLICommand(is, os, params)
{
    if (params.empty())
    {
        exit_code_ = 0;
    }
    else
    {
        try
        {
            exit_code_ = CLIParser(params.front()).parse_integer();
        }
        catch (CLIParseException &e)
        {
            throw CLICommandException("exit", "unable to parse into integer exit code: " + params.front());
        }
    }
}


int CLIExitCommand::run(CLIEnvironment &env)
{
    (void)env;
    throw CLIExitException(exit_code_);
}
