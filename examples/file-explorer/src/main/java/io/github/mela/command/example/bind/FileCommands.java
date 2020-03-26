package io.github.mela.command.example.bind;

import io.github.mela.command.bind.Command;
import io.github.mela.command.provided.interceptors.Flag;
import io.github.mela.command.provided.interceptors.Maybe;
import io.github.mela.command.provided.interceptors.Remaining;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class FileCommands {

  @Command(
      labels = "create",
      desc = "Creates an empty file with the given name "
          + "and its parent directories if they don't exist",
      help = "Run \"file create\" with an absolute path or with a relative path to create "
          + "the file in the working directory. "
          + "Use the optional -e flag to write some initial content.",
      usage = "file create [-e <content>] <file>"
  )
  public void create(@Maybe @Flag("e") String content, @NonExistent Path path) throws IOException {
    Path parent = path.getParent();
    if (parent != null) {
      Files.createDirectories(parent);
    }
    Files.createFile(path);
    if (content != null) {
      edit(path, content);
    }
    System.out.printf("Created file %s%n", path);
  }

  @Command(
      labels = {"show", "read"},
      desc = "Displays the content of a file in plain text",
      help = "The provided path must be a file, not a directory.",
      usage = "file [show|read] <file>"
  )
  public void show(@Existent @File Path file) throws IOException {
    Files.readAllLines(file).forEach(System.out::println);
  }

  @Command(
      labels = {"edit", "write"},
      desc = "Edits the given file, replacing all of its content with the given text",
      help = "The provided path must be a file, not a directory.",
      usage = "file [edit|write] <file> <text>"
  )
  public void edit(@Existent @File Path file, @Remaining String text) throws IOException {
    Files.write(file, text.getBytes(StandardCharsets.UTF_8));
    System.out.printf("Edited file %s%n", file);
  }

  @Command(
      labels = {"delete", "del"},
      desc = "Deletes the given file or directory",
      help = "If you provide a directory, all sub-directories and "
          + "files in it will be deleted as well.",
      usage = "file [delete|del] <path>"
  )
  public void delete(@Existent Path path) throws IOException {
    if (Files.isDirectory(path)) {
      Files.walkFileTree(path, new FileDeleteVisitor());
    } else {
      Files.delete(path);
    }
    System.out.printf("Deleted %s%n", path);
  }

  private static class FileDeleteVisitor extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      Files.delete(file);
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      Files.delete(dir);
      return FileVisitResult.CONTINUE;
    }
  }

  @Command(
      labels = {"move", "mov"},
      desc = "Moves a file or directory to a given target path",
      help = "This command can also be used to rename a file or directory. "
          + "Note that the target path must not exist already.",
      usage = "file [move|mov] <source> <target>"
  )
  public void move(@Existent Path source, @NonExistent Path target) throws IOException {
    Files.move(source, target);
    System.out.printf("Moved %s to %s%n", source, target);
  }

}
