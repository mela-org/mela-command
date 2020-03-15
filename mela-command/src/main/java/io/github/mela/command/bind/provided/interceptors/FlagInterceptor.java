package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class FlagInterceptor extends MappingInterceptorAdapter<Flag> {

  @Override
  public void preprocess(@Nonnull Flag annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    process.getContext().put(String[].class, "flag-names", annotation.value());
    Arguments arguments = process.getArguments();
    int flagPosition = findFlag(arguments, Set.of(annotation.value()));
    Type type = process.getTargetType().getType();

    if (type == boolean.class || type == Boolean.class) {
      process.setValue(flagPosition != -1);
    } else {
      process.setArgumentToMap(() -> {


      });
    }
  }

  private int findFlag(Arguments arguments, Set<String> names) {
    int initialPosition = arguments.currentPosition();
    arguments.jumpTo(0);
    while (arguments.hasNext()) {
      String argument = arguments.next();
      if (names.contains(argument)) {
        int flagPosition = arguments.currentPosition();
        arguments.jumpTo(initialPosition);
        return flagPosition;
      }
    }
    return -1;
  }

}
