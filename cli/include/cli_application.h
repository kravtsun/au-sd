#ifndef CLI_APPLICATION_H
#define CLI_APPLICATION_H

#include "cli_environment.h"

class CLIApplication
{
public:
    CLIApplication(int argc, char **argv);

    int main_loop();

private:
    CLIApplication();

    CLIEnvironment env_;
};

#endif // CLI_APPLICATION_H
