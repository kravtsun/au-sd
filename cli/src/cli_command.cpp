#include "cli_command.h"

CLICommand::CLICommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : is_(is)
    , os_(os)
    , params_(params)
{

}
