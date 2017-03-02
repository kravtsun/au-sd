#ifndef CLI_EXCEPTION_H
#define CLI_EXCEPTION_H

#include <exception>
#include <string>

/**
 * @brief CLIException - stub base class for exceptions thrown in CLI project.
 */
class CLIException : public std::exception
{
public:
    CLIException();

    const char *what() const noexcept override;
};

class CLIExitException : public CLIException
{
public:
    CLIExitException(int exit_code=0)
        : exit_code_(exit_code)
    {}

    int exit_code() const
    {
        return exit_code_;
    }

private:
    int exit_code_;
    CLIExitException();
};

class CLIParseException : public CLIException
{
public:
    CLIParseException(const std::string &expr, const std::string &goal);

    const char *what() const noexcept override;

private:
    std::string expr_, goal_;
};

class CLINotImlementedException : public CLIException
{
public:
    CLINotImlementedException(const std::string &component);

    const char *what() const noexcept override;

private:
    std::string component_;
};

class CLICommandException : public CLIException
{
public:
    CLICommandException(const std::string &command, const std::string &msg);

    const char *what() const noexcept override;

private:
    std::string command_, msg_;
};


class CLIUnknownException : public CLIException
{};


void CLIAssert(bool condition);

#endif // CLI_EXCEPTION_H
