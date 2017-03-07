#pragma once
#ifndef CLI_COMMAND_QUEUE_H
#define CLI_COMMAND_QUEUE_H

#include "cli_environment.h"
#include "cli_command_pipe.h"
#include <ostream>

namespace cli {

/**
 * @brief The CLICommandQueue class - commands executor.
 * have responsibility on the order of commands' executed
 * and makes sure they share data streams correctly.
 */
class CLICommandQueue
{
public:
    /**
     * @brief CLICommandQueue - constructor.
     * @param env Environmental variables needed for initialization.
     * @param pipe commands to be executed in a pipe sequence.
     * @param os output stream.
     */
    CLICommandQueue(CLIEnvironment &env, CLICommandPipe &&pipe, std::ostream &os);

    /**
     * @brief execute_pipe - trigger for starting scheduling and running commands
     * in a single pipe.
     * @return new environment (which can be changed after commands executed).
     */
    CLIEnvironment execute_pipe();

private:
    CLIEnvironment env_;
    CLICommandPipe pipe_;
    std::ostream &os_;
};

} // namespace cli

#endif // CLI_COMMAND_QUEUE_H
