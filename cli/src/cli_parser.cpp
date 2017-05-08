#include "cli_parser.h"
#include "cli_exception.h"
#include <cctype>

namespace cli {

Parser::Parser(const std::string &s)
    : iss_(s)
{}

int Parser::parse_integer()
{
    std::string s;
    iss_ >> s;

    const std::string parse_goal = "integer";
    if (s.empty())
    {
        throw ParseException(s, parse_goal);
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
            throw ParseException(s, parse_goal);
        }
    }

    int x;
    std::istringstream is(s);
    is >> x;

    return x;
}

} // namespace cli
