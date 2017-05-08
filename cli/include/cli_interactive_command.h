#ifndef CLI_INTERACTIVE_COMMAND_H
#define CLI_INTERACTIVE_COMMAND_H

#include "cli_command.h"

namespace cli {

/**
 * @brief The InteractiveCommand class
 * base class for commands working with files -
 * and only if not presented with them - turning into
 * interactive mode.
 * Template method pattern.
 */
class InteractiveCommand : public Command
{
public:
    /**
     * @brief InteractiveCommand constructor for command objects.
     * @param is input stream.
     * @param os output stream.
     * @param params command line parameters.
     */
    InteractiveCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief run entering point.
     * @param env environment.
     * @return return code.
     */
    int run(Environment &env) override;

    virtual std::string name() const = 0;

protected:
    std::vector<std::string> filenames_;

    virtual void init_run(const Environment &env) = 0;
    virtual void step(std::string &&line) = 0;
    virtual void end_file_step(const std::string &filename) = 0;
    virtual void end_run(Environment &env) = 0;
};

} // namespace cli

#endif // CLI_INTERACTIVE_COMMAND_H
