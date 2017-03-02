#!/bin/sh
# 
# rm -f src/cxxtest_runner.cpp
# 
# make
# 
# rm -f main.o
# 
# OBJECTS=$(ls *.o)
# 
# HEADERS_FILE=cxxtest-headers
# 
# rm -f ${HEADERS_FILE}
# 
# ls -1 *Test.h > ${HEADERS_FILE}
# 
# ./cxxtest/bin/cxxtestgen --have-std --runner=ErrorPrinter --headers=${HEADERS_FILE} -o runner.cpp
# 
# clang -I./cxxtest -std=c++11 -lstdc++ -o testrunner runner.cpp $OBJECTS
# 
# ./testrunner $@
# 
# rm -f runner.cpp ${HEADERS_FILE}

g++ -std=c++11 -I third_party/cxxtest -I include/ src/test_runner.cpp -o bin/test_runner.out
third_party/cxxtest/bin/cxxtestgen --have-std --runner=ErrorPrinter -o cli_tester/test_runner.cpp include/cli_cat_command_test.h  include/cli_command_parser_test.h  include/cli_command_queue_test.h  include/cli_environment_test.h
 
