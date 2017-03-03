#include "cli_parser.h"
#include "cli_exception.h"
#include <cctype>

CLIParser::CLIParser(const std::__cxx11::string &s)
    : iss_(s)
{}

int CLIParser::parse_integer()
{
    std::string s;
    iss_ >> s;

    const std::string parse_goal = "integer";
    if (s.empty())
    {
        throw CLIParseException(s, parse_goal);
    }

    size_t start = 0;
    if (s.front() == '-' || s.front() == '+')
    {
        start++;
    }

    for (size_t i = start; i < s.size(); ++i)
    {
        if (!isdigit(s[i]))
        {
            throw CLIParseException(s, parse_goal);
        }
    }

    int x;
    std::istringstream is(s);
    is >> x;

    return x;
}
