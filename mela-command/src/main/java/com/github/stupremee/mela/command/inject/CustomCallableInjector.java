package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.CommandCallable;
import com.google.inject.MembersInjector;

import java.lang.reflect.Field;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class CustomCallableInjector<T> implements MembersInjector<T> {

  private final CommandCallable parent;
  private final Group annotation;
  private final Field field;

  private CustomCallableInjector(CommandCallable parent, Group annotation, Field field) {
    this.parent = parent;
    this.annotation = annotation;
    this.field = field;
  }

  @Override
  public void injectMembers(T instance) {
    field.setAccessible(true);
    try {
      CommandCallable value = parent.selectChild(annotation.value())
          .orElseThrow(() -> new RuntimeException("Invalid group")); // TODO: 22.06.2019 besserer exception type && catchen
      field.set(instance, value);
    } catch (IllegalAccessException e) {
      e.printStackTrace(); // TODO logging
    }
  }

  static <T> CustomCallableInjector<T> create(CommandCallable parent, Group annotation, Field field) {
    return new CustomCallableInjector<>(parent, annotation, field);
  }
}
