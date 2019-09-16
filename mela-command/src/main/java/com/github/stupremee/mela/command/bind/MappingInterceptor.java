package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.CommandContext;
import com.github.stupremee.mela.command.bind.process.MappingProcess;

import java.lang.annotation.Annotation;

public interface MappingInterceptor<T extends Annotation> {

  void preprocess(T annotation, MappingProcess process, CommandContext context);

  void postprocess(T annotation, MappingProcess process, CommandContext context);

}
