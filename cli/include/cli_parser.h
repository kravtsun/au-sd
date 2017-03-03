#ifndef CLIPARSER_H
#define CLIPARSER_H

#include <string>
#include <sstream>

/**
 * @brief The CLIParser class for simple parses.
 * basicaly, it's just a wrapper around std::stringstream procedures.
 * as it's not a fully object at all, should it be moved into its separate namespace?
 */
class CLIParser
{
public:
    /**
     * @brief CLIParser constructor for this particular strings.
     * @param s string for parsing to be prepared.
     */
    CLIParser(const std::string &s);

    /**
     * @brief parse_integer tries to rip an integer
     * out of current state of input stream (inner structure).
     * @return
     */
    int parse_integer();

private:
    CLIParser();
    std::istringstream iss_;
};

#endif // CLIPARSER_H
