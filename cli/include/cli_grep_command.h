#ifndef CLI_GREP_COMMAND_H
#define CLI_GREP_COMMAND_H

#include <regex>
#include "cli_interactive_command.h"

namespace cli {

class GrepCommand : public InteractiveCommand
{
public:
    GrepCommand(std::istream &is, std::ostream &os, const ParamsListType &params);

    /**
     * @brief name of command in CLI.
     * @return std::string
     */
    std::string name() const override;

    // InteractiveCommand interface
protected:
    void init_run(const Environment &) override;
    void step(std::string &&line) override;
    void end_file_step(const std::string &) override;
    void end_run(Environment &env) override;

private:
    std::vector<std::string> filenames_;

    /**
     * @brief The RegexParameters struct incorporating all std::regex-related parameters.
     */
    struct RegexParameters {
        // TODO optimize code with using namespace alias std::regex_constants.
    public:
        RegexParameters();

        /**
         * @brief build Constructs regex from parameters received earlier.
         * TODO add assert on initializing all atomic parameters -
         * be fully ready for building std::regex from them.
         */
        void build();

        std::regex get_regex() const;

        void set_ignore_case(bool ignore_case);

        void set_words_only(bool words_only);

        void set_pattern(const std::string &pattern);

        static constexpr std::regex::flag_type default_regex_flags() {
            return std::regex_constants::ECMAScript;
        }

        std::regex_constants::match_flag_type get_match_flags() const;

    private:
        static std::string default_regex_word_beginning() {
            return "\\b";
        }

        static std::string default_regex_word_ending() {
            return "\\b";
        }

    private:
        std::regex_constants::match_flag_type match_flags_;
        std::regex regex_;

        bool ignore_case_;
        bool words_only_;
        std::string pattern_;
    } regex_params_;

    struct GrepState {
        void reset()
        {
            lines_left_ = 0;
        }

        int lines_left_;
        int after_lines_;
    } state_;
//    static std::regex GrepCommand::word_regex_{"\\w+",
//                                               RegexParameters::default_regex_flags()};
};

} // namespace cli.

#endif // CLI_GREP_COMMAND_H
