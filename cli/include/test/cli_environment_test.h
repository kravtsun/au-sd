#pragma once
#ifndef CLI_ENVIRONMENT_TEST_H
#define CLI_ENVIRONMENT_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_environment.h"
#include <vector>
#include <string>
#include <cassert>

/**
 * @brief The EnvironmentTest class - testing suite
 * for Environment.
 */
class EnvironmentTest : public CxxTest::TestSuite
{
public:
    /**
     * @brief testIsVarAssignment tests is_var_assignment method.
     */
    void testIsVarAssignment()
    {
        const std::vector<std::string> asks = {
            "x = 1",
            "y123=1",
            "z=",
            "1=x"
        };

        const std::vector<bool> answers = {
            false,
            true,
            true,
            true
        };
        assert(asks.size() == answers.size());

        for (size_t i = 0; i < asks.size(); ++i)
        {
            TS_ASSERT_EQUALS(cli::Environment::is_var_assignment(asks[i]), answers[i]);
        }
    }

    /**
     * @brief testVarFlow tests assigning environmental variables.
     */
    void testVarFlow()
    {
        const std::vector<std::string> vars = {
            "y123",
            "z",
            "1"
        };

        const std::vector<std::string> asks = {
            "y123=1",
            "z=",
            "1=x"
        };

        const std::vector<std::string> answers = {
            "1",
            "",
            "x"
        };
        assert(vars.size() == asks.size() && asks.size() == answers.size());
        cli::Environment env;
        TS_ASSERT_EQUALS(env.get_vars().empty(), true);

        cli::Environment env2;
        for (size_t i = 0; i < asks.size(); ++i)
        {
            env.parse_and_assign(asks[i]);
            env2.set_var(vars[i], answers[i]);
            TS_ASSERT_EQUALS(env.get_var(vars[i]), answers[i]);
        }

        TS_ASSERT_EQUALS(env.get_vars().empty(), false);
        TS_ASSERT_EQUALS(env.get_vars(), env2.get_vars());
    }
};

#endif // CLI_ENVIRONMENT_TEST_H

