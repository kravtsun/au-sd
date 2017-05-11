# Doomulike
Second homework project, roguelike-like game named **Doomuelike**.

## Prerequisites
* Java SE Development Kit ver. ~8u131

## USAGE
### Ubuntu:
`./gradle run`
### Windows
run batch file `gradlew.bat`


## Architecture description
TODO in a separate hometask?

## Gameplay
Field tiles:
* `@` - your avatar

* `#` - wall

* `.` - floor (your avatar and all the creatures can walk through it).

* `$` - monsters (statues-like very lazy beings)

* `#` - chests with some nice items you can't wear, sorry.

* `&` - corpses.

One can rule one's avatar over the field (with arrow keys) hitting the walls and looting chests and killing monsters (trying to take the places of them).
You can receive valuable information after hitting on `h` button (Help screen) or `i` button (Status screen).

**Hint:** try hit `enter` once in a while. 
