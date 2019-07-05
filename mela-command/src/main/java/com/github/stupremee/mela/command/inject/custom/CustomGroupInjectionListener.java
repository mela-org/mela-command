package com.github.stupremee.mela.command.inject.custom;

import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.inject.annotation.Group;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.lang.reflect.Field;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CustomGroupInjectionListener implements TypeListener {

  private CustomGroupInjectionListener() {}

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    Class<?> raw = type.getRawType();
    while (raw != null) {
      for (Field field : raw.getDeclaredFields()) {
        if (field.getType() == CommandGroup.class
            && field.isAnnotationPresent(Group.class)) {
          Group annotation = field.getAnnotation(Group.class);
          CommandGroup root = encounter.getProvider(CommandGroup.class).get();
          encounter.register(new CustomGroupInjector<>(root, annotation, field));
        }
      }
      raw = raw.getSuperclass();
    }
  }

  public static CustomGroupInjectionListener create() {
    return new CustomGroupInjectionListener();
  }

}
