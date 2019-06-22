package com.github.stupremee.mela.command.inject;

import com.google.inject.Provider;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class SingletonProvider<T> implements Provider<T> {

  private T instance;

  @Override
  public final T get() {
    if (instance != null)
      return instance;

    instance = createInstance();
    return instance;
  }

  protected abstract T createInstance();
}
