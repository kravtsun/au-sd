#include "cli_pwd_command.h"
#include "cli_exception.h"
#ifndef _MSC_VER
#include <unistd.h>
#include <cstdlib>
#else
#include <windows.h>
#include <tchar.h>
#include <cstdio>
#endif

namespace cli {

PwdCommand::PwdCommand(std::istream &is, std::ostream &os, const Command::ParamsListType &params)
    : Command(is, os, params)
{}

int PwdCommand::run(Environment &env)
{
    (void)env;
#ifndef _MSC_VER
    char *buf = get_current_dir_name();
    if (!buf)
#else
    TCHAR buf[MAX_PATH];
    DWORD dwRet = GetCurrentDirectory(MAX_PATH, buf);

    if (dwRet == 0 || dwRet > MAX_PATH)
#endif
    {
        throw CommandException(name(), "failed to determine current directory.");
    }
    else
#ifdef _MSC_VER
    {
        _tprintf(TEXT("%s\n"), buf);
    }
#else
    {
        os_ << buf << std::endl;
    }
    free(buf);
#endif
    return 0;
}

std::string PwdCommand::name() const
{
    return "pwd";
}

} // namespace cli
