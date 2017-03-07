#pragma once
#ifndef CLI_PARSER_TEST_H
#define CLI_PARSER_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_parser.h"
#include "cli_exception.h"

/**
 * @brief The CLIParserTest class testing suite
 * for our general-purpose parser.
 */
class CLIParserTest : public CxxTest::TestSuite
{
public:
    /**
     * @brief testSuccess performs tests which should be successful
     * on some valid data.
     */
    void testSuccess()
    {
        const int num = 12312312;
        std::string num_str = std::to_string(num);
        TS_ASSERT_EQUALS(CLIParser(num_str).parse_integer(), num);
    }

    /**
     * @brief testFail tests CLIParser's behavior on incorrect input.
     */
    void testFail()
    {
        const std::string fail_str = "q234ewsdff";
        TS_ASSERT_THROWS(CLIParser(fail_str).parse_integer(), CLIParseException &);

        const std::string double_str = "1.0";
        TS_ASSERT_THROWS(CLIParser(double_str).parse_integer(), CLIParseException &);
    }
};

#endif // CLI_PARSER_TEST_H

