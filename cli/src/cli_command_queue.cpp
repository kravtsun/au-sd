#include "cli_command_queue.h"
#include "cli_exception.h"
#include "common.h"
#include "cli_command.h"
#include "cli_exit_command.h"
#include "cli_cat_command.h"
#include "cli_echo_command.h"
#include <string>
#include <iostream>
#include <cassert>

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
    else if (command == "echo")
    {
        return new CLIEchoCommand(is, os, params);
    }
    else
    {
        throw CLINotImlementedException("Subprocess");
        // send to subprocess.
    }
    return nullptr;
}


template<typename T>
static void clear_stream(T &ss)
{
    ss.str(std::string());
}

CLIEnvironment CLICommandQueue::execute_pipe()
{
    // TODO: execute environmental varialbes assignments.
    std::istringstream is;
    std::ostringstream os;

    for (auto const &command_strings : pipe_)
    {
        if (command_strings.empty())
        {
            LOG("command_strings is empty");
            continue;
        }

        CLIEnvironment local_env = env_;

        size_t i;
        for (i = 0; i < command_strings.size(); ++i)
        {
            const std::string &s = command_strings[i];
            if (!CLIEnvironment::is_var_assignment(s))
            {
                break;
            }
            local_env.parse_and_assign(s);
        }

        if (i == command_strings.size())
        {
            // no command was specified. Only env.variables modifications.
            // in this cases bash updates global environment with new assignments.
            env_ = local_env;
            continue;
        }
        const std::string &command_name = command_strings[i];
        i++;

        const CLICommand::ParamsListType command_params(command_strings.begin() + i, command_strings.end());
        clear_stream(os);
        CLICommand *new_command = command_from_name(command_name, command_params, is, os);
        new_command->run(env_);

        is.str(os.str());
    }
    os_ << os.str();
    return env_;
}
