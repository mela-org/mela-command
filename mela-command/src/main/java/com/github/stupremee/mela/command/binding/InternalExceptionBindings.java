package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class InternalExceptionBindings implements ExceptionBindings {

  private static final Comparator<Class<?>> CONCRETE_TO_ABSTRACT =
      (one, two) -> {
        if (one == two) {
          return 0;
        } else if (one.isAssignableFrom(two)) {
          return 1;
        } else {
          return -1;
        }
      };

  private final Map<Class<?>, HandlerWrapper<?>> bindings =
      Maps.newTreeMap(CONCRETE_TO_ABSTRACT);

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  @Override
  public <T extends Throwable> Optional<ExceptionHandler<T>> getHandler(Class<T> exceptionType) {
    HandlerWrapper<T> wrapper = (HandlerWrapper<T>) bindings.get(exceptionType);
    if (wrapper == null)
      wrapper = selectNearestSuperclassWrapper(exceptionType);
    return Optional.ofNullable(wrapper).map((w) -> w.handler);
  }

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  private <T extends Throwable> HandlerWrapper<T> selectNearestSuperclassWrapper(Class<T> exceptionType) {
    Class<?> superclass = exceptionType;
    while ((superclass = superclass.getSuperclass()) != null) {
      HandlerWrapper<T> wrapper = (HandlerWrapper<T>) bindings.get(superclass);
      if (wrapper != null && !wrapper.ignoreInheritance)
        return wrapper;
    }
    return null;
  }

  <T extends Throwable> void put(Class<T> exceptionType, HandlerWrapper<T> wrapper) {
    bindings.put(exceptionType, wrapper);
  }

  static final class HandlerWrapper<T extends Throwable> {
    private final boolean ignoreInheritance;
    private final ExceptionHandler<T> handler;

    private HandlerWrapper(boolean ignoreInheritance, ExceptionHandler<T> handler) {
      this.ignoreInheritance = ignoreInheritance;
      this.handler = handler;
    }

    static <T extends Throwable> HandlerWrapper<T> of(ExceptionHandler<T> handler, boolean ignoreInheritance) {
      return new HandlerWrapper<>(ignoreInheritance, handler);
    }
  }
}
