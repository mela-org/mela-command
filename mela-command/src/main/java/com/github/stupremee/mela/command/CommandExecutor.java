package com.github.stupremee.mela.command;

import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.dispatcher.Dispatcher;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 14.06.2019 rename
public interface CommandExecutor {

  void execute(Dispatcher dispatcher, String command, Namespace namespace);

}
