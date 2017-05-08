#ifndef CLI_COMMAND_PARSER_H
#define CLI_COMMAND_PARSER_H

#include "cli_environment.h"
#include "cli_command_pipe.h"
#include <vector>
#include <istream>

namespace cli {

/**
 * @brief The CommandParser
 * transforms all the user's input into sequence
 * of commands and their arguments
 * happens to be a lexer too as it works as a preprocessor
 * make substitution for environmental variables in input.
 */
class CommandParser
{
public:
    /**
     * @brief CommandParser constructor.
     * @param env information around the command parser
     * and all meta-information needed for initialization.
     */
    explicit CommandParser(const Environment &env);

    /**
     * @brief parse_all_commands - "red button" for the command parser,
     * the order to do its work.
     * @param is_ input stream.
     * @return the pipes - sequence of commands which will share user's input
     * on current main loop's iteration.
     */
    CommandPipe parse_all_commands(std::istream &is);

private:
    const Environment &env_;
};

} // namespace cli

#endif // CLI_COMMAND_PARSER_H
