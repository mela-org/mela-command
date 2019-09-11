package com.github.stupremee.mela.command.bind.parameter;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.ArgumentInterceptor;
import com.github.stupremee.mela.command.bind.ArgumentMapper;
import com.github.stupremee.mela.command.bind.process.MappingProcess;
import com.github.stupremee.mela.command.parse.Arguments;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CollectionParameter extends CommandParameter {

  public CollectionParameter(Type type, Map<Annotation, ArgumentInterceptor> interceptors, ArgumentMapper<?> mapper) {
    super(type, interceptors, mapper);
  }

  @Override
  public MappingProcess process(int argumentIndex, Arguments arguments, CommandContext context) {
    MappingProcess result = new MappingProcess(this);
    if (argumentIndex < arguments.size()) {
      int length = arguments.size() - argumentIndex;
      List<Object> collection = new ArrayList<>();
      for (; argumentIndex < length; argumentIndex++) {
        MappingProcess singleResult = processSingle(arguments.get(argumentIndex), context);
        collection.add(singleResult.getValue());
        if (!singleResult.isSuccessful()) {
          result.fail(singleResult.getError());
        }
      }
      result.setValue(List.copyOf(collection));
    } else {
      result.setValue(List.of());
    }
    return result;
  }
}
