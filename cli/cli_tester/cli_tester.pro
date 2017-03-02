TEMPLATE = app
CONFIG += console c++11
CONFIG -= app_bundle
CONFIG -= qt

SOURCES += \
    ../src/cli_parser.cpp \
    ../src/cli_exit_command.cpp \
    ../src/cli_exception.cpp \
    ../src/cli_environment.cpp \
    ../src/cli_echo_command.cpp \
    ../src/cli_command.cpp \
    ../src/cli_command_queue.cpp \
    ../src/cli_command_parser.cpp \
    ../src/cli_cat_command.cpp \
    ../src/cli_application.cpp \
    test_runner.cpp \
#    ../third_party/cxxtest/cxxtest/ValueTraits.cpp \
#    ../third_party/cxxtest/cxxtest/TestTracker.cpp \
#    ../third_party/cxxtest/cxxtest/TestSuite.cpp \
#    ../third_party/cxxtest/cxxtest/Root.cpp \
#    ../third_party/cxxtest/cxxtest/RealDescriptions.cpp \
#    ../third_party/cxxtest/cxxtest/LinkedList.cpp \
#    ../third_party/cxxtest/cxxtest/GlobalFixture.cpp \
#    ../third_party/cxxtest/cxxtest/DummyDescriptions.cpp \
#    ../third_party/cxxtest/cxxtest/Descriptions.cpp
    ../src/test_runner.cpp \
    ../src/main.cpp \
    ../src/cli_word_count_command.cpp \
    ../src/cli_unknown_command.cpp \
    ../src/cli_interactive_command.cpp

INCLUDEPATH += ../include/ ../third_party/cxxtest/

HEADERS += \
#    ../include/common.h \
#    ../include/cli_parser.h \
#    ../include/cli_exit_command.h \
#    ../include/cli_exception.h \
#    ../include/cli_environment.h \
#    ../include/cli_echo_command.h \
#    ../include/cli_command.h \
#    ../include/cli_command_queue.h \
#    ../include/cli_command_pipe.h \
#    ../include/cli_command_parser.h \
    ../include/cli_command_parser_test.h \
#    ../include/cli_cat_command.h \
#    ../include/cli_application.h \
#    ../third_party/cxxtest/cxxtest/YesNoRunner.h \
#    ../third_party/cxxtest/cxxtest/XUnitPrinter.h \
#    ../third_party/cxxtest/cxxtest/XmlPrinter.h \
#    ../third_party/cxxtest/cxxtest/XmlFormatter.h \
#    ../third_party/cxxtest/cxxtest/X11Gui.h \
#    ../third_party/cxxtest/cxxtest/Win32Gui.h \
#    ../third_party/cxxtest/cxxtest/ValueTraits.h \
#    ../third_party/cxxtest/cxxtest/unix.h \
#    ../third_party/cxxtest/cxxtest/TestTracker.h \
#    ../third_party/cxxtest/cxxtest/TestSuite.h \
#    ../third_party/cxxtest/cxxtest/TestRunner.h \
#    ../third_party/cxxtest/cxxtest/TestMain.h \
#    ../third_party/cxxtest/cxxtest/TestListener.h \
#    ../third_party/cxxtest/cxxtest/TeeListener.h \
#    ../third_party/cxxtest/cxxtest/StdValueTraits.h \
#    ../third_party/cxxtest/cxxtest/StdTestSuite.h \
#    ../third_party/cxxtest/cxxtest/StdioPrinter.h \
#    ../third_party/cxxtest/cxxtest/StdioFilePrinter.h \
#    ../third_party/cxxtest/cxxtest/StdHeaders.h \
#    ../third_party/cxxtest/cxxtest/SelfTest.h \
#    ../third_party/cxxtest/cxxtest/RealDescriptions.h \
#    ../third_party/cxxtest/cxxtest/QtGui.h \
#    ../third_party/cxxtest/cxxtest/ParenPrinter.h \
#    ../third_party/cxxtest/cxxtest/MSVCErrorPrinter.h \
#    ../third_party/cxxtest/cxxtest/Mock.h \
#    ../third_party/cxxtest/cxxtest/LinkedList.h \
#    ../third_party/cxxtest/cxxtest/Gui.h \
#    ../third_party/cxxtest/cxxtest/GlobalFixture.h \
#    ../third_party/cxxtest/cxxtest/Flags.h \
#    ../third_party/cxxtest/cxxtest/ErrorPrinter.h \
#    ../third_party/cxxtest/cxxtest/ErrorFormatter.h \
#    ../third_party/cxxtest/cxxtest/DummyDescriptions.h \
#    ../third_party/cxxtest/cxxtest/Descriptions.h
    ../include/cli_environment_test.h \
    ../include/cli_cat_command_test.h \
    ../include/cli_command_queue_test.h

