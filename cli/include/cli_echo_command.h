#ifndef CLIECHOCOMMAND_H
#define CLIECHOCOMMAND_H

#include "cli_command.h"

/**
 * @brief The CLIEchoCommand class
 * implements console command "cat"'s
 * functionality.
 */
class CLIEchoCommand : public CLICommand
{
public:
    /**
     * @brief CLIEchoCommand - constructor (the same as for base class
     * CLICommand).
     * @param is input stream.
     * @param os output stream.
     * @param params command line parameters.
     */
    CLIEchoCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief run - command for starting.
     * @param env environment, can be used for meta-information
     * needed before start.
     * @return exit code.
     */
    int run(CLIEnvironment &env) override;

    /**
     * @brief name - helper function.
     * @return
     */
    std::string name() const override;
};

#endif // CLIECHOCOMMAND_H
