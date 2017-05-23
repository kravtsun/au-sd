/* Generated file, do not edit */

#ifndef CXXTEST_RUNNING
#define CXXTEST_RUNNING
#endif

#define _CXXTEST_HAVE_STD
#define _CXXTEST_HAVE_EH
#include <cxxtest/TestListener.h>
#include <cxxtest/TestTracker.h>
#include <cxxtest/TestRunner.h>
#include <cxxtest/RealDescriptions.h>
#include <cxxtest/TestMain.h>
#include <cxxtest/ErrorPrinter.h>

int main( int argc, char *argv[] ) {
 int status;
    CxxTest::ErrorPrinter tmp;
    CxxTest::RealWorldDescription::_worldName = "cxxtest";
    status = CxxTest::Main< CxxTest::ErrorPrinter >( tmp, argc, argv );
    return status;
}
bool suite_CLICatCommandTest_init = false;
#include "..\..\include\test\cli_cat_command_test.h"

static CLICatCommandTest suite_CLICatCommandTest;

static CxxTest::List Tests_CLICatCommandTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLICatCommandTest( "C:/Users/Andrey/Projects/au-sd/cli/msvc//../include/test/cli_cat_command_test.h", 18, "CLICatCommandTest", suite_CLICatCommandTest, Tests_CLICatCommandTest );

static class TestDescription_suite_CLICatCommandTest_testEmpty : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICatCommandTest_testEmpty() : CxxTest::RealTestDescription( Tests_CLICatCommandTest, suiteDescription_CLICatCommandTest, 46, "testEmpty" ) {}
 void runTest() { suite_CLICatCommandTest.testEmpty(); }
} testDescription_suite_CLICatCommandTest_testEmpty;

static class TestDescription_suite_CLICatCommandTest_testFile : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICatCommandTest_testFile() : CxxTest::RealTestDescription( Tests_CLICatCommandTest, suiteDescription_CLICatCommandTest, 61, "testFile" ) {}
 void runTest() { suite_CLICatCommandTest.testFile(); }
} testDescription_suite_CLICatCommandTest_testFile;

static class TestDescription_suite_CLICatCommandTest_testMultipleFiles : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICatCommandTest_testMultipleFiles() : CxxTest::RealTestDescription( Tests_CLICatCommandTest, suiteDescription_CLICatCommandTest, 75, "testMultipleFiles" ) {}
 void runTest() { suite_CLICatCommandTest.testMultipleFiles(); }
} testDescription_suite_CLICatCommandTest_testMultipleFiles;

static class TestDescription_suite_CLICatCommandTest_testNotExists : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICatCommandTest_testNotExists() : CxxTest::RealTestDescription( Tests_CLICatCommandTest, suiteDescription_CLICatCommandTest, 92, "testNotExists" ) {}
 void runTest() { suite_CLICatCommandTest.testNotExists(); }
} testDescription_suite_CLICatCommandTest_testNotExists;

#include "..\..\include\test\cli_command_parser_test.h"

static CommandParserTest suite_CommandParserTest;

static CxxTest::List Tests_CommandParserTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CommandParserTest( "C:/Users/Andrey/Projects/au-sd/cli/msvc//../include/test/cli_command_parser_test.h", 14, "CommandParserTest", suite_CommandParserTest, Tests_CommandParserTest );

static class TestDescription_suite_CommandParserTest_testEnvironmentVariables : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CommandParserTest_testEnvironmentVariables() : CxxTest::RealTestDescription( Tests_CommandParserTest, suiteDescription_CommandParserTest, 55, "testEnvironmentVariables" ) {}
 void runTest() { suite_CommandParserTest.testEnvironmentVariables(); }
} testDescription_suite_CommandParserTest_testEnvironmentVariables;

static class TestDescription_suite_CommandParserTest_testQuotes : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CommandParserTest_testQuotes() : CxxTest::RealTestDescription( Tests_CommandParserTest, suiteDescription_CommandParserTest, 79, "testQuotes" ) {}
 void runTest() { suite_CommandParserTest.testQuotes(); }
} testDescription_suite_CommandParserTest_testQuotes;

static class TestDescription_suite_CommandParserTest_testLines : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CommandParserTest_testLines() : CxxTest::RealTestDescription( Tests_CommandParserTest, suiteDescription_CommandParserTest, 102, "testLines" ) {}
 void runTest() { suite_CommandParserTest.testLines(); }
} testDescription_suite_CommandParserTest_testLines;

#include "..\..\include\test\cli_command_queue_test.h"

static CommandQueueTest suite_CommandQueueTest;

static CxxTest::List Tests_CommandQueueTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CommandQueueTest( "C:/Users/Andrey/Projects/au-sd/cli/msvc//../include/test/cli_command_queue_test.h", 13, "CommandQueueTest", suite_CommandQueueTest, Tests_CommandQueueTest );

static class TestDescription_suite_CommandQueueTest_testPipe : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CommandQueueTest_testPipe() : CxxTest::RealTestDescription( Tests_CommandQueueTest, suiteDescription_CommandQueueTest, 21, "testPipe" ) {}
 void runTest() { suite_CommandQueueTest.testPipe(); }
} testDescription_suite_CommandQueueTest_testPipe;

#include "..\..\include\test\cli_echo_command_test.h"

static EchoCommandTest suite_EchoCommandTest;

static CxxTest::List Tests_EchoCommandTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_EchoCommandTest( "C:/Users/Andrey/Projects/au-sd/cli/msvc//../include/test/cli_echo_command_test.h", 15, "EchoCommandTest", suite_EchoCommandTest, Tests_EchoCommandTest );

static class TestDescription_suite_EchoCommandTest_testSingle : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_EchoCommandTest_testSingle() : CxxTest::RealTestDescription( Tests_EchoCommandTest, suiteDescription_EchoCommandTest, 32, "testSingle" ) {}
 void runTest() { suite_EchoCommandTest.testSingle(); }
} testDescription_suite_EchoCommandTest_testSingle;

static class TestDescription_suite_EchoCommandTest_testMultiple : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_EchoCommandTest_testMultiple() : CxxTest::RealTestDescription( Tests_EchoCommandTest, suiteDescription_EchoCommandTest, 41, "testMultiple" ) {}
 void runTest() { suite_EchoCommandTest.testMultiple(); }
} testDescription_suite_EchoCommandTest_testMultiple;

#include "..\..\include\test\cli_environment_test.h"

static EnvironmentTest suite_EnvironmentTest;

static CxxTest::List Tests_EnvironmentTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_EnvironmentTest( "C:/Users/Andrey/Projects/au-sd/cli/msvc//../include/test/cli_environment_test.h", 14, "EnvironmentTest", suite_EnvironmentTest, Tests_EnvironmentTest );

static class TestDescription_suite_EnvironmentTest_testIsVarAssignment : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_EnvironmentTest_testIsVarAssignment() : CxxTest::RealTestDescription( Tests_EnvironmentTest, suiteDescription_EnvironmentTest, 20, "testIsVarAssignment" ) {}
 void runTest() { suite_EnvironmentTest.testIsVarAssignment(); }
} testDescription_suite_EnvironmentTest_testIsVarAssignment;

static class TestDescription_suite_EnvironmentTest_testVarFlow : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_EnvironmentTest_testVarFlow() : CxxTest::RealTestDescription( Tests_EnvironmentTest, suiteDescription_EnvironmentTest, 46, "testVarFlow" ) {}
 void runTest() { suite_EnvironmentTest.testVarFlow(); }
} testDescription_suite_EnvironmentTest_testVarFlow;

#include "..\..\include\test\cli_grep_command_test.h"

static CLIGrepCommandTest suite_CLIGrepCommandTest;

static CxxTest::List Tests_CLIGrepCommandTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLIGrepCommandTest( "C:/Users/Andrey/Projects/au-sd/cli/msvc//../include/test/cli_grep_command_test.h", 17, "CLIGrepCommandTest", suite_CLIGrepCommandTest, Tests_CLIGrepCommandTest );

static class TestDescription_suite_CLIGrepCommandTest_testSimple : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIGrepCommandTest_testSimple() : CxxTest::RealTestDescription( Tests_CLIGrepCommandTest, suiteDescription_CLIGrepCommandTest, 26, "testSimple" ) {}
 void runTest() { suite_CLIGrepCommandTest.testSimple(); }
} testDescription_suite_CLIGrepCommandTest_testSimple;

static class TestDescription_suite_CLIGrepCommandTest_testWords : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIGrepCommandTest_testWords() : CxxTest::RealTestDescription( Tests_CLIGrepCommandTest, suiteDescription_CLIGrepCommandTest, 41, "testWords" ) {}
 void runTest() { suite_CLIGrepCommandTest.testWords(); }
} testDescription_suite_CLIGrepCommandTest_testWords;

static class TestDescription_suite_CLIGrepCommandTest_testLines : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIGrepCommandTest_testLines() : CxxTest::RealTestDescription( Tests_CLIGrepCommandTest, suiteDescription_CLIGrepCommandTest, 53, "testLines" ) {}
 void runTest() { suite_CLIGrepCommandTest.testLines(); }
} testDescription_suite_CLIGrepCommandTest_testLines;

#include "..\..\include\test\cli_parser_test.h"

static ParserTest suite_ParserTest;

static CxxTest::List Tests_ParserTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_ParserTest( "C:/Users/Andrey/Projects/au-sd/cli/msvc//../include/test/cli_parser_test.h", 12, "ParserTest", suite_ParserTest, Tests_ParserTest );

static class TestDescription_suite_ParserTest_testSuccess : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_ParserTest_testSuccess() : CxxTest::RealTestDescription( Tests_ParserTest, suiteDescription_ParserTest, 19, "testSuccess" ) {}
 void runTest() { suite_ParserTest.testSuccess(); }
} testDescription_suite_ParserTest_testSuccess;

static class TestDescription_suite_ParserTest_testFail : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_ParserTest_testFail() : CxxTest::RealTestDescription( Tests_ParserTest, suiteDescription_ParserTest, 29, "testFail" ) {}
 void runTest() { suite_ParserTest.testFail(); }
} testDescription_suite_ParserTest_testFail;

#include <cxxtest/Root.cpp>
const char* CxxTest::RealWorldDescription::_worldName = "cxxtest";
