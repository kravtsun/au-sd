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
    src/cli_catcommand.cpp

HEADERS += \
    include/cli_exception.h \
    include/cli_environment.h \
    include/common.h \
    include/cli_application.h \
    include/cli_command.h \
    include/cli_catcommand.h


QMAKE_CXXFLAGS += -Wall -Wconversion -Wpedantic

QMAKE_CXXFLAGS_DEBUG += -O0 -ggdb
