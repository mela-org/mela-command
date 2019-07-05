package com.github.stupremee.mela.command;

import com.google.inject.ImplementedBy;

@ImplementedBy(DefaultDispatcher.class)
public interface Dispatcher {

  boolean dispatch(String command, CommandContext context);

}
