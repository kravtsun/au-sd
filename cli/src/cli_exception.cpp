#include "cli_exception.h"
#include <cstring>

CLIException::CLIException()
{}


const char *CLIException::what() const noexcept
{
    std::string msg = "CLIException: ";
    msg += std::exception::what();
    return msg.c_str();
}

CLIParseException::CLIParseException(const std::string &expr, const std::string &goal)
    : expr_(expr)
    , goal_(goal)
{}

const char *CLIParseException::what() const noexcept
{
    const std::string msg = ("CLIParseException: Failed to parse: \"") +
                            expr_ + ("\" into ") + goal_;
    return msg.c_str();
}

CLINotImlementedException::CLINotImlementedException(const std::string &component)
    : component_(component)
{}

const char *CLINotImlementedException::what() const noexcept
{
    const std::string msg = "CLINotImlementedException: " + component_;
    return msg.c_str();
}

CLICommandException::CLICommandException(const std::string &command, const std::string &msg)
    : command_(command)
    , msg_(msg)
{}

const char *CLICommandException::what() const noexcept
{
    const std::string msg = command_ + ": " + msg_;
    return msg.c_str();
}

void CLIAssert(bool condition)
{
    if (!condition)
    {
        throw CLIUnknownException();
    }
}
