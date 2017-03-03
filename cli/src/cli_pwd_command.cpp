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

CLIPwdCommand::CLIPwdCommand(std::istream &is, std::ostream &os, const CLICommand::ParamsListType &params)
    : CLICommand(is, os, params)
{}


int CLIPwdCommand::run(CLIEnvironment &env)
{
    (void)env;
#ifndef _MSC_VER
    char *str = get_current_dir_name();
    if (!str)
#else
    TCHAR buf[MAX_PATH];
    DWORD dwRet = GetCurrentDirectory(MAX_PATH, buf);

    if (dwRet == 0 || dwRet > MAX_PATH)
#endif
    {
        throw CLICommandException(name(), "failed to determine current directory.");
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

std::string CLIPwdCommand::name() const
{
    return "pwd";
}
