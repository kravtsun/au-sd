#include "cli_exception.h"
#include "cli_application.h"
#include <iostream>


int main(int argc, char **argv)
{
    try
    {
        CLIApplication app(argc, argv);
        return app.main_loop();
    }
    catch (CLIException &e)
    {
        std::cout << "CLIException: ";
        std::cout << e.what() << std::endl;
    }

    return 0;
}

