#include "cli_exception.h"
#include "cli_application.h"
#include <iostream>

int main(int argc, char **argv)
{
    cli::Application app(argc, argv);
    return app.main_loop();
}
