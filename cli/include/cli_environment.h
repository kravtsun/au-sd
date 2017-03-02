#ifndef CLI_ENVIRONMENT_H
#define CLI_ENVIRONMENT_H

#include <map>
#include <string>

class CLIEnvironment
{
public:
    typedef std::map<std::string, std::string> VarListType;

    CLIEnvironment();
    CLIEnvironment(int argc, char **argv);
    CLIEnvironment(const VarListType &vars);

    // parsing functions look odd here?..
    static bool is_var_assignment(const std::string &s);
    void parse_and_assign(const std::string &s);

    std::string get_var(const std::string &name) const;
    void set_var(const std::string &name, const std::string &value);

    CLIEnvironment operator|(const CLIEnvironment &rhs);

    const VarListType &get_vars();

private:
    VarListType vars_;
};

#endif // CLI_ENVIRONMENT_H
