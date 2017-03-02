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
        // parse variables.
        while (true)
        {
            //не совсем понятно, зачем на каждой итерации создавать парсер.
            CLICommandParser p(env_, std::cin);

            auto commands = p.parse_all();
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

