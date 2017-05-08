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
    : expr_(expr)
    , goal_(goal)
{
    base_msg_ = ("ParseException: Failed to parse: \"") +
            expr_ + ("\" into ") + goal_;
}

NotImlementedException::NotImlementedException(const std::string &component)
    : component_(component)
{
    base_msg_ = "NotImlementedException: " + component_;
}

CommandException::CommandException(const std::string &command, const std::string &msg)
    : command_(command)
    , msg_(msg)
{
    base_msg_ = command_ + ": " + msg_;
}

UnknownError::UnknownError(std::string &&msg)
    : msg_(msg)
{
    base_msg_ = "UnknownError: " + msg_;
}

} // namespace cli
