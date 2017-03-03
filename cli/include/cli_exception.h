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

#ifdef _MSC_VER
	const char *what() const override;
#else
    const char *what() const noexcept override;
#endif
protected:
    std::string base_msg_;
};

/**
 * @brief The CLIExitException class generated when
 * "exit" command. Apart from this exception there's no way
 * to break through the main loop (not mentioning hard tools like terminal signals).
 */
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

/**
 * @brief The CLIParseException class exception thrown on
 * bad input when other is expected.
 */
class CLIParseException : public CLIException
{
public:
    CLIParseException(const std::string &expr, const std::string &goal);

private:
    std::string expr_, goal_;
};

/**
 * @brief The CLINotImlementedException class mark code-flow places,
 * when functionality is not implemented yet.
 */
class CLINotImlementedException : public CLIException
{
public:
    CLINotImlementedException(const std::string &component);

private:
    std::string component_;
};

/**
 * @brief The CLICommandException class represents some complaint
 * for the command - an atomic worker on some condition which
 * makes it impossible to continue its job.
 */
class CLICommandException : public CLIException
{
public:
    CLICommandException(const std::string &command, const std::string &msg);

private:
    std::string command_, msg_;
};

/**
 * @brief The CLIUnknownError class represents unclassified or not implemented yet
 * types of errors.
 */
class CLIUnknownError : public CLIException
{
public:
    CLIUnknownError(std::string &&msg);

private:
    std::string msg_;
};


#endif // CLI_EXCEPTION_H
