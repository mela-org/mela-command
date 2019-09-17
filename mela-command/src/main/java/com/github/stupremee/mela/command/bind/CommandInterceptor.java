package com.github.stupremee.mela.command.bind;

import com.github.stupremee.mela.command.core.CommandContext;

import javax.annotation.Nonnull;
import java.lang.annotation.Annotation;

public interface CommandInterceptor<T extends Annotation> {

  boolean intercept(@Nonnull T annotation, @Nonnull CommandContext context) throws Throwable; // TODO: 24.06.2019 replace with actual logic

}
