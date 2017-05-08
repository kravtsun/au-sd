#include "cli_command_queue.h"
#include "cli_exception.h"
#include "cli_command.h"
#include "cli_exit_command.h"
#include "cli_cat_command.h"
#include "cli_echo_command.h"
#include "cli_word_count_command.h"
#include "cli_pwd_command.h"
#include "cli_unknown_command.h"
#include <string>
#include <iostream>
#include <memory>
#include <cassert>

namespace cli {

CommandQueue::CommandQueue(Environment &env, CommandPipe &&pipe, std::ostream &os)
    : env_(env)
    , pipe_(pipe)
    , os_(os)
{}

static Command *command_from_name(const std::string &command,
                                     const Command::ParamsListType &params,
                                     std::istream &is,
                                     std::ostream &os)
{
    if (command == "exit")
    {
        return new ExitCommand(is, os, params);
    }
    else if (command == "cat")
    {
        return new CatCommand(is, os, params);
    }
    else if (command == "echo")
    {
        return new EchoCommand(is, os, params);
    }
    else if (command == "wc")
    {
        return new WordCountCommand(is, os, params);
    }
    else if (command == "pwd")
    {
        return new PwdCommand(is, os, params);
    }
    else
    {
        Command::ParamsListType unknown_params;
        unknown_params.push_back(command);
        unknown_params.insert(unknown_params.end(), params.cbegin(), params.cend());
        return new UnknownCommand(is, os, unknown_params);
    }
    return nullptr;
}

Environment CommandQueue::execute_pipe()
{
    std::ostringstream os;
    std::istringstream iss;
    bool iss_initialized = false;

    for (auto const &command_strings : pipe_)
    {
        // clears output stream.
        os.str("");
        if (command_strings.empty())
        {
            continue;
        }

        Environment local_env = env_;

        // Executing environmental varialbes assignments.
        size_t i;
        for (i = 0; i < command_strings.size(); ++i)
        {
            const std::string &s = command_strings[i];
            if (!Environment::is_var_assignment(s))
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

        const Command::ParamsListType command_params(command_strings.begin() + i, command_strings.end());
        std::istream &is = iss_initialized? iss : std::cin;
        std::unique_ptr<Command> new_command(command_from_name(command_name, command_params, is, os));
        new_command->run(env_);

        iss.str(os.str());
        iss_initialized = true;
    }
    os_ << os.str();
    return env_;
}

} // namespace cli
