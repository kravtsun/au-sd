#include "cli_command_parser.h"
#include "cli_exception.h"
#include <functional>
#include <cassert>
#include <cctype>

namespace cli {

CLICommandParser::CLICommandParser(const CLIEnvironment &env)
    : env_(env)
{}

CLICommandPipe CLICommandParser::parse_all_commands(std::istream &is) {
    CLICommandPipe pipe;
    CLICommandPipeEntry pipe_entry;

    bool is_variable_opened = false;
    std::string var_name = "";

    bool is_double_quotes = false, is_single_quotes = false;
    std::string output = "";

    std::function<void()> close_word;

    auto close_variable_if_needed = [&]()
    {
        if (!is_variable_opened)
        {
            return;
        }

        // can't work with environmental variables when in single quotes.
        assert(!is_single_quotes);

        std::string var_value = env_.get_var(std::move(var_name));
        is_variable_opened = false;
        var_name.clear();

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
    };

    close_word = [&]()
    {
        close_variable_if_needed();
        if (output.empty())
        {
            return;
        }
        pipe_entry.push_back(std::move(output));
        output.clear();
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
    while (next_line_needed && std::getline(is, line))
    {
        for (size_t i = 0; i < line.size(); ++i)
        {
            next_line_needed = false;
            if (line[i] == '$' && !is_single_quotes)
            {
                if (is_variable_opened && var_name.empty())
                {
                    // $$ in input transforms into single '$' sign.
                    output += '$';
                    is_variable_opened = false;
                }
                else
                {
                    close_variable_if_needed(); // $x$y
                    is_variable_opened = true;
                    assert(var_name.empty());
                }
            }
            else if (line[i] == '\'' && !is_double_quotes)
            {
                close_variable_if_needed();
                is_single_quotes = !is_single_quotes;
            }
            else if (line[i] == '"' && !is_single_quotes)
            {
                close_variable_if_needed();
                is_double_quotes = !is_double_quotes;
            }
#ifndef _MSC_VER
            else if (line[i] == '\\' && i + 1 == line.size())
            {
                next_line_needed = true;
            }
#endif
            else if (is_variable_opened && isalnum(line[i]))
            {
                var_name += line[i];
            }
            else if (line[i] == ' ' && !is_single_quotes && !is_double_quotes)
            {
                close_word();
            }
            else if (line[i] == '|' && !is_single_quotes && !is_double_quotes)
            {
                close_pipe_entry();
            }
            else
            {
                close_variable_if_needed();
                output += line[i];
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

} // namespace cli
