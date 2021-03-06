package io.github.mela.command.bind.map;

import static com.google.common.base.Preconditions.checkNotNull;


import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.TargetType;
import io.github.mela.command.bind.parameter.CommandParameter;
import io.github.mela.command.core.CommandArguments;
import io.github.mela.command.core.CommandContext;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
@SuppressWarnings("rawtypes")
public final class MappingProcessor {

  private final TargetType type;
  private final ArgumentMapper<?> mapper;
  private final Map<Annotation, MappingInterceptor> interceptors;

  private MappingProcessor(
      TargetType type,
      ArgumentMapper<?> mapper,
      Map<Annotation, MappingInterceptor> interceptors
  ) {
    this.type = type;
    this.mapper = mapper;
    this.interceptors = interceptors;
  }

  @Nonnull
  public static MappingProcessor fromParameter(
      @Nonnull CommandBindings bindings, @Nonnull CommandParameter parameter) {
    return create(bindings, parameter.getTargetType(),
        Sets.newLinkedHashSet(Arrays.asList(parameter.getAnnotations())));
  }

  @Nonnull
  public static MappingProcessor fromTargetType(
      @Nonnull CommandBindings bindings, @Nonnull TargetType type) {
    return create(bindings, type, Sets.newLinkedHashSet());
  }

  @SuppressWarnings("unchecked")
  private static MappingProcessor create(
      CommandBindings bindings, TargetType type, Set<Annotation> annotations) {
    annotations.addAll(Arrays.asList(type.getAnnotatedType().getAnnotations()));
    ArgumentMapper mapper = bindings.getMapper(type);
    if (mapper == null) {
      throw new MissingMapperBindingError(
          "Invalid type: missing ArgumentMapper binding for type " + type.getTypeKey());
    }
    Map<Annotation, MappingInterceptor> interceptors = getInterceptors(bindings, annotations);
    interceptors.forEach((annotation, interceptor) -> interceptor.verify(annotation, type));
    return new MappingProcessor(type, mapper, interceptors);
  }

  private static Map<Annotation, MappingInterceptor> getInterceptors(
      CommandBindings bindings, Set<Annotation> annotations) {
    Map<Annotation, MappingInterceptor> interceptors = Maps.newLinkedHashMap();
    for (Annotation annotation : annotations) {
      MappingInterceptor<?> interceptor =
          bindings.getMappingInterceptor(annotation.annotationType());
      if (interceptor != null) {
        interceptors.put(annotation, interceptor);
      }
    }
    return interceptors;
  }

  @Nullable
  public Object process(@Nonnull CommandArguments arguments, @Nonnull CommandContext commandContext)
      throws Throwable {
    checkNotNull(commandContext);
    MappingProcess process = MappingProcess.create(type, arguments);
    intercept(process, commandContext, true);
    CommandArguments lastMappingArgs = process.getArgumentsToMap();
    if (!process.isErroneous() && !process.isSet()) {
      map(commandContext, process);
    }
    intercept(process, commandContext, false);
    return finishProcess(lastMappingArgs, commandContext, process);
  }

  private Object finishProcess(
      CommandArguments lastMappingArgs,
      CommandContext commandContext,
      MappingProcess process
  ) throws Throwable {
    if (process.isErroneous()) {
      throw process.getError();
    } else if (!process.isSet()) {
      if (process.getArgumentsToMap() != lastMappingArgs) {
        map(commandContext, process);
        return finishProcess(process.getArguments(), commandContext, process);
      } else {
        throw MappingProcessException.create("Unsatisfied value: No value was set for "
            + type + ". The cause is most likely a logic error in the program.", type.getType());
      }
    } else {
      return process.getValue();
    }
  }

  private void map(CommandContext commandContext, MappingProcess process) {
    try {
      Object value = mapper.map(process.getArgumentsToMap(), commandContext);
      process.setValue(value);
    } catch (Throwable throwable) {
      process.fail(throwable);
    }
  }

  @SuppressWarnings("unchecked")
  private void intercept(MappingProcess process, CommandContext context, boolean before) {
    for (Map.Entry<Annotation, MappingInterceptor> entry : interceptors.entrySet()) {
      Annotation annotation = entry.getKey();
      MappingInterceptor interceptor = entry.getValue();
      if (before) {
        interceptor.preprocess(annotation, process, context);
      } else {
        interceptor.postprocess(annotation, process, context);
      }
    }
  }
}
