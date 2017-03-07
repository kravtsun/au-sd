#pragma once
#ifndef CLI_PWD_COMMAND_H
#define CLI_PWD_COMMAND_H

#include "cli_command.h"

namespace cli {

class PwdCommand : public Command
{
public:
    /**
     * @brief PwdCommand constructor
     * @param is input stream.
     * @param os output stream.
     * @param params command line parameters.
     */
    PwdCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief run entry point for command.
     * @return return code.
     */
    int run(Environment &env) override;

    /**
     * @brief name identifier for command.
     * @return name of command.
     */
    std::string name() const override;
};

} // namespace cli

#endif // CLI_PWD_COMMAND_H
