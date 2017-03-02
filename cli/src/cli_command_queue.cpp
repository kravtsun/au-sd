#include "cli_command_queue.h"
#include "cli_exception.h"
#include "cli_command.h"
#include "cli_exit_command.h"
#include "cli_cat_command.h"
//#include "cli_echo_command.h"
#include <string>
#include <iostream>

CLICommandQueue::CLICommandQueue(CLIEnvironment &env, CLICommandPipe &&pipe, std::ostream &os)
    : env_(env)
    , pipe_(pipe)
    , os_(os)
{

}

static CLICommand * command_from_name(const std::string &command,
                                      const CLICommand::ParamsListType &params,
                                      std::istream &is,
                                      std::ostream &os)
{
    if (command == "exit")
    {
        return new CLIExitCommand(is, os, params);
    }
    else if (command == "cat")
    {
        return new CLICatCommand(is, os, params);
    }
//    else if (command == "echo")
//    {
//        return new CLIEchoCommand();
//    }
    else
    {
        // send to subprocess.
    }
    return nullptr;
}


static void swap_io(std::istringstream &is, std::ostringstream &os)
{
    std::string s;
    while (is >> s);

    is = std::istringstream(os.str());
}

CLIEnvironment CLICommandQueue::execute_pipe()
{
    // TODO: execute environmental varialbes assignments.
    std::istringstream is;
    std::ostringstream os;
    for (auto const &command_strings : pipe_.get_vs())
    {
        if (command_strings.empty())
        {
            throw CLIUnknownException();
        }
        const CLICommand::ParamsListType command_params(command_strings.begin() + 1, command_strings.end());
        CLICommand *new_command = command_from_name(command_strings.back(), command_params, is, os);
        new_command->run(env_);
        swap_io(is, os);
    }
    os_ << os.str();
    return env_;
}
