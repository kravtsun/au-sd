**cli_tester** is a helper Qt Creator's project in order to allow developing not inside Makefile.

But in order to start working just like `cli/run_tests.sh` script, you need to set a custom pre-build step in this project's build settings.

The following text applies to Qt Creator run on Ubuntu, as cli_tester's project functionality can be translated easily with project **test** from MS Visual Studio's solution **msvc**.

1) open **cli** and **cli_tester** project in Qt Creator and set **cli_tester** dependent on **cli**. It is essential that both projects have the same output directory.

2) create a build step before qmake's build step for **cli_tester** with following settings:
-- **Command:** click "Browse" ans specify absolute path to file `cli/third_party/cxxtest/bin/cxxtestgen` (on Windows: `cli/third_party/cxxtest/bin/cxxtestgen.bat`).
-- **Arguments:** `--have-std --runner=ErrorPrinter -o ../../src/test_runner.cpp ../../include/*_test.h`
-- **Working directory:** `${buildDir}`

## TODO
Insert that pre-build step described before into .pro file. No idea, how to link it to another project (**cli**). Maybe one should insert all tests activity into **cli** project.
