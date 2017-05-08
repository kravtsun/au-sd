# cli_tester
**cli_tester** is a helper Qt Creator's project in order to allow developing not inside Makefile.

But in order to start working just like cli/run_tests.sh script, you need to set a custom pre-build step in this project's build settings.
1) open **cli** and **cli_tester** project in Qt Project and set **cli_tester** dependent on **cli**.

2) create a build step before qmake's build step with following settings (as if **cli**'s build directory in `cli/bin/debug`):
- Command: ../../third_party/cxxtest/bin/cxxtestgen
- `--have-std --runner=ErrorPrinter -o ../../src/test_runner.cpp ../../include/*_test.h`
- Working directory: `${buildDir}`
