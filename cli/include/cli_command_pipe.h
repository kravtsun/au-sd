#ifndef CLI_COMMAND_PIPE_H
#define CLI_COMMAND_PIPE_H

#include <vector>
#include <string>

/**
 * @brief The CLICommandQueueEntry class
 * simple struct for storing all commands in a "step" -
 * it represents all commands executed till EOL or ';'
 * (if ';' is to be implemented further).
 * Simply a wrapper around std::vector of std::vector of something...
 */
class CLICommandPipe
{
public:
    typedef std::vector<std::string> Entry;
    typedef std::vector<Entry> Container;
    /**
     * @brief CLICommandQueueEntry constructor.
     * @param sequence of commands (interspersed with commands).
     */
    CLICommandPipe(Container &&vs = Container());

    const Container &get_vs() const
    {
        return vs_;
    }

private:
    std::vector<std::vector<std::string>> vs_;
};

#endif // CLI_COMMAND_PIPE_H
