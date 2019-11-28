package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.ArgumentValidationException;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class RangeInterceptor extends MappingInterceptorAdapter<Range> {

  @Override
  public void postprocess(@Nonnull Range annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
    Type type = process.getParameter().getType();
    if ((type == int.class || type == Integer.class)
        && process.isSet() && !process.isErroneous()) {
      int value = (int) process.getValue();
      int from = annotation.from();
      int to = annotation.to();
      if (value < from || value >= to) {
        process.fail(new ArgumentValidationException("Value " + value + " is out of range "
            + from + "-" + to, process.getParameter()));
      }
    }
  }
}
