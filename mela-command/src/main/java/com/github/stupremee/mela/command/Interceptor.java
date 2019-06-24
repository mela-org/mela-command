package com.github.stupremee.mela.command;

import java.lang.annotation.Annotation;

public interface Interceptor<T extends Annotation> {

  boolean intercept(CommandContext context) throws Exception; // TODO: 24.06.2019 replace with actual logic
}
