#ifndef CLI_COMMAND_PARSER_H
#define CLI_COMMAND_PARSER_H

#include "cli_environment.h"
#include "cli_command_pipe.h"
#include <vector>
#include <istream>

/**
 * @brief The CLICommandParser
 * happens to be a lexer too...
 */
class CLICommandParser
{
public:
    CLICommandParser(const CLIEnvironment &env);

    CLICommandPipe parse_all_commands(std::istream &is_);

private:
    const CLIEnvironment &env_;
};

#endif // CLI_COMMAND_PARSER_H
