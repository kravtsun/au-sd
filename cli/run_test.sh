#!/bin/bash
set -e
qmake -o bin/Makefile
cd bin
TEST_HEADERS_FILE=cxxtest-headers
ls -1 ../include/*_test.h > ${TEST_HEADERS_FILE}

../third_party/cxxtest/bin/cxxtestgen --have-std --runner=ErrorPrinter -o test_runner.cpp --headers=${TEST_HEADERS_FILE}

make
OBJECTS=$(ls *.o | grep -v main.o)
g++ -std=c++11 -I ../third_party/cxxtest -I ../include -o test_runner.out test_runner.cpp ${OBJECTS}

./test_runner.out
 
