#ifndef CLIPARSER_H
#define CLIPARSER_H

#include <string>
#include <sstream>

/**
 * @brief The CLIParser class for simple parses.
 * basicaly, it's just a wrapper around std::stringstream procedures.
 */
class CLIParser
{
public:
    CLIParser(const std::string &s);

    int parse_integer();

private:
    CLIParser();
    std::istringstream iss_;
};

#endif // CLIPARSER_H
