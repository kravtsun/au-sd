#ifndef CLI_INPUT_HANDLER_H
#define CLI_INPUT_HANDLER_H

#include <iostream>

/**
 * @brief View: Wrapper around low-level input procedures.
 * Maybe it would be better to use inheritance.
 */
class CLIInputHandler
{
public:
    CLIInputHandler(std::istream &is);

    template<typename T>
    CLIInputHandler &operator >>(T &x) {
        is_ >> x;
        return *this;
    }

    bool operator()() {
        return (bool)is_;
    }

private:
    CLIInputHandler();
    std::istream &is_;
};

#endif // CLI_INPUT_HANDLER_H
