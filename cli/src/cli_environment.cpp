#include <cassert>
#include "cli_environment.h"
#include "cli_exception.h"

namespace cli {

Environment::Environment()
    : vars_({})
{}

Environment::Environment(int argc, char **argv)
{
    for (int i = 0; i < argc; ++i)
    {
        set_var(std::to_string(i), argv[i]);
    }
}

Environment::Environment(const VarListType &vars)
    : vars_(vars)
{}

bool Environment::is_var_assignment(const std::string &s)
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

    return true;
}

void Environment::parse_and_assign(const std::string &s)
{
    if (!is_var_assignment(s))
    {
        throw ParseException(s, "variable assignment");
    }

    auto equal_pos = s.find('=');
    std::string name = s.substr(0, equal_pos);
    std::string value = s.substr(equal_pos+1);

    set_var(std::move(name), std::move(value));
}

std::string Environment::get_var(const std::string &name) const
{
    auto it = vars_.find(name);
    if (it == vars_.end())
    {
        return "";
    }
    else
    {
        return it->second;
    }
}

void Environment::set_var(const std::string &name, const std::string &value)
{
    vars_[name] = value;
}

Environment Environment::operator|(const Environment &rhs) const
{
    Environment res(vars_);
    for (auto const &it : rhs.vars_)
    {
        res.vars_[it.first] = it.second;
    }
    return res;
}

const Environment::VarListType &Environment::get_vars() const
{
    return vars_;
}

} // namespace cli
