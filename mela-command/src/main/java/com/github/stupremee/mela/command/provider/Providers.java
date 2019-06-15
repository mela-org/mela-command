package com.github.stupremee.mela.command.provider;

import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.parametric.Provider;
import com.sk89q.intake.parametric.ProvisionException;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class Providers {

  private Providers() {
  }

  public static Provider<CommandArgs> newCommandArgsProvider() {
    return fromArgs(Function.identity());
  }

  public static Provider<Namespace> newNamespaceProvider() {
    return fromArgs(CommandArgs::getNamespace);
  }

  public static Provider<Integer> newArgSizeProvider() {
    return fromArgs(CommandArgs::size);
  }

  public static Provider<Map> newFlagsProvider() {
    return fromArgs(CommandArgs::getFlags);
  }

  public static <T> Provider<T> newNamespaceDataProvider(Class<T> keyType) {
    return fromArgs((args) -> args.getNamespace().get(keyType));
  }

  public static Provider<?> newNameSpaceDataProvider(Object key) {
    return fromArgs((args) -> args.getNamespace().get(key));
  }

  public static <T> Provider<T> fromArgs(Function<CommandArgs, T> function) {
    return new ProvidedArgsProvider<>(function);
  }

  private static class ProvidedArgsProvider<T> implements EmptySuggestionProvider<T>, NonConsumingProvider<T> {
    final Function<CommandArgs, T> function;

    private ProvidedArgsProvider(Function<CommandArgs, T> function) {
      this.function = function;
    }

    @Nullable
    @Override
    public T get(CommandArgs arguments, List<? extends Annotation> modifiers) throws ProvisionException {
      T t = function.apply(arguments);
      if (t == null)
        throw new ProvisionException("Could not provide element for " + arguments + ": it doesn't exist");
      return t;
    }
  }

}
