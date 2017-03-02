#include "cli_command_queue.h"
#include "cli_exception.h"
#include "common.h"
#include "cli_command.h"
#include "cli_exit_command.h"
#include "cli_cat_command.h"
#include "cli_echo_command.h"
#include "cli_word_count_command.h"
#include "cli_unknown_command.h"
#include <string>
#include <iostream>
#include <memory>
#include <cassert>

CLICommandQueue::CLICommandQueue(CLIEnvironment &env, CLICommandPipe &&pipe, std::ostream &os)
    : env_(env)
    , pipe_(pipe)
    , os_(os)
{

}

static CLICommand *command_from_name(const std::string &command,
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
    else if (command == "wc")
    {
        return new CLIWordCountCommand(is, os, params);
    }
    else
    {
        CLICommand::ParamsListType unknown_params;
        unknown_params.push_back(command);
        unknown_params.insert(unknown_params.end(), params.cbegin(), params.cend());
        return new CLIUnknownCommand(is, os, unknown_params);
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
    std::ostringstream os;
    std::istringstream iss;
    bool iss_initialized = false;

    for (auto const &command_strings : pipe_)
    {
        os.str("");
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
        std::istream &is = iss_initialized? iss : std::cin;
        std::unique_ptr<CLICommand> new_command(command_from_name(command_name, command_params, is, os));
        new_command->run(env_);

        iss.str(os.str());
        iss_initialized = true;
    }
    os_ << os.str();
    return env_;
}
