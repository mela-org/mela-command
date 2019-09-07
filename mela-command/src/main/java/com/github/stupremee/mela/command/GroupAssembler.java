package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.compile.CommandCompiler;
import com.github.stupremee.mela.command.compile.UncompiledGroup;
import com.google.common.collect.Sets;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.Function;

import static com.google.common.base.Preconditions.checkNotNull;

public interface GroupAssembler<T> {

  @Nonnull
  Set<? extends T> getChildren(@Nonnull T group);

  @Nonnull
  Set<String> getNames(@Nonnull T group);

  @Nonnull
  Set<? extends CommandCallable> getCommands(@Nonnull T group);

  @Nonnull
  static GroupAssembler<UncompiledGroup> compiling(@Nonnull CommandCompiler compiler) {
    checkNotNull(compiler);
    return GroupAssembler.of(
        UncompiledGroup::getChildren,
        UncompiledGroup::getNames,
        (group) -> shallowCompile(compiler, group)
    );
  }

  private static Set<CommandCallable> shallowCompile(CommandCompiler compiler, UncompiledGroup group) {
    return group.getUncompiledCommands().stream()
        .map(compiler::compile)
        .collect(Sets::newHashSet, Set::addAll, Set::addAll);
  }

  @Nonnull
  static GroupAssembler<CommandGroup> forGroup() {
    return GroupAssembler.of(CommandGroup::getChildren, CommandGroup::getNames, CommandGroup::getCommands);
  }

  @Nonnull
  static <T> GroupAssembler<T> of(@Nonnull Function<T, Set<? extends T>> childrenFunction,
                                  @Nonnull Function<T, Set<String>> namesFunction,
                                  @Nonnull Function<T, Set<? extends CommandCallable>> commandsFunction) {
    checkNotNull(childrenFunction);
    checkNotNull(namesFunction);
    checkNotNull(commandsFunction);
    return new GroupAssembler<>() {
      @Nonnull
      @Override
      public Set<? extends T> getChildren(@Nonnull T group) {
        return childrenFunction.apply(group);
      }

      @Nonnull
      @Override
      public Set<String> getNames(@Nonnull T group) {
        return namesFunction.apply(group);
      }

      @Nonnull
      @Override
      public Set<? extends CommandCallable> getCommands(@Nonnull T group) {
        return commandsFunction.apply(group);
      }
    };
  }
}
