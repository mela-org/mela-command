package io.github.mela.command.guice;

import com.google.inject.Binder;
import com.google.inject.Key;
import com.google.inject.multibindings.MapBinder;
import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.CommandInterceptor;
import io.github.mela.command.bind.ExceptionHandler;
import io.github.mela.command.bind.map.MappingInterceptor;
import io.github.mela.command.guice.annotation.ArgumentMappers;
import io.github.mela.command.guice.annotation.CommandInterceptors;
import io.github.mela.command.guice.annotation.ExceptionHandlers;
import io.github.mela.command.guice.annotation.MappingInterceptors;

import javax.annotation.Nonnull;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class CommandBinder {

  private final CommandBindingNode root;
  // TODO: 24.11.2019 api for these
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
