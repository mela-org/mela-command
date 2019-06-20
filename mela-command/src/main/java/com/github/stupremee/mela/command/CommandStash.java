package com.github.stupremee.mela.command;

import java.util.Optional;
import java.util.Set;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// @ImplementedBy
public interface CommandStash {

  Optional<CommandCallable> select(String command);

  Set<? extends CommandCallable> getAll();

}
