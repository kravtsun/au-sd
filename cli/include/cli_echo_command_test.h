#ifndef CLI_ECHO_COMMAND_TEST_H
#define CLI_ECHO_COMMAND_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_echo_command.h"

#include <vector>
#include <string>
#include <sstream>

class CLIEchoCommandTest : public CxxTest::TestSuite
{
    void single_test(const CLICommand::ParamsListType &params, const std::string &shouldbe)
    {
        std::istringstream is;
        std::ostringstream os;

        CLIEchoCommand echo_command(is, os, params);
        CLIEnvironment empty_env;
        echo_command.run(empty_env);
        TS_ASSERT_EQUALS(os.str(), shouldbe);
    }

public:
    void testSingle()
    {
        single_test({"123"}, "123\n");
    }

    void testMultiple()
    {
        single_test({"a   ", "b", "c" }, "a    b c\n");
    }
};


#endif // CLI_ECHO_COMMAND_TEST_H

