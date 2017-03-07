TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += \
    ../src/test_runner.cpp \

INCLUDEPATH += ../include/ ../third_party/cxxtest/

HEADERS += \
	../include/cli_command_parser_test.h \
    ../include/cli_environment_test.h \
    ../include/cli_cat_command_test.h \
    ../include/cli_command_queue_test.h \
    ../include/cli_echo_command_test.h \
    ../include/cli_parser_test.h

LIBS += cli_application.o \
	cli_cat_command.o\
	cli_command.o\
	cli_command_parser.o\
	cli_command_queue.o\
	cli_echo_command.o\
	cli_environment.o\
	cli_exception.o\
	cli_exit_command.o\
	cli_interactive_command.o\
	cli_parser.o\
	cli_unknown_command.o\
	cli_word_count_command.o\
    cli_pwd_command.o\


