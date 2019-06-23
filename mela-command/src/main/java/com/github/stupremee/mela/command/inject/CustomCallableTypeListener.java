package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.CommandCallable;
import com.github.stupremee.mela.command.inject.annotation.Group;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.lang.reflect.Field;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CustomCallableTypeListener implements TypeListener {

  private CustomCallableTypeListener() {}

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    Class<?> raw = type.getRawType();
    while (raw != null) {
      for (Field field : raw.getDeclaredFields()) {
        if (field.getType() == CommandCallable.class
            && field.isAnnotationPresent(Group.class)) {
          Group annotation = field.getAnnotation(Group.class);
          CommandCallable parent = encounter.getProvider(CommandCallable.class).get();
          encounter.register(CustomCallableInjector.create(parent, annotation, field));
        }
      }
      raw = raw.getSuperclass();
    }
  }

  public static CustomCallableTypeListener create() {
    return new CustomCallableTypeListener();
  }

}
