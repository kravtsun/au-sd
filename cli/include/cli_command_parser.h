#ifndef CLI_COMMAND_PARSER_H
#define CLI_COMMAND_PARSER_H

#include "cli_environment.h"
#include "cli_command_pipe.h"
#include <vector>
#include <istream>

/**
 * @brief The CLICommandParser
 * transforms all the user's input into sequence
 * of commands and their arguments
 * happens to be a lexer too as it works as a preprocessor
 * make substitution for environmental variables in input.
 */
class CLICommandParser
{
public:
    /**
     * @brief CLICommandParser constructor.
     * @param env information around the command parser
     * and all meta-information needed for initialization.
     */
    CLICommandParser(const CLIEnvironment &env);

    /**
     * @brief parse_all_commands - "red button" for the command parser,
     * the order to do its work.
     * @param is_ input stream.
     * @return the pipes - sequence of commands which will share user's input
     * on current main loop's iteration.
     */
    CLICommandPipe parse_all_commands(std::istream &is_);

private:
    const CLIEnvironment &env_;
};

#endif // CLI_COMMAND_PARSER_H
