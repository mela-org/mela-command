package com.github.stupremee.mela.command.provider;

import com.sk89q.intake.parametric.Provider;

import java.util.Collections;
import java.util.List;

public interface EmptySuggestionProvider<T> extends Provider<T> {

  List<String> EMPTY_SUGGESTIONS = Collections.emptyList();

  @Override
  default List<String> getSuggestions(String prefix) {
    return EMPTY_SUGGESTIONS;
  }
}
