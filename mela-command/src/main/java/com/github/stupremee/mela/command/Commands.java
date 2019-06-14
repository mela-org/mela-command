package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.annotations.CommandClass;
import com.google.inject.Injector;
import com.sk89q.intake.dispatcher.Dispatcher;
import com.sk89q.intake.parametric.ParametricBuilder;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;

import java.util.List;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class Commands {

  public static void registerFromClasspath(Injector injector) {
    try (ScanResult result = scanClasspath()) {
      ParametricBuilder builder = injector.getInstance(ParametricBuilder.class);
      Dispatcher dispatcher = injector.getInstance(Dispatcher.class);
      getCommandClasses(result)
              .stream()
              .map(injector::getInstance)
              .forEach((object) -> builder.registerMethodsAsCommands(dispatcher, object));
    }
  }

  private static List<Class<?>> getCommandClasses(ScanResult scan) {
    return scan.getClassesWithAnnotation(CommandClass.class.getName()).loadClasses();
  }

  private static ScanResult scanClasspath() {
    return new ClassGraph()
            .enableClassInfo()
            .enableAnnotationInfo()
            .scan();
  }

}
