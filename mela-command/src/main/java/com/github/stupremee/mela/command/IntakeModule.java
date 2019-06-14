package com.github.stupremee.mela.command;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.sk89q.intake.dispatcher.Dispatcher;
import com.sk89q.intake.dispatcher.SimpleDispatcher;
import com.sk89q.intake.parametric.Injector;
import com.sk89q.intake.parametric.ParametricBuilder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class IntakeModule extends AbstractModule {

  private final Injector injector;

  private IntakeModule(Injector injector) {
    this.injector = injector;
  }

  public static IntakeModule create(Injector intakeInjector) {
    return new IntakeModule(intakeInjector);
  }

  @Override
  protected void configure() {
    bind(Injector.class).toInstance(injector);
    bind(ParametricBuilder.class).toProvider(() -> new ParametricBuilder(injector)).in(Singleton.class);
    bind(Dispatcher.class).to(SimpleDispatcher.class);
  }
}
