package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.compile.UncompiledGroup;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.function.Function;

public interface GroupAccumulator<T> {

  Set<? extends T> getChildren(T parent);

  Set<String> getNames(T group);

  Set<? extends CommandCallable> getCommands(T group);

  static GroupAccumulator<CommandGroup> of(CommandGroup group) {
    return of(CommandGroup::getChildren, CommandGroup::getNames, CommandGroup::getCommands);
  }

  static <T> GroupAccumulator<T> of(Function<T, Set<? extends T>> childrenFunction,
                                    Function<T, Set<String>> namesFunction,
                                    Function<T, Set<? extends CommandCallable>> commandsFunction) {
    return new GroupAccumulator<>() {
      @Override
      public Set<? extends T> getChildren(T parent) {
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

  static GroupAccumulator<UncompiledGroup> compiling(CommandCompiler compiler) {
    return of(
        UncompiledGroup::getChildren,
        UncompiledGroup::getNames,
        (group) -> shallowCompile(compiler, group)
    );
  }

  private static Set<CommandCallable> shallowCompile(CommandCompiler compiler, UncompiledGroup group) {
    return group.getUncompiledCommands().stream()
        .map((command) -> compiler.compile(command, group.getBindings()))
        .collect(Sets::newHashSet, Set::addAll, Set::addAll);
  }
}
