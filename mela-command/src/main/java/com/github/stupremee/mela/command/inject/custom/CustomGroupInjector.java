package com.github.stupremee.mela.command.inject.custom;

import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.inject.annotation.Group;
import com.github.stupremee.mela.command.util.GroupFinder;
import com.google.inject.MembersInjector;

import java.lang.reflect.Field;
import java.lang.reflect.InaccessibleObjectException;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
final class CustomGroupInjector<T> implements MembersInjector<T> {

  private final CommandGroup root;
  private final Group annotation;
  private final Field field;

  CustomGroupInjector(CommandGroup root, Group annotation, Field field) {
    this.root = root;
    this.annotation = annotation;
    this.field = field;
  }

  @Override
  public void injectMembers(T instance) {
    try {
      CommandGroup value = GroupFinder.findExact(annotation.value(), root)
          .orElseThrow(() -> new RuntimeException("Invalid group")); // TODO: 22.06.2019 besserer exception type && catchen
      if (!field.canAccess(instance))
        field.setAccessible(true);
      field.set(instance, value);
    } catch (IllegalAccessException | InaccessibleObjectException e) {
      e.printStackTrace(); // TODO logging
    }
  }
}
