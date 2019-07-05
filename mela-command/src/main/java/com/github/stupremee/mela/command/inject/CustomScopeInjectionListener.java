package com.github.stupremee.mela.command.inject;

import com.github.stupremee.mela.command.CommandGroup;
import com.github.stupremee.mela.command.inject.annotation.Scope;
import com.google.inject.TypeLiteral;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

import java.lang.reflect.Field;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CustomScopeInjectionListener implements TypeListener {

  private CustomScopeInjectionListener() {}

  @Override
  public <I> void hear(TypeLiteral<I> type, TypeEncounter<I> encounter) {
    Class<?> raw = type.getRawType();
    while (raw != null) {
      for (Field field : raw.getDeclaredFields()) {
        if (field.getType() == CommandGroup.class
            && field.isAnnotationPresent(Scope.class)) {
          Scope annotation = field.getAnnotation(Scope.class);
          CommandGroup root = encounter.getProvider(CommandGroup.class).get();
          encounter.register(new CustomScopeInjector<>(root, annotation, field));
        }
      }
      raw = raw.getSuperclass();
    }
  }

  public static CustomScopeInjectionListener create() {
    return new CustomScopeInjectionListener();
  }

}
