package io.github.mela.command.example.bind;

import io.github.mela.command.bind.Command;
import io.github.mela.command.provided.interceptors.Flag;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ListCommand {

  @Command(
      labels = "list",
      desc = "Lists all files and directories in a directory",
      help = "Uses the working directoy if no directory is given. "
          + "To show a file tree (recursive contents), use the -t flag.",
      usage = "list [-t] <directory>"
  )
  public void list(@Flag("t") boolean tree, @Existent @Directory Path directory)
      throws IOException {
    if (tree) {
      Files.walkFileTree(directory, new TreePrinterVisitor());
    } else {
      try (Stream<Path> paths = Files.list(directory)) {
        paths.map(Path::getFileName)
            .map(Path::toString)
            .map(File.separator::concat)
            .forEach(System.out::println);
      }
    }
  }

  private static class TreePrinterVisitor extends SimpleFileVisitor<Path> {
    int depth = 0;

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
      printPath(dir);
      depth++;
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
      depth--;
      return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
      System.out.print("  ");
      printPath(file);
      return FileVisitResult.CONTINUE;
    }

    private void printPath(Path path) {
      for (int i = 0; i < depth; i++) {
        System.out.print("  ");
      }
      System.out.print(File.separatorChar);
      System.out.println(path.getFileName());
    }
  }

}
