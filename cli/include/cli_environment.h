#ifndef CLI_ENVIRONMENT_H
#define CLI_ENVIRONMENT_H

#include <map>
#include <string>

namespace cli {

/**
 * @brief The Environment class structure for
 * storing environmental variables and meta information.
 * Basic (but currently not so much used) tool for exchanging
 * knowledge between CLI application entities.
 */
class Environment
{
public:
    /**
     * @brief VarListType an alias for the type used for storing information
     * in this structure.
     */
    typedef std::map<std::string, std::string> VarListType;

    /**
     * @brief Environment default constructor.
     * Not needed on running CLI app, but
     * happens to be useful when testing.
     */
    Environment();

    /**
     * @brief Environment init command-line arguments
     * as in bash they are stored as environmental variables with number names
     * such as $0, $1, ... etc.
     * @param argc - number of arguments.
     * @param argv - array of c-strings.
     */
    Environment(int argc, char **argv);

    /**
     * @brief Environment simple interface to the inner
     * storage of this structure.
     * helps when testing, so you won't need to
     * start the whole application stack just in order to
     * setup an eligible Environment structure.
     * @param vars variables to be stored in the structure.
     */
    explicit Environment(const VarListType &vars);

    /**
     * @brief is_var_assignment check if a string
     * can be a valid variable assignment as it is
     * somewhat neat and subtle element of syntax
     * in bash and the others. It literally can appear
     * anywhere and change the environment apparently any time
     * no command is being executed.
     * @param string to be checked on being an assignment of
     * an environmental variable
     * @return can or cannot be an assignment.
     */
    static bool is_var_assignment(const std::string &s);

    /**
     * @brief the same as is_var_assignment, but
     * it does not answer the question if a string can be a
     * variable assignment. It does the variable assignment.
     * @param s string to be parsed.
     */
    void parse_and_assign(const std::string &s);

    /**
     * @brief get_var - get environmental variable's value
     * in current environment.
     * @param name variable's name.
     * @return "" if no variable with name "name" is here.
     * Otherwise its value is returned.
     */
    std::string get_var(const std::string &name) const;

    /**
     * @brief set_var change current environment via changing
     * or setting an environmental variable's value.
     * @param name variable's name.
     * @param value new value for variable @p name.
     */
    void set_var(const std::string &name, const std::string &value);

    /**
     * @brief operator update 'this' environment with all variables stored in right
     * hand side environment storage.
     * @param rhs right hand side operand.
     * @return updated environment
     */
    Environment operator|(const Environment &rhs) const;

    /**
     * @brief get_vars get all variables and their values stored.
     * for testing purposes only.
     * @return structure used for storing variables in Environment.
     */
    const VarListType &get_vars() const;

private:
    VarListType vars_;
};

} // namespace cli

#endif // CLI_ENVIRONMENT_H
