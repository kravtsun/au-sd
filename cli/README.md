#CLI
First homework project, CLI - command line interpreter.

##Prerequisites
###Ubuntu (tested on ver. 16.04): 
* qmake ver. (tested on ver.3.0 using Qt version 5.5.1)
* g++ (tested on ver. 5.4.0).
* Python (ver. >= 2.7) added to PATH.

###Windows (tested on ver. 10)
* Microsoft Visual Studio (tested on MSVS 2013).
* Python (ver. >= 2.7) added to PATH.

##USAGE
###Ubuntu:
```bash
qmake && make && ./cli
```

###Windows:
open solution in MS Visual Studio (ver. >= 2015) an build project 'cli'
(optionally testing it with project 'tester').
to be descripted later.

## Architecture description

We can describe all most important classes via Model-View-Controller concept.
###Controller
CLIApplication - operates basic logic of the CLI.

###View: User can interact with the application via stdin (input) or stdout (output). 
Parsing user's input is made by *CLICommandParser* class. It turns our raw input stream into string lexems 
which are transported to **Model**-representing classes.
Output part of **View** is dealed by classes which represent console commands.

###Model:
There are classes which control the code flow inside the application. Most notable of them is *CLIApplication* 
to whom control is transfered from main module. It does all initialization procedures has responsibility of 
manipulating events which happen on each iteration of user-machine session. Talking in terms of intercaction 
between user and terminal (i.e. Bash), by 'iteration' we mean all computer processes from the moment user provided 
all the information machine needs for starting doing anything (i.e. when we type "echo hello" and hit Enter - we 
did just enough in order for machine to start doing its job. But we can type "echo hel\" and hit Enter - in this case 
machine just has to wait for user to finish his or her input) and the moment when machine did everything on its part and 
waits user to input something in response. 

So, on each iteration there are several stages:

1. Get all input available and which is just enough for starting computing processes.

2. Split raw input data (string) into array of lexems, separate expressions.

3. Replace all environmental variables summoned with "$" as a prefix with their values.

4. Parse all lexems into sequences of command strings (which are basically a command's name and its parameters).

5. Execute commands in order in pipe sequence assigning output stream of currently ending command to input stream 
of the following command (it's kind of a definition of 'pipe' command sequence).

6. Show to user output stream of the last command executed (generally speaking it's not true in Bash as we can execute 
last command which does not use its input stream, but we can assume it's not the case for our task).

7. Return to the first item of this list.

Maybe it's a huge mistake but in our project items 1 through 4 are dealed through *CLICommandParser* while operating 
each incoming symbol one by one. 5 is done by *CLICommandQueue* - it builds the order of commands (represented
as *CLICommand*-based classes - those named with *Command* suffix) executed, plays with their streams (pipe magic) and returns resulting output, 
literally doing 6th item.

7th item is done in main loop of *CLIApplication*.

There are a couple of classes which are accessible by almost any other classes in our project, 
they are building blocks of code flow (*CLIException*-based ones) and metadata flow (*CLIEnvironment* struct, not
vastly used in this application yet).

For further reading on classes documentation you can go to _cli/doc_ folder and have a look at file _index.html_ in our browser.
