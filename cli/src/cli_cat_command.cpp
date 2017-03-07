#include "cli_cat_command.h"
#include <iostream>
#include <string>

namespace cli {

CLICatCommand::CLICatCommand(std::istream &is, std::ostream &os, const ParamsListType &params_)
    : CLIInteractiveCommand(is, os, params_)
{}

std::string CLICatCommand::name() const
{
    return "cat";
}

void CLICatCommand::init_run(const CLIEnvironment &env)
{
    (void) env;
}

void CLICatCommand::step(std::string &&line)
{
    os_ << line << std::endl;
}

void CLICatCommand::end_file_step(const std::string &filename)
{
    (void)filename;
}

void CLICatCommand::end_run(CLIEnvironment &env)
{
    (void)env;
}

} // namespace cli
