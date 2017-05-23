#include "cli_application.h"
#include "cli_exception.h"
#include "cli_command_parser.h"
#include "cli_command_queue.h"
#include <iostream>

namespace cli {

Application::Application(int argc, char **argv)
    : env_(argc, argv)
{
    env_.set_var("PS2", "> ");
}

int Application::main_loop()
{
    while (std::cin)
    {
        try
        {
            std::cout << env_.get_var("PS2");
            // NB. Not sure if I need to create a parser on every iteration.
            // Maybe it's because of environment - it's kind of interiterational thing.
            CommandParser p(env_);

            auto commands = p.parse_all_commands(std::cin);
            CommandQueue q(env_, std::move(commands), std::cout);
            env_ = q.execute_pipe();
        }
        catch (CommandException &e)
        {
            std::cerr << e.what() << std::endl;
        }
        catch (ExitException &e)
        {
            return e.exit_code();
        }
        catch (FinishedCommandException &e)
        {
            // command exited too fast, but sucessfully.
            // doing nothing is chosen strategy for now.
        }
        catch (Exception &e)
        {
            std::cerr << "Unhandled Exception: ";
            std::cerr << e.what() << std::endl;
        }
    }

    return 0;
}

} // namespace cli
