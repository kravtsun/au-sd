#ifndef CLI_EXCEPTION_H
#define CLI_EXCEPTION_H

#include <exception>
#include <string>

namespace cli {

/**
 * @brief Exception - stub base class for exceptions thrown in CLI project.
 */
class Exception : public std::exception
{
public:

#ifdef _MSC_VER
    const char *what() const override;
#else
    const char *what() const noexcept override;
#endif
protected:
    Exception();
    std::string base_msg_;
};

/**
 * @brief The ExitException class generated when
 * "exit" command. Apart from this exception there's no way
 * to break through the main loop (not mentioning hard tools like terminal signals).
 */
class ExitException : public Exception
{
public:
    explicit ExitException(int exit_code);

    int exit_code() const
    {
        return exit_code_;
    }

private:
    int exit_code_;
};

/**
 * @brief The ParseException class exception thrown on
 * bad input when other is expected.
 */
class ParseException : public Exception
{
public:
    ParseException(const std::string &expr, const std::string &goal);
};

/**
 * @brief The NotImlementedException class mark code-flow places,
 * when functionality is not implemented yet.
 */
class NotImlementedException : public Exception
{
public:
    explicit NotImlementedException(const std::string &component);
};

/**
 * @brief The CommandException class represents some complaint
 * for the command - an atomic worker on some condition which
 * makes it impossible to continue its job.
 */
class CommandException : public Exception
{
public:
    CommandException(const std::string &command, const std::string &msg);
};

class IOError : public Exception
{
public:
    explicit IOError(const std::string &msg);
};

/**
 * @brief The UnknownError class represents unclassified or not implemented yet
 * types of errors.
 */
class UnknownError : public Exception
{
public:
    explicit UnknownError(const std::string &msg);
};

} // namespace cli

#endif // CLI_EXCEPTION_H
