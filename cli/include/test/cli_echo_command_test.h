#pragma once
#ifndef CLI_ECHO_COMMAND_TEST_H
#define CLI_ECHO_COMMAND_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_echo_command.h"

#include <vector>
#include <string>
#include <sstream>

/**
 * @brief The EchoCommandTest class testing suite
 * for "echo" command.
 */
class EchoCommandTest : public CxxTest::TestSuite
{
    void single_test(const cli::Command::ParamsListType &params, const std::string &should_be)
    {
        std::istringstream is;
        std::ostringstream os;

        cli::EchoCommand echo_command(is, os, params);
        cli::Environment empty_env;
        echo_command.run(empty_env);
        TS_ASSERT_EQUALS(os.str(), should_be);
    }

public:
    /**
     * @brief testSingle simple test.
     */
    void testSingle()
    {
        single_test({"123"}, "123\n");
    }

    /**
     * @brief testMultiple testing when echo command
     * is dealing with several arguments.
     */
    void testMultiple()
    {
        single_test({"a   ", "b", "c" }, "a    b c\n");
    }
};


#endif // CLI_ECHO_COMMAND_TEST_H

