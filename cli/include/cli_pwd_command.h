#pragma once
#ifndef CLI_PWD_COMMAND_H
#define CLI_PWD_COMMAND_H

#include "cli_command.h"

class CLIPwdCommand : public CLICommand
{
public:
    /**
     * @brief CLIPwdCommand constructor
     * @param is input stream.
     * @param os output stream.
     * @param params command line parameters.
     */
    CLIPwdCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief run entry point for command.
     * @return return code.
     */
    int run(CLIEnvironment &env) override;

    /**
     * @brief name identifier for command.
     * @return name of command.
     */
    std::string name() const override;
};

#endif // CLI_PWD_COMMAND_H

