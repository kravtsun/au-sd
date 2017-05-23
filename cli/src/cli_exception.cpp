#include "cli_exception.h"

namespace cli {

Exception::Exception()
    : base_msg_("")
{}

#ifdef _MSC_VER
const char *Exception::what() const
#else
const char *Exception::what() const noexcept
#endif
{
    return base_msg_.c_str();
}

ExitException::ExitException(int exit_code)
    : exit_code_(exit_code)
{}

ParseException::ParseException(const std::string &expr, const std::string &goal)
{
    base_msg_ = ("ParseException: Failed to parse: \"") +
            expr + ("\" into ") + goal;
}

NotImlementedException::NotImlementedException(const std::string &component)
{
    base_msg_ = "NotImlementedException: " + component;
}

CommandException::CommandException(const std::string &command, const std::string &msg)
{
    base_msg_ = command + ": " + msg;
}

IOError::IOError(const std::string &msg)
{
    base_msg_ = "IOError: " + msg;
}

UnknownError::UnknownError(const std::string &msg)
{
    base_msg_ = "UnknownError: " + msg;
}

} // namespace cli
