#ifndef CLI_UNKNOWN_COMMAND_H
#define CLI_UNKNOWN_COMMAND_H

#include "cli_command.h"

/**
 * @brief The CLIUnknownCommand class command
 * for delegating unknown command's execution to the terminal
 * from which CLI app was called.
 */
class CLIUnknownCommand : public CLICommand
{
public:
    /**
     * @brief CLIUnknownCommand usual constructor for command object.
     * @param is input stream.
     * @param os output stream.
     * @param params command line parameters.
     */
    CLIUnknownCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief run entry point.
     * @param env environment.
     * @return exit code.
     */
    int run(CLIEnvironment &env) override;

    /**
     * @brief name helper method for identifying command objects.
     * @return name of the command mimicked.
     */
    std::string name() const override;
};

#endif // CLI_UNKNOWN_COMMAND_H
