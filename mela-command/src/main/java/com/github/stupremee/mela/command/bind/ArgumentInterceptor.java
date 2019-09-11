package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.process.MappingProcess;

import java.lang.annotation.Annotation;

public interface ArgumentInterceptor<T extends Annotation> {

  void interceptBefore(T annotation, MappingProcess process, CommandContext context);

  void interceptAfter(T annotation, MappingProcess process, CommandContext context);

}
