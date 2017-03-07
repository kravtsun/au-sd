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
    src/cli_pwd_command.cpp \

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

TEST_HEADERS = \
    include/test/cli_cat_command_test.h \
    include/test/cli_command_parser_test.h \
    include/test/cli_command_queue_test.h \
    include/test/cli_echo_command_test.h \
    include/test/cli_environment_test.h \
    include/test/cli_parser_test.h

HEADERS += $$TEST_HEADERS

QMAKE_CXXFLAGS += -Wall -Wconversion -Wpedantic

QMAKE_CXXFLAGS_DEBUG += -O0 -ggdb

cli_tester_main = $$OUT_PWD/cli_tester_main.cpp
cxxtestgen = $$PWD/third_party/cxxtest/bin/cxxtestgen
test_main.target = cli_tester_main
test_main.commands = cd $$PWD;\
    $$cxxtestgen --have-std --runner=ErrorPrinter --output=$$cli_tester_main $$HEADERS;\
    cd -;

test_runner = $$OUT_PWD/test_runner.out
test_exec.target = test_runner
test_exec.commands = cd $$OUT_PWD;\
    CXXTEST_OBJECTS=$\$(ls $(OBJECTS) | grep -v main.o);\
    $$QMAKE_CXX -std=c++11 $$QMAKE_CXXFLAGS_RELEASE -I$$_PRO_FILE_PWD_/third_party/cxxtest \
        -I $$PWD/$$INCLUDEPATH -o $$test_runner $\${CXXTEST_OBJECTS} $$cli_tester_main; \
    cd -
test_exec.depends = $(OBJECTS)

test.commands = $$test_runner
test.depends = test_main test_exec

QMAKE_CLEAN += $$cli_tester_main $$test_runner

QMAKE_EXTRA_TARGETS += test_main test_exec test

POST_TARGETDEPS += test
