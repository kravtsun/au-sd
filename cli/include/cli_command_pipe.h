#pragma once
#ifndef CLI_COMMAND_PIPE_H
#define CLI_COMMAND_PIPE_H

#include <vector>
#include <string>

namespace cli {
/**
 * @brief The CLICommandQueueEntry class
 * simple storage for all commands executed in a "step"
 * (main loop's iteration) it represents all commands
 * executed till EOL or ';' (if ';' is to be implemented further).
 * Simply a wrapper around std::vector of std::vector of something...
 */
typedef std::vector<std::string> CLICommandPipeEntry;

/**
 * @brief one single command with its command line arguments
 * stored as an array of strings.
 */
typedef std::vector<CLICommandPipeEntry> CLICommandPipe;

} // namespace cli

#endif // CLI_COMMAND_PIPE_H
