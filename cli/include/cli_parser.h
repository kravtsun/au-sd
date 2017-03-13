#ifndef CLI_PARSER_H
#define CLI_PARSER_H

#include <string>
#include <sstream>

namespace cli {

/**
 * @brief The Parser class for simple parses.
 * basicaly, it's just a wrapper around std::stringstream procedures.
 * as it's not a fully object at all, should it be moved into its separate namespace?
 */
class Parser
{
public:
    /**
     * @brief Parser constructor for this particular strings.
     * @param s string for parsing to be prepared.
     */
    explicit Parser(const std::string &s);

    /**
     * @brief parse_integer tries to rip an integer
     * out of current state of input stream (inner structure).
     * @return
     */
    int parse_integer();

private:
    Parser();
    std::istringstream iss_;
};

} // namespace cli

#endif // CLI_PARSER_H
