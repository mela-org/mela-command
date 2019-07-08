package com.github.stupremee.mela.command.inject.custom;

import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.GroupFinder;
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
      GroupFinder finder = GroupFinder.of(root, annotation.value());
      finder.findExact();
      CommandGroup value = finder.getResult(); // TODO: 08.07.2019 bessere exception
      if (!field.canAccess(instance))
        field.setAccessible(true);
      field.set(instance, value);
    } catch (IllegalAccessException | InaccessibleObjectException e) {
      e.printStackTrace(); // TODO logging
    }
  }
}
