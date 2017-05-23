#ifndef CLI_GREP_COMMAND_TEST_H
#define CLI_GREP_COMMAND_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_grep_command.h"
#include "cli_exception.h"
#include <vector>
#include <string>
#include <fstream>
#include <sstream>

/**
 * @brief The CLICatCommandTest class
 * testing suite for CatCommand methods and logic
 * behind them.
 */
class CLIGrepCommandTest : public CxxTest::TestSuite
{
public:
    CLIGrepCommandTest() {}

    /**
     * @brief testSimple test working with one and only file
     * specified.
     */
    void testSimple()
    {
        cli::GrepCommand grep_command(is, os, {pattern, filename});
        grep_command.run(empty_env_);

        const std::string should_be = "        ofSourceSet(\'main\')*.plugins {\n"
                                            "            // NamedDomainObjectContainer "
                                            "binds the methods.\n";
        TS_ASSERT_EQUALS(os.str(), should_be);
    }

    /**
     * @brief testWords test working with one and only file
     * looking for words-alike matched sequences.
     */
    void testWords()
    {
        cli::GrepCommand(is, os, {"-w", pattern, filename}).run(empty_env_);

        const std::string should_be = "        ofSourceSet(\'main\')*.plugins {\n";
        TS_ASSERT_EQUALS(os.str(), should_be);
    }

    /**
     * @brief testWords test working with one and only file
     * looking for words-alike matched sequences.
     */
    void testLines()
    {
        cli::GrepCommand(is, os, {"-A", "2", pattern, filename}).run(empty_env_);

        const std::string should_be = "        ofSourceSet(\'main\')*.plugins {\n"
                                      "            // Apply the \"grpc\" plugin whose spec is defined above, without\n"
                                      "            // options.  Note the braces cannot be omitted, otherwise the\n"
                                      "            // NamedDomainObjectContainer binds the methods.\n"
                                      "            grpc { }"
                                      "\n        }\n";
        TS_ASSERT_EQUALS(os.str(), should_be);
    }

    void tearDown() override {
        os.str("");
        os.clear();
    }

private:
    cli::Environment empty_env_;
    std::istringstream is;
    std::ostringstream os;
    const std::string pattern = "main";
    const std::string filename = "../cli/misc/build.gradle";
};

#endif // CLI_GREP_COMMAND_TEST_H

