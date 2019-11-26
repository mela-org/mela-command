package io.github.mela.command.bind.guice;

import com.google.inject.Provider;

import javax.annotation.Nonnull;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class LazySingletonProvider<T> implements Provider<T> {

  private T instance;

  @Override
  public final T get() {
    if (instance == null)
      instance = createInstance();
    return instance;
  }

  @Nonnull
  protected abstract T createInstance();
}
