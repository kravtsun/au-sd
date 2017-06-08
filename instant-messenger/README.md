# Instant Messenger
Simple peer-to-peer jabber-like chat messenger.

## Prerequisites
* Ubuntu (tested on ver. 16.04);
* JRE (tested on ver. 1.8);
* Connection to Internet for other dependencies.

## USAGE

```bash
# build
./gradlew installDist
# start server
build/install/instant-messenger/bin/server --host localhost --port 3034
# start client
build/install/instant-messenger/bin/client --host localhost --port 3034 --name name
```
