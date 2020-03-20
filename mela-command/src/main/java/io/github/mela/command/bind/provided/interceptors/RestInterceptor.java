package io.github.mela.command.bind.provided.interceptors;

import com.google.inject.Singleton;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@Singleton
public class RestInterceptor extends MappingInterceptorAdapter<Rest> {

  @Override
  public void preprocess(@Nonnull Rest annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
    Arguments arguments = process.getArguments();
    StringBuilder builder = new StringBuilder();
    while (arguments.hasNext()) {
      builder.append(arguments.next());
    }
    process.requestMapping(Arguments.of("\"" + builder.toString().trim() + "\""));
  }

}
