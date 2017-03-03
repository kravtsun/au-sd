#ifndef CLI_CAT_COMMAND_H
#define CLI_CAT_COMMAND_H

#include "cli_interactive_command.h"

/**
 * @brief The CLICatCommand class
 * implements "cat" bash command.
 * Provided with list of filenames tries to print contents of
 * each of them.
 * In other case it just echoes user's input (or the output received through pipe
 * from the previous command).
 */
class CLICatCommand : public CLIInteractiveCommand
{
public:
    /**
     * @brief CLICatCommand - the only constructor.
     * @param is - input stream.
     * @param os - output stream.
     * @param params_ list of parameters.
     */
    CLICatCommand(std::istream &is, std::ostream &os, const ParamsListType &params_);

    /**
     * @brief name: helper function in order to identify CLICommand object.
     * @return name of the command implemented.
     */
    std::string name() const override;

private:
    void init_run(const CLIEnvironment &env) override;
    void step(std::string &&line) override;
    void end_file_step(const std::string &filename) override;
    void end_run(CLIEnvironment &env) override;
};

#endif // CLI_CAT_COMMAND_H
