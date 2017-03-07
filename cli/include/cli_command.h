#pragma once
#ifndef CLI_COMMAND_H
#define CLI_COMMAND_H

#include "cli_environment.h"
#include <vector>
#include <string>
#include <sstream>

namespace cli {

/**
 * @brief Base class for all commands.
 * reflects general behavior and interface to the atomic worker
 * in our project.
 */
class CLICommand
{
public:
    /**
     * @brief ParamsListType just an alias for
     * structure, storing information about parameters of
     * a console command.
     */
    typedef std::vector<std::string> ParamsListType;

    /**
     * @brief CLICommand - the only constructor.
     */
    CLICommand(std::istream &is, std::ostream &os, const ParamsListType &params = {});

    /**
     * @brief run a command's 'play' button.
     * starts all the useful activity of the atomic worker.
     * @param env environment variables. Can be used for
     * transcending some meta-information which came to light just before
     * running the command.
     * @return return code.
     */
    virtual int run(CLIEnvironment &env) = 0;

    /**
     * @brief name: helper function in order to identify CLICommand object.
     * @return name of the command implemented.
     */
    virtual std::string name() const = 0;

    /**
     * @brief ~CLICommand destructor.
     */
    virtual ~CLICommand() {}

protected:
    std::istream &is_;
    std::ostream &os_;
    ParamsListType params_;
};

} // namespace cli

#endif // CLI_COMMAND_H
