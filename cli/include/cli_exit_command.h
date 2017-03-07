#pragma once
#ifndef CLI_EXIT_COMMAND_H
#define CLI_EXIT_COMMAND_H

#include "cli_command.h"

namespace cli {

/**
 * @brief The ExitCommand class exit command.
 * stops execution of the whole application on user's demand.
 */
class ExitCommand : public Command
{
public:
    /**
     * @brief ExitCommand constructor
     * @param is input stream.
     * @param os output stream.
     * @param params command line parameters.
     */
    ExitCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief run entering point for the command.
     * @param env environment.
     * @return exit code (bash like).
     */
    int run(Environment &env) override;

    /**
     * @brief name helper function helps identify the type of command object.
     * @return name of the command object.
     */
    std::string name() const override;
private:
    int exit_code_;
};

} // namespace cli

#endif // CLI_EXIT_COMMAND_H
