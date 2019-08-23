# Building from Source Code

## tl;dr

```bash
export JPACKAGE_HOME="/Users/brian/Applications/jdk-14.jdk/Contents/Home"
source my-env-config.sh
gradle jpackage
```

## Instructions

This project can be built on macOS, Linux, and Windows (We actually tested all of them). We primarily develop on _macOS_ and these build instructions reflect that. They should be essentially identical on Linux.

### Set up

Before you build, run `gradle wrapper --gradle-version 5.6`

1. This build is for [Java 11 LTS](https://openjdk.java.net/projects/jdk/11/), so install that before building. Any flavor of Java 11+ should work, but we are developing against [AdoptOpenJDK 11](https://adoptopenjdk.net).
2. Run `gradlew check` to run tests and verify environment.
3. Download [jpackage](https://jdk.java.net/jpackage/). 
4. Define `JPACKAGE_HOME` environment variable that points to the location of the jpackage JVM:

```bash
# bash
export JPACKAGE_HOME="/Users/brian/Applications/jdk-14.jdk/Contents/Home"

# fish
set -x JPACKAGE_HOME "/Users/brian/Applications/jdk-14.jdk/Contents/Home"

# cmd
set JPACKAGE_HOME="C:\Users\brian\Applications\jdk-14"
```
