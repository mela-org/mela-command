package com.github.stupremee.mela.command.mapping;

public interface ArgumentMapper<T> {

  static <T> ArgumentMapper<T> singleton(T instance) {
    return null;
  }
}
