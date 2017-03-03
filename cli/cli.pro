TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

INCLUDEPATH += include/
DEPENDPATH += include/

SOURCES += src/main.cpp \
    src/cli_exception.cpp \
    src/cli_environment.cpp \
    src/cli_application.cpp \
    src/cli_command.cpp \
    src/cli_exit_command.cpp \
    src/cli_parser.cpp \
    src/cli_command_parser.cpp \
    src/cli_command_queue.cpp \
    src/cli_cat_command.cpp \
    src/cli_echo_command.cpp \
    src/cli_word_count_command.cpp \
    src/cli_interactive_command.cpp \
    src/cli_unknown_command.cpp \
    src/cli_pwd_command.cpp

HEADERS += \
    include/cli_exception.h \
    include/cli_environment.h \
    include/cli_application.h \
    include/cli_command.h \
    include/cli_exit_command.h \
    include/cli_parser.h \
    include/cli_command_parser.h \
    include/cli_command_queue.h \
    include/cli_command_pipe.h \
    include/cli_cat_command.h \
	include/cli_echo_command.h \
    include/cli_word_count_command.h \
    include/cli_interactive_command.h \
    include/cli_unknown_command.h \
    include/cli_pwd_command.h


QMAKE_CXXFLAGS += -std=c++11
QMAKE_CXXFLAGS += -Wall -Wconversion -Wpedantic

QMAKE_CXXFLAGS_DEBUG += -O0 -ggdb

DISTFILES += \
    misc/input.txt
