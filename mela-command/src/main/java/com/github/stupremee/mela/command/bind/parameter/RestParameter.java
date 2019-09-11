package com.github.stupremee.mela.command.bind.parameter;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.ArgumentInterceptor;
import com.github.stupremee.mela.command.bind.ArgumentMapper;
import com.github.stupremee.mela.command.bind.process.MappingProcess;
import com.github.stupremee.mela.command.parse.Arguments;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class RestParameter extends CommandParameter {

  public RestParameter(Type type, Map<Annotation, ArgumentInterceptor> interceptors, ArgumentMapper mapper) {
    super(type, interceptors, mapper);
  }

  @Override
  public MappingProcess process(int argumentIndex, Arguments arguments, CommandContext context) {
    return argumentIndex < arguments.size()
        ? processSingle(rawArgsFrom(arguments, argumentIndex), context)
        : processSingle("", context);
  }

  private String rawArgsFrom(Arguments arguments, int index) {
    return arguments.getRawArgs().replaceFirst(
        String.join("\\s+", arguments.getArguments().subList(index, arguments.size())), ""
    ).trim();
  }
}
