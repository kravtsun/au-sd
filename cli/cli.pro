TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

# TODO cleanup for declaring include directories in just one variable
# and using it after.
INCLUDEPATH += include/ third_party/args third_party/cxxtest

# for tests' environment.
INCLUDEPATH_OPTION += -I $$_PRO_FILE_PWD_/include
INCLUDEPATH_OPTION += -I $$_PRO_FILE_PWD_/third_party/args
INCLUDEPATH_OPTION += -I $$_PRO_FILE_PWD_/include/test

DEPENDPATH += include/ third_party/args

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
    src/cli_pwd_command.cpp \
    src/cli_grep_command.cpp \

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
    include/cli_pwd_command.h \
    include/cli_grep_command.h \
    third_party/args/args.hxx \

TEST_HEADERS = \
    include/test/cli_cat_command_test.h \
    include/test/cli_command_parser_test.h \
    include/test/cli_command_queue_test.h \
    include/test/cli_echo_command_test.h \
    include/test/cli_environment_test.h \
    include/test/cli_parser_test.h \
    include/test/cli_grep_command_test.h \

HEADERS += $$TEST_HEADERS

QMAKE_CXXFLAGS += -Wall -Wconversion -Wpedantic

QMAKE_CXXFLAGS_DEBUG += -O0 -ggdb


cxxtestgen = $$PWD/third_party/cxxtest/bin/cxxtestgen
test_main.target = $$OUT_PWD/cli_tester_main.cpp
test_main.commands = cd $$PWD;\
    $$cxxtestgen --have-std --runner=ErrorPrinter --output=$$test_main.target $$TEST_HEADERS;\
    cd -;
# TODO solve problems with rebuilding test code.
#test_main.depends = $$TEST_HEADERS
QMAKE_CLEAN += $$test_main.target

test_exec.target = $$OUT_PWD/test_runner.out
test_exec.commands = cd $$OUT_PWD;\
    CXXTEST_OBJECTS=$\$(ls $(OBJECTS) | grep -v main.o);\
    $$QMAKE_CXX -std=c++11 $$QMAKE_CXXFLAGS_RELEASE -I$$_PRO_FILE_PWD_/third_party/cxxtest \
        $$INCLUDEPATH_OPTION -o $$test_exec.target $\${CXXTEST_OBJECTS} $$test_main.target; \
    cd -
test_exec.depends = $(OBJECTS) test_main
QMAKE_CLEAN += $$test_exec.target

test.commands = $$test_exec.target
test.depends = test_exec

QMAKE_EXTRA_TARGETS += test_main test_exec test

PRE_TARGETDEPS += test
