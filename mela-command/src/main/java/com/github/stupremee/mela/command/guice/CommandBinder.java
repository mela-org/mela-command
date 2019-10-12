package com.github.stupremee.mela.command.guice;

import com.github.stupremee.mela.command.bind.ArgumentMapper;
import com.github.stupremee.mela.command.bind.CommandInterceptor;
import com.github.stupremee.mela.command.bind.ExceptionHandler;
import com.github.stupremee.mela.command.bind.MappingInterceptor;
import com.github.stupremee.mela.command.guice.annotation.ArgumentMappers;
import com.github.stupremee.mela.command.guice.annotation.CommandInterceptors;
import com.github.stupremee.mela.command.guice.annotation.ExceptionHandlers;
import com.github.stupremee.mela.command.guice.annotation.MappingInterceptors;
import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.multibindings.MapBinder;

import javax.annotation.Nonnull;

import java.lang.annotation.Annotation;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandBinder {

  private final CommandBindingNode root;
  private final MapBinder<Key, ArgumentMapper> mapperBinder;
  private final MapBinder<Class, MappingInterceptor> mappingInterceptorBinder;
  private final MapBinder<Class, CommandInterceptor> commandInterceptorBinder;
  private final MapBinder<Class, ExceptionHandler> handlerBinder;

  private CommandBinder(@Nonnull Binder binder) {
    this.root = new CommandBindingNode(checkNotNull(binder));

    this.mapperBinder = MapBinder.newMapBinder(binder, Key.class, ArgumentMapper.class, ArgumentMappers.class);
    this.mappingInterceptorBinder = MapBinder.newMapBinder(binder, Class.class, MappingInterceptor.class, MappingInterceptors.class);
    this.commandInterceptorBinder = MapBinder.newMapBinder(binder, Class.class, CommandInterceptor.class, CommandInterceptors.class);
    this.handlerBinder = MapBinder.newMapBinder(binder, Class.class, ExceptionHandler.class, ExceptionHandlers.class);
  }

  @Nonnull
  public static CommandBinder create(@Nonnull Binder binder) {
    return new CommandBinder(binder);
  }

  @Nonnull
  public CommandBindingNode root() {
    return root;
  }

}
