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
public final class FlagParameter extends CommandParameter {

  private final String flagName;

   public FlagParameter(Type type, Map<Annotation, ArgumentInterceptor> interceptors, ArgumentMapper<?> mapper, String flagName) {
    super(type, interceptors, mapper);
    this.flagName = flagName;
  }

  @Override
  public MappingProcess process(int argumentIndex, Arguments arguments, CommandContext context) {
    String value = arguments.getFlagValue(flagName);
    if (value == null && getType() == boolean.class)
      value = "false";
    MappingProcess result = processSingle(value, context);
    result.skip();
    return result;
  }

  public String getFlagName() {
    return flagName;
  }

}
