package com.github.stupremee.mela.command;

import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.dispatcher.Dispatcher;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 15.06.2019 rethink CommandExecutor approach - where to put help & error response, prefix
public abstract class CommandExecutorAdapter implements CommandExecutor {

  private final Dispatcher dispatcher;

  protected CommandExecutorAdapter(Dispatcher dispatcher) {
    this.dispatcher = dispatcher;
  }

  @Override
  public final void execute(Dispatcher dispatcher, String command, Namespace namespace) {

  }
}
