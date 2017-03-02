#include "include/cli_command_parser.h"
#include "include/cli_exception.h"
#include <cassert>
#include <cctype>
#include <functional>

CLICommandParser::CLICommandParser(const CLIEnvironment &env, std::istream &is)
    : env_(env)
    , is_(is)
{}


CLICommandPipe CLICommandParser::parse_all() {
    // entries currently being parsed.
    CLICommandPipe::Container pipe; //
    CLICommandPipe::Entry pipe_entry;

    bool is_variable_opened;
    std::string var_name;

    bool is_double_quotes = false, is_single_quotes = false;
    std::string output = "";

    auto close_word = [&]()
    {
        if (output == "")
        {
            return;
        }
        pipe_entry.push_back(std::move(output));
        output.clear();
    };

    auto close_variable_if_needed = [&]()
    {
        if (!is_variable_opened)
        {
            return;
        }

//        if (var_name.empty())
//        {
//            throw CLIParseException(output, "environment variable");
//        }
        if (!is_single_quotes)
        {
            throw CLIUnknownException();
        }

        std::string var_value = env_.get_var(var_name);
        if (is_double_quotes)
        {
            output += var_value;
        }
        else
        {
            for (auto const &c : var_value)
            {
                if (c == ' ')
                {
                    close_word();
                }
                else
                {
                    output += c;
                }
            }
        }

        is_variable_opened = false;
        var_name.clear();
    };


    auto close_pipe_entry = [&]()
    {
        close_word();
        pipe.push_back(std::move(pipe_entry));
        pipe_entry.clear();
    };

    auto close_pipe = [&]()
    {
        close_pipe_entry();
    };

    std::string line;
    bool next_line_needed = true;
    while (next_line_needed && std::getline(is_, line))
    {
        next_line_needed = false;
        for (size_t i = 0; i < line.size(); ++i)
        {
            if (line[i] == '$' && !is_single_quotes)
            {
                if (is_variable_opened)
                {
                    if (var_name.empty())
                    {
                        output += '$'; // $$ -> $.
                    }
                    else
                    {
                        close_variable_if_needed();
                    }
                    is_variable_opened = false;
                }
                else
                {
                    is_variable_opened = true;
                    CLIAssert(var_name == "");
                }
            }
            else if (line[i] == '\'' && !is_double_quotes)
            {
                is_single_quotes = !is_single_quotes;
            }
            else if (line[i] == '"' && !is_single_quotes)
            {
                close_variable_if_needed();
                is_double_quotes = !is_double_quotes;
            }
            else if (line[i] == '\\' && i + 1 == line.size())
            {
                next_line_needed = true;
            }
            else if (is_variable_opened && isalnum(line[i]))
            {
                var_name += line[i];
            }
//            else if (line[i] == '#' && !is_single_quotes && !is_double_quotes)
//            {
//                // TODO. implement?
//            }
            else
            {
                close_variable_if_needed();

                if (line[i] == '|')
                {
                    close_pipe_entry();
                }
                else
                {
                    output += line[i];
                }
            }
        }

        next_line_needed = next_line_needed || is_single_quotes || is_double_quotes;
        if (!next_line_needed)
        {
            close_variable_if_needed();
        }
    }
    close_pipe();

    return pipe;
}
