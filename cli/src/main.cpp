#include "cli_exception.h"
#include "cli_application.h"
#include <iostream>


int main(int argc, char **argv)
{
//    freopen("../../misc/input.txt", "r", stdin);
    CLIApplication app(argc, argv);
    return app.main_loop();
}
