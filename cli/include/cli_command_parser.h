#ifndef CLI_COMMAND_PARSER_H
#define CLI_COMMAND_PARSER_H

#include "include/cli_environment.h"
#include "include/cli_command_pipe.h"
#include <vector>
#include <istream>

/**
 * @brief The CLICommandParser
 * happens to be a lexer too...
 */
class CLICommandParser
{
public:
    CLICommandParser(const CLIEnvironment &env, std::istream &is);

    CLICommandPipe parse_all();

private:
    const CLIEnvironment &env_;
    std::istream &is_;


//    // kind of Finite Discrete Automata's state.
//    class CLICommandParserState
//    {
//        enum struct LexerState
//        {
//            FREE,
//            SINGLE_QUOTES,
//            DOUBLE_QUOTES,
//            NEXT_STRING
//        };

//    public:
//        CLICommandParserState()
//            : lexer_state_(LexerState::FREE)
//            , is_var_open_(false)
//        {}


//        std::string get_result();
//    private:
//        LexerState lexer_state_;

//        bool is_var_open_;
//    };

    std::string get_all_input();
};

#endif // CLI_COMMAND_PARSER_H
