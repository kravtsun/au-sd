#ifndef CLI_COMMAND_PARSER_TEST_H
#define CLI_COMMAND_PARSER_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_command_parser.h"
#include <string>
#include <sstream>
#include <cassert>

/**
 * @brief The CommandParserTest class testing suite
 * for CommandParser
 */
class CommandParserTest : public CxxTest::TestSuite
{
    /**
     * @brief single_test represent a skeleton for a test.
     * @param env environment.
     * @param string as if it was taken from user's input.
     * @param expected array of lexems already preprocessed
     * with environmental variable mechanisms.
     */
    void single_test(const cli::Environment &env, const std::string &s, const std::vector<std::string> &expected)
    {
        std::istringstream is(s);
        const cli::CommandPipe pipe = cli::CommandParser(env).parse_all_commands(is);
        TS_ASSERT_EQUALS(pipe.size(), 1);
        TS_ASSERT_EQUALS(pipe[0], expected);
    }

    /**
     * @brief multiple_test runs several single_test's
     * @param vars variables to be put in environment when
     * testing.
     * @param asks array of strings as it they are from user's input.
     * @param answers result of CommandParser.
     */
    void multiple_test(cli::Environment::VarListType &&vars,
                       std::vector<std::string> &&asks,
                       std::vector<cli::CommandPipeEntry> &&answers)
    {
        cli::Environment env(vars);
        assert(asks.size() == answers.size());
        for (size_t i = 0; i < asks.size(); ++i)
        {
            single_test(env, asks[i], answers[i]);
        }
    }

public:
    /**
     * @brief testEnvironmentVariables tests preprocessing environmental variables occurences
     * in user's input.
     */
    void testEnvironmentVariables()
    {
        TS_TRACE("Starting CommandParser::environmentVariables test.");

        cli::Environment::VarListType vars = {{"x", "1"}, {"y", "2"}, {"z", "ls -l"}};
        std::vector<std::string> asks = {"echo $x!=$y",
                                         "echo a$x$y$z",
                                         "$z",
                                         "\"$z\""
                                        };
        std::vector<cli::CommandPipeEntry> answers = {
            {"echo", "1!=2"},
            {"echo", "a12ls", "-l"},
            {"ls", "-l"},
            {"ls -l"}
        };
        multiple_test(std::move(vars), std::move(asks), std::move(answers));
        TS_TRACE("Finishing CommandParser::environmentVariables test.");
    }

    /**
     * @brief testQuotes checks if CommandParser treats quotes properly
     * (according to technical task).
     */
    void testQuotes()
    {
        TS_TRACE("Starting CommandParser::quotes test.");
        cli::Environment::VarListType vars = {{"x", "1"}, {"y", "2"}, {"z", "ls -l"}};

        std::vector<std::string> asks = {
            "\"   \"",
            "'x$y $z'"
        };

        std::vector<cli::CommandPipeEntry> answers = {
            {"   "},
            {"x$y $z"}
        };

        multiple_test(std::move(vars), std::move(asks), std::move(answers));
        TS_TRACE("Finishing CommandParser::quotes test.");
    }

    /**
     * @brief testLines test if some sentence was split by user on several lines.
     * but all these lines are to make one pipe sequence.
     */
    void testLines()
    {
#ifndef _MSC_VER
        TS_TRACE("Starting CommandParser::quotes test.");
        cli::Environment::VarListType vars = {{"x", "1"}, {"y", "2"}, {"z", "ls -l"}};

        std::vector<std::string> asks = {
            "\" \\\n  \"",
            "x$y\\\n2 $\\\nz"
        };

        std::vector<cli::CommandPipeEntry> answers = {
            {"   "},
            {"x", "ls", "-l"}
        };

        multiple_test(std::move(vars), std::move(asks), std::move(answers));

        TS_TRACE("Finishing CommandParser::quotes test.");
#endif
    }
};

#endif // CLI_COMMAND_PARSER_TEST_H

