package com.github.stupremee.mela.command.handle;

import com.github.stupremee.mela.command.CommandContext;

public interface ExceptionHandler<T extends Throwable> {

  void handle(Throwable exception, CommandContext context); // TODO: 24.06.2019 replace with actual logic

}
