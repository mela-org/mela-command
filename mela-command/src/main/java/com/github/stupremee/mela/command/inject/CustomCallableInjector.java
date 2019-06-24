package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.inject.annotation.Group;
import com.google.inject.MembersInjector;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class CustomCallableInjector<T> implements MembersInjector<T> {

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
    try {
      CommandCallable value = parent.selectChild(annotation.value())
          .result().orElseThrow(() -> new RuntimeException("Invalid group")); // TODO: 22.06.2019 besserer exception type && catchen
      if (!field.canAccess(instance))
        field.setAccessible(true);
      field.set(instance, value);
    } catch (IllegalAccessException | InaccessibleObjectException e) {
      e.printStackTrace(); // TODO logging
    }
  }

  static <T> CustomCallableInjector<T> create(CommandCallable parent, Group annotation, Field field) {
    return new CustomCallableInjector<>(parent, annotation, field);
  }
}
