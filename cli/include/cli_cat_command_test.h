#pragma once
#ifndef CLI_CAT_COMMAND_TEST_H
#define CLI_CAT_COMMAND_TEST_H

#include <cxxtest/TestSuite.h>
#include "cli_cat_command.h"
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
class CLICatCommandTest : public CxxTest::TestSuite
{
public:
    /**
     * @brief CLICatCommandTest constructor.
     * prepare test following after.
     */
    CLICatCommandTest()
    {
        writeLinesToFile(filename1(), lines1());
        writeLinesToFile(filename2(), lines2());
    }

    /**
     * @brief testEmpty test if no parameters was specified
     * and so cat should run in interactive mode.
     */
    void testEmpty()
    {
        std::string ask = "\n\n123";
        std::istringstream is(ask);
        std::string answer = ask + "\n";
        std::ostringstream os;
        cli::CatCommand cat_command(is, os, {});
        cat_command.run(empty_env_);
        TS_ASSERT_EQUALS(os.str(), answer);
    }

    /**
     * @brief testFile test working with one and only file
     * specified.
     */
    void testFile()
    {
        std::istringstream is("");
        std::ostringstream os;

        cli::CatCommand cat_command(is, os, {filename1()});
        cat_command.run(empty_env_);

        TS_ASSERT_EQUALS(os.str(), merge_lines(lines1()));
    }

    /**
     * @brief testMultipleFiles test working with several files.
     */
    void testMultipleFiles()
    {
        std::istringstream is("");
        std::ostringstream os("");

        cli::CatCommand cat_command(is, os, {filename1(), filename2()});
        cat_command.run(empty_env_);

        const std::string all_lines = merge_lines(lines1()) + merge_lines(lines2());

        TS_ASSERT_EQUALS(os.str(), all_lines);
    }

    /**
     * @brief testNotExists test on correct behavior when
     * not-existent file is specified.
     */
    void testNotExists()
    {
        std::string filename = "26486321386463213";
        std::istringstream is;
        std::ostringstream os;
        cli::CatCommand cat_command(is, os, {filename});
        TS_ASSERT_THROWS(cat_command.run(empty_env_), cli::CommandException &);
    }

private:
    cli::Environment empty_env_;

    static inline std::string filename1()
    {
        return "example.txt";
    }

    static inline std::string filename2()
    {
        return "123";
    }

    static inline std::vector<std::string> lines1()
    {
        return {
            "Some example text",
            "second line"
        };
    }

    static inline std::vector<std::string> lines2()
    {
        return {
            "#123",
            "\\"
        };
    }

    std::string merge_lines(std::vector<std::string> &&vs)
    {
        if (vs.empty())
        {
            return "\n";
        }

        std::string res = "";
        for (auto &&s : vs)
        {
            res += s + "\n";
        }

        return res;
    }

    static void writeLinesToFile(const std::string &filename, const std::vector<std::string> &lines)
    {
        std::ofstream fout(filename);
        for (auto const &s : lines)
        {
            fout << s << std::endl;
        }
    }
};

#endif // CLI_CAT_COMMAND_TEST_H

