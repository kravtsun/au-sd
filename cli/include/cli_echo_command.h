#pragma once
#ifndef CLI_ECHO_COMMAND_H
#define CLI_ECHO_COMMAND_H

#include "cli_command.h"

namespace cli {

/**
 * @brief The EchoCommand class
 * implements console command "cat"'s
 * functionality.
 */
class EchoCommand : public Command
{
public:
    /**
     * @brief EchoCommand - constructor (the same as for base class
     * Command).
     * @param is input stream.
     * @param os output stream.
     * @param params command line parameters.
     */
    EchoCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief run - command for starting.
     * @param env environment, can be used for meta-information
     * needed before start.
     * @return exit code.
     */
    int run(Environment &env) override;

    /**
     * @brief name - helper function.
     * @return
     */
    std::string name() const override;
};

} // namespace cli

#endif // CLI_ECHO_COMMAND_H
