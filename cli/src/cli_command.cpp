#include "cli_command.h"

namespace cli {

CLICommand::CLICommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : is_(is)
    , os_(os)
    , params_(params)
{}

} // namespace cli
