#include "cli_cat_command.h"
#include <iostream>
#include <string>

namespace cli {

CatCommand::CatCommand(std::istream &is, std::ostream &os, const ParamsListType &params_)
    : InteractiveCommand(is, os, params_)
{}

std::string CatCommand::name() const
{
    return "cat";
}

void CatCommand::init_run(const Environment &env)
{
    (void) env;
}

void CatCommand::step(std::string &&line)
{
    os_ << line << std::endl;
}

void CatCommand::end_file_step(const std::string &filename)
{
    (void)filename;
}

void CatCommand::end_run(Environment &env)
{
    (void)env;
}

} // namespace cli
