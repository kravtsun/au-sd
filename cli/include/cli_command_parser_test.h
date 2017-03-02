#ifndef CLI_COMMAND_PARSER_TEST_H
#define CLI_COMMAND_PARSER_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_command_parser.h"
#include <string>
#include <sstream>
#include <cassert>


class CLICommandParserTest : public CxxTest::TestSuite
{
    void single_test(const CLIEnvironment &env, const std::string &s, const std::vector<std::string> &expected)
    {
        std::istringstream is(s);
        const CLICommandPipe pipe = CLICommandParser(env).parse_all_commands(is);
        TS_ASSERT_EQUALS(pipe.size(), 1);
        TS_ASSERT_EQUALS(pipe[0], expected);
    }

    void multiple_test(CLIEnvironment::VarListType &&vars,
                       std::vector<std::string> &&asks,
                       std::vector<CLICommandPipeEntry> &&answers)
    {
        CLIEnvironment env(vars);
        assert(asks.size() == answers.size());
        for (size_t i = 0; i < asks.size(); ++i)
        {
//            std::cout << i << std::endl;
            single_test(env, asks[i], answers[i]);
        }
    }

public:
    void testEnvironmentVariables()
    {
        TS_TRACE("Starting CLICommandParser::environmentVariables test.");

        CLIEnvironment::VarListType vars = {{"x", "1"}, {"y", "2"}, {"z", "ls -l"}};
        std::vector<std::string> asks = {"echo $x!=$y",
                                         "echo a$x$y$z",
                                         "$z",
                                         "\"$z\""
                                        };
        std::vector<CLICommandPipeEntry> answers = {
            {"echo", "1!=2"},
            {"echo", "a12ls", "-l"},
            {"ls", "-l"},
            {"ls -l"}
        };
        multiple_test(std::move(vars), std::move(asks), std::move(answers));
        TS_TRACE("Finishing CLICommandParser::environmentVariables test.");
    }

    void testQuotes()
    {
        TS_TRACE("Starting CLICommandParser::quotes test.");
        CLIEnvironment::VarListType vars = {{"x", "1"}, {"y", "2"}, {"z", "ls -l"}};

        std::vector<std::string> asks = {
            "\"   \"",
            "'x$y $z'"
        };

        std::vector<CLICommandPipeEntry> answers = {
            {"   "},
            {"x$y $z"}
        };

        multiple_test(std::move(vars), std::move(asks), std::move(answers));
        TS_TRACE("Finishing CLICommandParser::quotes test.");
    }

    void testLines()
    {
        TS_TRACE("Starting CLICommandParser::quotes test.");
        CLIEnvironment::VarListType vars = {{"x", "1"}, {"y", "2"}, {"z", "ls -l"}};

        std::vector<std::string> asks = {
            "\" \\\n  \"",
            "x$y\\\n2 $\\\nz"
        };

        std::vector<CLICommandPipeEntry> answers = {
            {"   "},
            {"x", "ls", "-l"}
        };

        multiple_test(std::move(vars), std::move(asks), std::move(answers));

        TS_TRACE("Finishing CLICommandParser::quotes test.");
    }
};



#endif // CLI_COMMAND_PARSER_TEST_H

