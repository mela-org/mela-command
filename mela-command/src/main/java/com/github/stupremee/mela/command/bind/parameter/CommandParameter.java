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
public class CommandParameter {

  private final Map<Annotation, ArgumentInterceptor> interceptors;
  private final ArgumentMapper<?> mapper;
  private final Type type;

  public CommandParameter(Type type, Map<Annotation, ArgumentInterceptor> interceptors, ArgumentMapper<?> mapper) {
    this.type = type;
    this.interceptors = interceptors;
    this.mapper = mapper;
  }

  public MappingProcess process(int argumentIndex, Arguments arguments, CommandContext context) {
    return processSingle(arguments.get(argumentIndex), context);
  }

  public MappingProcess processSingle(String argument, CommandContext context) {
    MappingProcess process = new MappingProcess(this);
    interceptBefore(process, context);
    try {
      process.setValue(mapper.map(argument, context));
    } catch (Throwable throwable) {
      process.fail(throwable);
    }
    interceptAfter(process, context);
    return process;
  }

  private void interceptBefore(MappingProcess process, CommandContext context) {
    interceptors.forEach((annotation, interceptor) -> interceptor.interceptBefore(annotation, process, context));
  }

  private void interceptAfter(MappingProcess process, CommandContext context) {
    interceptors.forEach((annotation, interceptor) -> interceptor.interceptAfter(annotation, process, context));
  }

  public Type getType() {
    return type;
  }
}
