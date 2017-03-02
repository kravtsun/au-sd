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
protected:
    std::string base_msg_;
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

private:
    std::string expr_, goal_;
};

class CLINotImlementedException : public CLIException
{
public:
    CLINotImlementedException(const std::string &component);

private:
    std::string component_;
};

class CLICommandException : public CLIException
{
public:
    CLICommandException(const std::string &command, const std::string &msg);

private:
    std::string command_, msg_;
};


class CLIUnknownError : public CLIException
{
public:
    CLIUnknownError(std::string &&msg);

private:
    std::string msg_;
};


#endif // CLI_EXCEPTION_H
