#ifndef CLI_APPLICATION_H
#define CLI_APPLICATION_H

#include "cli_environment.h"

namespace cli {
/**
 * @brief The Application class
 * Controller in our class model.
 * knows basic logic of our tool. Moves most valuable building blocks
 * between main workers.
 */
class Application
{
public:
    /**
     * @brief Application
     * the only constructor which takes all the information coming to
     * main module.
     * @param argc - number of command-line arguments
     * @param argv - string command-line arguments
     */
    Application(int argc, char **argv);

    /**
     * @brief Main loop covers basic logic of our code flow.
     * most notable application processess take place in it.
     * @return return code of an application.
     */
    int main_loop();

private:
    Environment env_;
};

} // namespace cli.

#endif // CLI_APPLICATION_H
