package io.github.mela.command.bind.provided.interceptors;

import io.github.mela.command.bind.map.ArgumentChain;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import io.github.mela.command.bind.map.MappingProcess;
import io.github.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class JoinedRestInterceptor extends MappingInterceptorAdapter<JoinedRest> {

  @Override
  public void preprocess(@Nonnull JoinedRest annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
    ArgumentChain chain = process.getArguments();
    process.setArgumentToMap(() -> Stream.iterate("", (s) -> chain.hasNext(), (s) -> chain.next())
        .collect(Collectors.joining(annotation.value())));
  }

}
