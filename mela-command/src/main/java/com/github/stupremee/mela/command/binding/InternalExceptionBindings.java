package com.github.stupremee.mela.command.binding;

import com.github.stupremee.mela.command.ExceptionHandler;
import com.google.common.collect.Maps;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;

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

  private final SortedMap<Class<?>, ValueWrapper<?>> bindings;

  InternalExceptionBindings() {
    this.bindings = Maps.newTreeMap(CONCRETE_TO_ABSTRACT);
  }

  private InternalExceptionBindings(SortedMap<Class<?>, ValueWrapper<?>> bindings) {
    this.bindings = Maps.newTreeMap(bindings);
  }

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  @Override
  public <T extends Throwable> Optional<ExceptionHandler<T>> getHandler(Class<T> exceptionType) {
    ValueWrapper<T> wrapper = (ValueWrapper<T>) bindings.get(exceptionType);
    if (wrapper == null)
      wrapper = selectNearestSuperclassWrapper(exceptionType);
    return Optional.ofNullable(wrapper).map((value) -> value.handler);
  }

  @SuppressWarnings("unchecked") // type is always correct (map is encapsulated)
  private <T extends Throwable> ValueWrapper<T> selectNearestSuperclassWrapper(Class<T> exceptionType) {
    Class<?> superclass = exceptionType;
    while ((superclass = superclass.getSuperclass()) != null) {
      ValueWrapper<T> wrapper = (ValueWrapper<T>) bindings.get(superclass);
      if (wrapper != null && !wrapper.ignoreInheritance)
        return wrapper;
    }
    return null;
  }

  <T extends Throwable> void put(Class<T> exceptionType,
                                 Class<? extends ExceptionHandler<T>> handlerType,
                                 boolean ignoreInheritance) {
    bindings.put(exceptionType, new ValueWrapper<>(ignoreInheritance, handlerType));
  }

  void inject(Set<? extends ExceptionHandler<?>> handlers) {
    for (ValueWrapper value : bindings.values()) {
      for (ExceptionHandler handler : handlers) {
        if (handler.getClass() == value.handlerType) {
          value.handler = handler;
        }
      }
    }
  }

  InternalExceptionBindings copy() {
    return new InternalExceptionBindings(bindings);
  }

  private static final class ValueWrapper<T extends Throwable> {

    ExceptionHandler<T> handler;
    final boolean ignoreInheritance;
    final Class<? extends ExceptionHandler<T>> handlerType;

    ValueWrapper(boolean ignoreInheritance, Class<? extends ExceptionHandler<T>> handlerType) {
      this.ignoreInheritance = ignoreInheritance;
      this.handlerType = handlerType;
    }
  }
}