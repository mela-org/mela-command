package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class RestInterceptor extends MappingInterceptorAdapter<Rest> {

  @Override
  public void preprocess(
      @Nonnull Rest annotation,
      @Nonnull MappingProcess process,
      @Nonnull CommandContext context
  ) {
    CommandArguments arguments = process.getArguments();
    StringBuilder builder = new StringBuilder();
    while (arguments.hasNext()) {
      builder.append(arguments.next());
    }
    process.requestMapping(new RestArguments(builder.toString().trim()));
  }

  private static class RestArguments extends CommandArguments {

    RestArguments(String arguments) {
      super(arguments);
    }

    @Override
    public String nextString() {
      while (hasNext()) {
        next();
      }
      return getRaw();
    }
  }

}
