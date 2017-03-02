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
typedef std::vector<std::string> CLICommandPipeEntry;
typedef std::vector<CLICommandPipeEntry> CLICommandPipe;

#endif // CLI_COMMAND_PIPE_H
