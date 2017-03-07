#pragma once
#ifndef CLI_APPLICATION_H
#define CLI_APPLICATION_H

#include "cli_environment.h"

/**
 * @brief The CLIApplication class
 * Controller in our class model.
 * knows basic logic of our tool. Moves most valuable building blocks
 * between main workers.
 */
class CLIApplication
{
public:
    /**
     * @brief CLIApplication
     * the only constructor which takes all the information coming to
     * main module.
     * @param argc - number of command-line arguments
     * @param argv - string command-line arguments
     */
    CLIApplication(int argc, char **argv);

    /**
     * @brief Main loop covers basic logic of our code flow.
     * most notable application processess take place in it.
     * @return return code of an application.
     */
    int main_loop();

private:
    CLIEnvironment env_;
};

#endif // CLI_APPLICATION_H
