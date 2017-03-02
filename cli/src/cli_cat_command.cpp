#include "cli_cat_command.h"
#include "cli_exception.h"
#include <iostream>
#include <fstream>
#include <string>

CLICatCommand::CLICatCommand(std::istream &is, std::ostream &os, const ParamsListType &params_)
    : CLICommand(is, os, params_)
    , filenames_(params_)
{
}

std::string CLICatCommand::name() const
{
    return "cat";
}

int CLICatCommand::run(CLIEnvironment &env)
{
    (void)env;

    if (filenames_.empty())
    {
        std::string line;
        while (getline(is_, line))
        {
            os_ << line << std::endl;
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
                os_ << line << std::endl;
            }
        }
    }
    return 0;
}
