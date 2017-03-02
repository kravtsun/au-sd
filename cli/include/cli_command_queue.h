#ifndef CLI_COMMAND_QUEUE_H
#define CLI_COMMAND_QUEUE_H

#include "cli_environment.h"
#include "cli_command_pipe.h"
#include <ostream>

class CLICommandQueue
{
public:
    CLICommandQueue(CLIEnvironment &env, CLICommandPipe &&pipe, std::ostream &os);

    CLIEnvironment execute_pipe();

private:
    CLIEnvironment env_;
    CLICommandPipe pipe_;
    std::ostream &os_;
};

#endif // CLI_COMMAND_QUEUE_H
