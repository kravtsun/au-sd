#include "cli_application.h"
#include "cli_exception.h"

CLIApplication::CLIApplication(int argc, char **argv)
    : env_(argc, argv)
{}

int CLIApplication::main_loop()
{
    try
    {
        // string.

        // parse variables.
        while (true)
        {
            std::string line;
            getline(std::cin, line);

            auto lexems = StringDealer(line, env);

            CommandRunner();

//            Command;
        }
    }
    catch (CLIException &e)
    {
        return -1;
    }

    return 0;
}

