#include "cli_interactive_command.h"
#include "cli_exception.h"
#include <fstream>

CLIInteractiveCommand::CLIInteractiveCommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : CLICommand(is, os, params)
    , filenames_(params_)
{}

int CLIInteractiveCommand::run(CLIEnvironment &env)
{
    init_run(env);

    if (filenames_.empty())
    {
        std::string line;
        while (getline(is_, line))
        {
            step(std::move(line));
        }
    }
    else
    {
        for (auto const &f : filenames_)
        {
            std::ifstream fin(f);
            if (!fin)
            {
                throw CLICommandException(name(), "Failed to open file: \"" + f + "\"");
            }
            std::string line;
            while (std::getline(fin, line))
            {
                step(std::move(line));
            }
            end_file_step(f);
        }
    }
    end_run(env);
    return 0;
}
