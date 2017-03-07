#pragma once
#ifndef CLI_COMMAND_QUEUE_TEST_H
#define CLI_COMMAND_QUEUE_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_command_queue.h"
#include <string>
#include <sstream>

/**
 * @brief The CommandQueueTest class
 * testing suite for CommandQueue.
 */
class CommandQueueTest : public CxxTest::TestSuite
{
public:
    /**
     * @brief testPipe general test for making sure
     * several commands in a pipe will share their
     * input and output streams on a chain.
     */
    void testPipe(void)
    {
        cli::CommandPipeEntry first_command = {"echo", "123"};
        cli::CommandPipeEntry second_command = {"wc"};
        cli::CommandPipe commands = {first_command, second_command};

        std::ostringstream os;
        cli::Environment empty_env;
        cli::CommandQueue q(empty_env, std::move(commands), os);
        q.execute_pipe();
        std::istringstream is(os.str());
        std::vector<std::string> ans;
        std::string s;
        while (is >> s)
        {
            ans.push_back(s);
        }
        std::vector<std::string> should_be = {"1", "1", "3"};
        TS_ASSERT_EQUALS(ans, should_be);
    }
};

#endif // CLI_COMMAND_QUEUE_TEST_H

