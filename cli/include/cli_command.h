#ifndef CLI_COMMAND_H
#define CLI_COMMAND_H

#include <vector>
#include <string>
#include <sstream>

#include "cli_environment.h"


/**
 * @brief Base class for any command executed.
 */
class CLICommand
{
protected:
    typedef std::vector<std::string> ParamsListType;
public:
    CLICommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    virtual int run(CLIEnvironment &env) = 0;

    virtual std::string name() const = 0;

protected:
    CLICommand();
    std::istream &is_;
    std::ostream &os_;
    ParamsListType params_;

private:
};

#endif // CLI_COMMAND_H
