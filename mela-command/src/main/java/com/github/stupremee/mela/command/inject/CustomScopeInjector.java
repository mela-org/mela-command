package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.inject.annotation.Scope;
import com.google.inject.MembersInjector;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class CustomScopeInjector<T> implements MembersInjector<T> {

  private final CommandGroup root;
  private final Scope annotation;
  private final Field field;

  CustomScopeInjector(CommandGroup root, Scope annotation, Field field) {
    this.root = root;
    this.annotation = annotation;
    this.field = field;
  }

  @Override
  public void injectMembers(T instance) {
    try {
      CommandGroup value = root.findChildScope(annotation.value())
          .orElseThrow(() -> new RuntimeException("Invalid group")); // TODO: 22.06.2019 besserer exception type && catchen
      if (!field.canAccess(instance))
        field.setAccessible(true);
      field.set(instance, value);
    } catch (IllegalAccessException | InaccessibleObjectException e) {
      e.printStackTrace(); // TODO logging
    }
  }
}
