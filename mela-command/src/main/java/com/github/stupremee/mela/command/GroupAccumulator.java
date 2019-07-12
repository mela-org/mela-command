package com.github.stupremee.mela.command;

import java.util.Set;
import java.util.function.Function;

public interface GroupAccumulator<T> {

  Set<T> getChildren(T parent);

  Set<String> getNames(T group);

  Set<? extends CommandCallable> getCommands(T group);

  static GroupAccumulator<CommandGroup> of(CommandGroup group) {
    return of(CommandGroup::getChildren, CommandGroup::getNames, CommandGroup::getCommands);
  }

  static <T> GroupAccumulator<T> of(Function<T, Set<T>> childrenFunction,
                                    Function<T, Set<String>> namesFunction,
                                    Function<T, Set<? extends CommandCallable>> commandsFunction) {
    return new GroupAccumulator<>() {
      @Override
      public Set<T> getChildren(T parent) {
        return childrenFunction.apply(parent);
      }

      @Override
      public Set<String> getNames(T group) {
        return namesFunction.apply(group);
      }

      @Override
      public Set<? extends CommandCallable> getCommands(T group) {
        return commandsFunction.apply(group);
      }
    };
  }
}
