package io.github.mela.command.example.bind;

import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.MethodHandleCompiler;
import io.github.mela.command.bind.map.MappingProcessException;
import io.github.mela.command.compile.CommandCompiler;
import io.github.mela.command.core.ArgumentException;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandDispatcher;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.CommandInput;
import io.github.mela.command.core.DefaultDispatcher;
import io.github.mela.command.core.ImmutableGroup;
import io.github.mela.command.core.UnknownCommandException;
import io.github.mela.command.provided.ProvidedBindings;
import io.github.mela.command.provided.mappers.CommandInputMapper;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class Main {

  public static void main(String[] args) {
    CommandBindings standardLibrary = ProvidedBindings.get();
    CommandInputMapper inputMapper = new CommandInputMapper();
    CommandBindings inputMapperBinding = CommandBindings.builder()
        .bindMapper(CommandInput.class, inputMapper)
        .build();
    CommandBindings fileExplorerBindings = getFileExplorerBindings();

    CommandBindings bindings = CommandBindings.allOf(
        standardLibrary, fileExplorerBindings, inputMapperBinding
    );
    CommandCompiler compiler = MethodHandleCompiler.withBindings(bindings);
    CommandGroup root = ImmutableGroup.builder()
        .add(new ApplicationCommands())
        .add(new HelpCommand())
        .add(new ListCommand())
        .add(new CdCommand())
        .group("file", "f")
          .add(new FileCommands())
        .root()
        .compile(compiler);
    inputMapper.setGroup(root);
    CommandDispatcher dispatcher = DefaultDispatcher.create(root);
    run(dispatcher);
  }

  private static void run(CommandDispatcher dispatcher) {
    System.out.println(
        "Welcome to file-explorer. Type \"help\" to see a list of all available commands.");
    Scanner scanner = new Scanner(System.in);
    Path workingDirectory = Paths.get(".").toAbsolutePath().normalize();
    CommandContext context = CommandContext.create();
    context.put(Path.class, "working", workingDirectory);
    while (true) {
      System.out.printf("%s > ", context.get(Path.class, "working")
          .orElseThrow(AssertionError::new));
      String command = scanner.nextLine();
      try {
        dispatcher.dispatch(command, context);
      } catch (UnknownCommandException e) {
        System.out.println("Unknown command. Use \"help\" for help.");
      }
    }
  }

  private static CommandBindings getFileExplorerBindings() {
    return CommandBindings.builder()
        .bindMapper(Path.class, new PathMapper())
        .bindMappingInterceptor(Existent.class,
            new PathValidator<>(Files::exists, "Path must exist"))
        .bindMappingInterceptor(NonExistent.class,
            new PathValidator<>(Files::notExists, "Path must not exist"))
        .bindMappingInterceptor(File.class,
            new PathValidator<>(Files::isRegularFile, "Path must point to a file"))
        .bindMappingInterceptor(Directory.class,
            new PathValidator<>(Files::isDirectory, "Path must point to a directory"))
        .bindHandler(AccessDeniedException.class,
            (e, c) -> System.out.printf("Access denied: %s%n", e.getMessage()))
        .bindHandler(MappingProcessException.class,
            (e, c) -> System.out.printf("Invalid argument: %s%n", e.getMessage()))
        .bindHandler(ArgumentException.class,
            (e, c) -> System.out.println("Wrong number of arguments provided - use \"help <command>\" for help"))
        .bindHandler(IOException.class,
            (e, c) -> System.out.printf("An I/O problem occurred: %s%n", e.getMessage()))
        .build();
  }
}
