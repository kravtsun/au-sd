#ifndef CLI_COMMAND_QUEUE_TEST_H
#define CLI_COMMAND_QUEUE_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_command_queue.h"
#include <string>
#include <sstream>

class CLICommandQueueTest : public CxxTest::TestSuite
{
public:
    void testPipe(void)
    {
        CLICommandPipeEntry first_command = {"echo", "123"};
        CLICommandPipeEntry second_command = {"wc"};
        CLICommandPipe commands = {first_command, second_command};

        std::ostringstream os;
        CLIEnvironment empty_env;
        CLICommandQueue q(empty_env, std::move(commands), os);
        q.execute_pipe();
        std::istringstream is(os.str());
        std::vector<std::string> ans;
        std::string s;
        while (is >> s)
        {
            ans.push_back(s);
        }
        std::vector<std::string> shouldbe = {"1", "1", "3"};
        TS_ASSERT_EQUALS(ans, shouldbe);
    }
};

#endif // CLI_COMMAND_QUEUE_TEST_H

