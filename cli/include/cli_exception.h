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


#endif // CLI_EXCEPTION_H
