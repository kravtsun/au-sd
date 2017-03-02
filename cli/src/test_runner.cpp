/* Generated file, do not edit */

#ifndef CXXTEST_RUNNING
#define CXXTEST_RUNNING
#endif

#define _CXXTEST_HAVE_STD
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
bool suite_CLICommandParserTest_init = false;
#include "/home/kravtsun/CPP/SoftwareDesign/au-sd/cli/include/cli_command_parser_test.h"

static CLICommandParserTest suite_CLICommandParserTest;

static CxxTest::List Tests_CLICommandParserTest = { 0, 0 };
CxxTest::StaticSuiteDescription suiteDescription_CLICommandParserTest( "include/cli_command_parser_test.h", 10, "CLICommandParserTest", suite_CLICommandParserTest, Tests_CLICommandParserTest );

static class TestDescription_suite_CLICommandParserTest_testEnvironmentVariables : public CxxTest::RealTestDescription {
public:
 TestDescription_suite_CLICommandParserTest_testEnvironmentVariables() : CxxTest::RealTestDescription( Tests_CLICommandParserTest, suiteDescription_CLICommandParserTest, 12, "testEnvironmentVariables" ) {}
 void runTest() { suite_CLICommandParserTest.testEnvironmentVariables(); }
} testDescription_suite_CLICommandParserTest_testEnvironmentVariables;

#include <cxxtest/Root.cpp>
const char* CxxTest::RealWorldDescription::_worldName = "cxxtest";
