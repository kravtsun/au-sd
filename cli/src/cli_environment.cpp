#include <cassert>
#include "cli_environment.h"
#include "cli_exception.h"

CLIEnvironment::CLIEnvironment()
    : vars_({})
{}

CLIEnvironment::CLIEnvironment(int argc, char **argv)
{
    for (int i = 0; i < argc; ++i)
    {
        set_var(std::to_string(i), argv[i]);
    }
}

CLIEnvironment::CLIEnvironment(const VarListType &vars)
    : vars_(vars)
{}

bool CLIEnvironment::is_var_assignment(const std::string &s)
{
    size_t equal_pos = s.find('=');
    if (equal_pos == std::string::npos)
    {
        return false;
    }

    for (size_t i = 0; i < equal_pos; ++i)
    {
        if (!isalnum(s[i]))
        {
            return false;
        }
    }
//    if (s[equal_pos+1] == ' ' && equal_pos+2 == std::string::npos)
//    {
//        return false;
//    }

    return true;
}

void CLIEnvironment::parse_and_assign(const std::string &s)
{
    if (!is_var_assignment(s))
    {
        throw CLIParseException(s, "variable assignment");
    }

    auto equal_pos = s.find('=');
    std::string name = s.substr(0, equal_pos);
    std::string value = s.substr(equal_pos+1);

    set_var(std::move(name), std::move(value));
}

std::string CLIEnvironment::get_var(const std::string &name) const
{
    auto it = vars_.find(name);
    if (it == vars_.end())
    {
//        LOG("Cannot find environment variable :\"" + name + "\"");
        return "";
    }
    else
    {
        return it->second;
    }
}

void CLIEnvironment::set_var(const std::string &name, const std::string &value)
{
//    auto it = vars_.find(name);
//    if (it != vars_.end())
//    {
//        LOG("Reassigning environment variable \"" + name + "\" to value: " + value);
//    }

    vars_[name] = value;
}

CLIEnvironment CLIEnvironment::operator|(const CLIEnvironment &rhs) const
{
    CLIEnvironment res(vars_);
    for (auto const &it : rhs.vars_)
    {
        res.vars_[it.first] = it.second;
    }
    return res;
}

const CLIEnvironment::VarListType &CLIEnvironment::get_vars()
{
    return vars_;
}
