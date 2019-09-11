package com.github.stupremee.mela.command.bind.parameter;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.ArgumentInterceptor;
import com.github.stupremee.mela.command.bind.ArgumentMapper;
import com.github.stupremee.mela.command.bind.process.MappingProcess;
import com.github.stupremee.mela.command.parse.Arguments;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ArrayParameter extends CommandParameter {

  private final Class<?> componentType;

  public ArrayParameter(Type type, Map<Annotation, ArgumentInterceptor> interceptors, ArgumentMapper<?> mapper) {
    super(type, interceptors, mapper);
    this.componentType = ((Class<?>) type).getComponentType();
  }

  @Override
  public MappingProcess process(int argumentIndex, Arguments arguments, CommandContext context) {
    MappingProcess result = new MappingProcess(this);
    if (argumentIndex < arguments.size()) {
      int length = arguments.size() - argumentIndex;
      Object[] array = (Object[]) Array.newInstance(componentType, length);
      for (int arrayIndex = 0; argumentIndex < length; argumentIndex++, arrayIndex++) {
        MappingProcess singleResult = processSingle(arguments.get(argumentIndex), context);
        array[arrayIndex] = singleResult.getValue();
        if (!singleResult.isSuccessful()) {
          result.fail(singleResult.getError());
        }
      }
      result.setValue(array);
    } else {
      result.setValue(Array.newInstance(componentType, 0));
    }
    return result;
  }

  public Class<?> getComponentType() {
    return componentType;
  }
}
