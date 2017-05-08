#include "cli_command.h"

namespace cli {

Command::Command(std::istream &is, std::ostream &os, const Command::ParamsListType &params)
    : is_(is)
    , os_(os)
    , params_(params)
{}

} // namespace cli
