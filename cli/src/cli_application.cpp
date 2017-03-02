#include "cli_application.h"
#include "cli_exception.h"
#include "cli_command_parser.h"
#include "cli_command_queue.h"

#include <iostream>

CLIApplication::CLIApplication(int argc, char **argv)
    : env_(argc, argv)
{}

int CLIApplication::main_loop()
{
    try
    {
        while (true)
        {
            // NB. Not sure if I need to create a parser on every iteration.
            // Maybe it's because of environment - it's kind of interiterational thing.
            CLICommandParser p(env_);

            auto commands = p.parse_all_commands(std::cin);
            CLICommandQueue q(env_, std::move(commands), std::cout);
            env_ = q.execute_pipe();
        }
    }
    catch (CLIExitException &e)
    {
        return e.exit_code();
    }
    catch (CLIException &e)
    {
        std::cout << "Unhandled CLIException: ";
        std::cout << e.what() << std::endl;
    }

    return 0;
}

