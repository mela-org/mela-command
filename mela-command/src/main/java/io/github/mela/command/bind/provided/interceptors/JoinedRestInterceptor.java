package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.Arguments;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.ContextMap;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class JoinedRestInterceptor extends MappingInterceptorAdapter<JoinedRest> {

  @Override
  public void preprocess(@Nonnull JoinedRest annotation, @Nonnull MappingProcess process, @Nonnull ContextMap context) {
    Arguments arguments = process.getArguments();
    process.setArgumentToMap(() -> Stream.iterate("", (s) -> arguments.hasNext(), (s) -> arguments.next())
        .collect(Collectors.joining(annotation.value())));
  }

}
