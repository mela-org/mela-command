package io.github.mela.command.bind.guice;

import com.google.inject.Provider;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public abstract class SingletonProvider<T> implements Provider<T> {

  private T instance;

  @Override
  public T get() {
    if (instance == null) {
      instance = create();
    }
    return instance;
  }

  protected abstract T create();
}
