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
#include "/home/kravtsun/CPP/SoftwareDesign/au-sd/cli/include/cli_cat_command_test.h"

static CLICatCommandTest suite_CLICatCommandTest;

static CxxTest::List Tests_CLICatCommandTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLICatCommandTest( "../../include/cli_cat_command_test.h", 18, "CLICatCommandTest", suite_CLICatCommandTest, Tests_CLICatCommandTest );

static class TestDescription_suite_CLICatCommandTest_testEmpty : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICatCommandTest_testEmpty() : CxxTest::RealTestDescription( Tests_CLICatCommandTest, suiteDescription_CLICatCommandTest, 35, "testEmpty" ) {}
 void runTest() { suite_CLICatCommandTest.testEmpty(); }
} testDescription_suite_CLICatCommandTest_testEmpty;

static class TestDescription_suite_CLICatCommandTest_testFile : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICatCommandTest_testFile() : CxxTest::RealTestDescription( Tests_CLICatCommandTest, suiteDescription_CLICatCommandTest, 50, "testFile" ) {}
 void runTest() { suite_CLICatCommandTest.testFile(); }
} testDescription_suite_CLICatCommandTest_testFile;

static class TestDescription_suite_CLICatCommandTest_testMultipleFiles : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICatCommandTest_testMultipleFiles() : CxxTest::RealTestDescription( Tests_CLICatCommandTest, suiteDescription_CLICatCommandTest, 64, "testMultipleFiles" ) {}
 void runTest() { suite_CLICatCommandTest.testMultipleFiles(); }
} testDescription_suite_CLICatCommandTest_testMultipleFiles;

static class TestDescription_suite_CLICatCommandTest_testNotExists : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICatCommandTest_testNotExists() : CxxTest::RealTestDescription( Tests_CLICatCommandTest, suiteDescription_CLICatCommandTest, 81, "testNotExists" ) {}
 void runTest() { suite_CLICatCommandTest.testNotExists(); }
} testDescription_suite_CLICatCommandTest_testNotExists;

#include "/home/kravtsun/CPP/SoftwareDesign/au-sd/cli/include/cli_command_parser_test.h"

static CLICommandParserTest suite_CLICommandParserTest;

static CxxTest::List Tests_CLICommandParserTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLICommandParserTest( "../../include/cli_command_parser_test.h", 14, "CLICommandParserTest", suite_CLICommandParserTest, Tests_CLICommandParserTest );

static class TestDescription_suite_CLICommandParserTest_testEnvironmentVariables : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICommandParserTest_testEnvironmentVariables() : CxxTest::RealTestDescription( Tests_CLICommandParserTest, suiteDescription_CLICommandParserTest, 56, "testEnvironmentVariables" ) {}
 void runTest() { suite_CLICommandParserTest.testEnvironmentVariables(); }
} testDescription_suite_CLICommandParserTest_testEnvironmentVariables;

static class TestDescription_suite_CLICommandParserTest_testQuotes : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICommandParserTest_testQuotes() : CxxTest::RealTestDescription( Tests_CLICommandParserTest, suiteDescription_CLICommandParserTest, 80, "testQuotes" ) {}
 void runTest() { suite_CLICommandParserTest.testQuotes(); }
} testDescription_suite_CLICommandParserTest_testQuotes;

static class TestDescription_suite_CLICommandParserTest_testLines : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICommandParserTest_testLines() : CxxTest::RealTestDescription( Tests_CLICommandParserTest, suiteDescription_CLICommandParserTest, 103, "testLines" ) {}
 void runTest() { suite_CLICommandParserTest.testLines(); }
} testDescription_suite_CLICommandParserTest_testLines;

#include "/home/kravtsun/CPP/SoftwareDesign/au-sd/cli/include/cli_command_queue_test.h"

static CLICommandQueueTest suite_CLICommandQueueTest;

static CxxTest::List Tests_CLICommandQueueTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLICommandQueueTest( "../../include/cli_command_queue_test.h", 13, "CLICommandQueueTest", suite_CLICommandQueueTest, Tests_CLICommandQueueTest );

static class TestDescription_suite_CLICommandQueueTest_testPipe : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICommandQueueTest_testPipe() : CxxTest::RealTestDescription( Tests_CLICommandQueueTest, suiteDescription_CLICommandQueueTest, 21, "testPipe" ) {}
 void runTest() { suite_CLICommandQueueTest.testPipe(); }
} testDescription_suite_CLICommandQueueTest_testPipe;

#include "/home/kravtsun/CPP/SoftwareDesign/au-sd/cli/include/cli_environment_test.h"

static CLIEnvironmentTest suite_CLIEnvironmentTest;

static CxxTest::List Tests_CLIEnvironmentTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLIEnvironmentTest( "../../include/cli_environment_test.h", 14, "CLIEnvironmentTest", suite_CLIEnvironmentTest, Tests_CLIEnvironmentTest );

static class TestDescription_suite_CLIEnvironmentTest_testIsVarAssignment : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIEnvironmentTest_testIsVarAssignment() : CxxTest::RealTestDescription( Tests_CLIEnvironmentTest, suiteDescription_CLIEnvironmentTest, 20, "testIsVarAssignment" ) {}
 void runTest() { suite_CLIEnvironmentTest.testIsVarAssignment(); }
} testDescription_suite_CLIEnvironmentTest_testIsVarAssignment;

static class TestDescription_suite_CLIEnvironmentTest_testVarFlow : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIEnvironmentTest_testVarFlow() : CxxTest::RealTestDescription( Tests_CLIEnvironmentTest, suiteDescription_CLIEnvironmentTest, 46, "testVarFlow" ) {}
 void runTest() { suite_CLIEnvironmentTest.testVarFlow(); }
} testDescription_suite_CLIEnvironmentTest_testVarFlow;

#include "/home/kravtsun/CPP/SoftwareDesign/au-sd/cli/include/cli_echo_command_test.h"

static CLIEchoCommandTest suite_CLIEchoCommandTest;

static CxxTest::List Tests_CLIEchoCommandTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLIEchoCommandTest( "../../include/cli_echo_command_test.h", 15, "CLIEchoCommandTest", suite_CLIEchoCommandTest, Tests_CLIEchoCommandTest );

static class TestDescription_suite_CLIEchoCommandTest_testSingle : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIEchoCommandTest_testSingle() : CxxTest::RealTestDescription( Tests_CLIEchoCommandTest, suiteDescription_CLIEchoCommandTest, 32, "testSingle" ) {}
 void runTest() { suite_CLIEchoCommandTest.testSingle(); }
} testDescription_suite_CLIEchoCommandTest_testSingle;

static class TestDescription_suite_CLIEchoCommandTest_testMultiple : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIEchoCommandTest_testMultiple() : CxxTest::RealTestDescription( Tests_CLIEchoCommandTest, suiteDescription_CLIEchoCommandTest, 41, "testMultiple" ) {}
 void runTest() { suite_CLIEchoCommandTest.testMultiple(); }
} testDescription_suite_CLIEchoCommandTest_testMultiple;

#include "/home/kravtsun/CPP/SoftwareDesign/au-sd/cli/include/cli_parser_test.h"

static CLIParserTest suite_CLIParserTest;

static CxxTest::List Tests_CLIParserTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLIParserTest( "../../include/cli_parser_test.h", 12, "CLIParserTest", suite_CLIParserTest, Tests_CLIParserTest );

static class TestDescription_suite_CLIParserTest_testSuccess : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIParserTest_testSuccess() : CxxTest::RealTestDescription( Tests_CLIParserTest, suiteDescription_CLIParserTest, 19, "testSuccess" ) {}
 void runTest() { suite_CLIParserTest.testSuccess(); }
} testDescription_suite_CLIParserTest_testSuccess;

static class TestDescription_suite_CLIParserTest_testFail : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLIParserTest_testFail() : CxxTest::RealTestDescription( Tests_CLIParserTest, suiteDescription_CLIParserTest, 29, "testFail" ) {}
 void runTest() { suite_CLIParserTest.testFail(); }
} testDescription_suite_CLIParserTest_testFail;

#include <cxxtest/Root.cpp>
const char* CxxTest::RealWorldDescription::_worldName = "cxxtest";
