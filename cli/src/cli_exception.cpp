#include "cli_exception.h"

CLIException::CLIException()
    : base_msg_("")
{}

#ifdef _MSC_VER
const char *CLIException::what() const
#else
const char *CLIException::what() const noexcept
#endif
{
    return base_msg_.c_str();
}

CLIExitException::CLIExitException(int exit_code)
    : exit_code_(exit_code)
{}

CLIParseException::CLIParseException(const std::string &expr, const std::string &goal)
    : expr_(expr)
    , goal_(goal)
{
    base_msg_ = ("CLIParseException: Failed to parse: \"") +
            expr_ + ("\" into ") + goal_;
}

CLINotImlementedException::CLINotImlementedException(const std::string &component)
    : component_(component)
{
    base_msg_ = "CLINotImlementedException: " + component_;
}

CLICommandException::CLICommandException(const std::string &command, const std::string &msg)
    : command_(command)
    , msg_(msg)
{
    base_msg_ = command_ + ": " + msg_;
}

CLIUnknownError::CLIUnknownError(std::string &&msg)
    : msg_(msg)
{
    base_msg_ = "CLIUnknownError: " + msg_;
}
