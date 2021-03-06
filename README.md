# mela-command
*mela-command* is a general purpose command parsing framework for Java. 
Like other *mela* modules, its aim is to reduce boilerplate code as much as possible.

The framework is inspired by [Intake](https://github.com/EngineHub/Intake) and implements 
similar ideas as [ACF](https://github.com/aikar/commands), but seeks to eliminate their 
downsides and to be much more extensible and flexible.

**Get started here:**
 - [Wiki Home](../../wiki)
 - [Setup](../../wiki/Setup) and [Quick Start](../../wiki/Quick_Start)
 - Guides:
   - [Core Framework](../../wiki/Core-Framework-Guide)
   - [Bind Framework](../../wiki/Bind-Framework-Guide)

## What is a command parsing framework?
A command in this context refers to a textual message sent by users in a simple, non-graphical 
user interface, that is translated to some action in code.
Commands are used in many applications: CLI (command line interfaces), social 
media or messenger bots, games and many more.

A command parsing framework takes an incoming command, parses its arguments - if any -
to a format that is easy to use in code and executes its corresponding action.

*mela-command* provides new, declarative ways of defining commands. 
This is what commands written using the bind framework look like:

```java
@Command(
  labels = "ban",  
  desc = "Bans a user.", 
  help = "Use \"ban @User\" to ban the mentioned user.",
  usage = "ban @User [--time <value>] <reason>"
)
@Requires(permission = "user.ban")
public void ban(
  @Context("server") Server server,
  @Flag("-time") @Default("1y") Duration time,
  User target,
  @Maybe String reason
) {
  server.ban(target, time);
  target.sendMessage("You have been banned.");
  if (reason != null) {
    target.sendMessage("Reason: " + reason);
  }
}
```
The body of this command only focuses on the command logic. Everything else that would 
usually be part of the command code (such as parsing the Duration and the User, 
checking whether the required permission "ban.user" is present, handling wrong arguments 
etc.) is determined by the method and parameter declarations and done externally.

## Dependency
*Still under construction...*

To try and use mela-command now, clone this repository and build it yourself:
```
git clone https://github.com/mela-org/mela-command.git
cd mela-command
gradlew publishToMavenLocal
```
Then you can add it as a dependency in your project.

Maven:
```xml
<depencies>
  <dependency>
    <groupId>io.github.mela</groupId>
    <artifactId>mela-command-MODULE</artifactId>
    <version>VERSION</version>
  </dependency>
</dependencies>
```
Gradle:
```gradle
repositories {
  mavenLocal()
}

dependencies {
  implementation("io.github.mela:mela-command-MODULE:VERSION")
}
```
(Replace `MODULE` with the module you need (i.e. `core`, `bind` or `provided`) and `VERSION` with the version (as long as mela-command isn't published, it's `1.0.0` by default.)

## Quick Overview
*mela-command* consists of two base parts, the core framework and the 
compile API. Both are found in the core module. 
The latter is used to connect higher-level command parsers with the lower-level 
core framework. The default implementation of this API is provided by the bind framework 
(found in the bind module).

This is just an extremely simplified overview of the features - reading the 
[Wiki](../../wiki) to get started is recommended.

### Core Framework
This is what a core framework command might look like that converts each of its arguments to an int (or 0,
if conversion fails), evaluates their sum and prints it to the console in a new line:
```java
public class PrintCommand extends CommandCallableAdapter {
  public PrintCommand() {
    // labels, description, help, usage (the last three are nullable and not required here)
    super(Set.of("print"), null, null, null);
  }

  @Override
  public void call(CommandArguments arguments, CommandContext context) {
    int sum = 0;
    while (arguments.hasNext()) {
      String next = arguments.nextString();
      sum += convert(next);
    }
    System.out.printf("Sum: %d%n", sum);
  }

  private int convert(String s) {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return 0;
    }
  }
}
```

### Bind framework
Although the core framework is efficient, simple, convenient to use and sufficient 
in many cases, it is not the main attraction of mela-command. 

The bind framework is a very high level abstraction of writing commands on
top of the core framework. 

The framework's core paradigm is **aspect-oriented programming (AOP)**. This means that
**argument mapping** (i.e., converting arguments to Java objects), **argument validation** 
(i.e., checking whether an argument fulfills certain conditions), **command interception** 
(i.e., checking whether the command can be executed in the first place) and 
**exception handling** (i.e., reacting to errors occurring along the way) are all done 
**separately** and do not happen in the execution code of a command. The framework instead 
looks at command method declarations and creates fitting `CommandCallable`s dynamically.

You've seen one hypothetical example of this at the top of this readme. 

This would be the bind framework equivalent of the simple command we made 
in the core framework:

```java
public class Commands {
  @Command(labels = "print")
  public void print(@Maybe int[] arguments) {
    int sum = 0;
    for (int value : arguments) {
      sum += value;
    }
    System.out.printf("Sum: %d%n", sum);
  }
}
```
Instead of figuring out the arguments inside the method, we just declare
the parameters as what we need them to be: a variable amount of ints (=> `int[]`) 
that default to 0 if anything goes wrong (=> `@Maybe`).

## Relation to Google Guice
This module has built-in components that make integration with the Dependency Injection 
framework [Guice](https://github.com/google/guice) very simple.

While no dependency on Guice is required to use any of mela-command's features, it is
recommended, as it aligns closely with mela's principles.

## Issues and Contributing
Contributions, requests and issue reports are always welcome.

The project is still in very early stages, so contribution guidelines and similar things
are not set up yet. If you are interested in helping with this project (by adding a 
feature, writing documentation or creating an extension) or if you just want to learn
more about, you may send me a message on Discord (Johnny#3826).
