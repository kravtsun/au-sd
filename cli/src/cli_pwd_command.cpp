#include "cli_pwd_command.h"
#include "cli_exception.h"
#include <unistd.h>


CLIPwdCommand::CLIPwdCommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : CLICommand(is, os, params)
{}

int CLIPwdCommand::run(CLIEnvironment &env)
{
    (void)env;
//    os_ << getenv("PWD") << std::endl;
    char *str = get_current_dir_name();
    if (str)
    {
        os_ << str << std::endl;
    }
    else
    {
        throw CLIUnknownError("failed to determine current directory.");
    }
    return 0;
}

std::string CLIPwdCommand::name() const
{
    return "pwd";
}
