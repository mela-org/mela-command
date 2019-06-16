package com.github.stupremee.mela.command;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.sk89q.intake.Intake;
import com.sk89q.intake.dispatcher.Dispatcher;
import com.sk89q.intake.dispatcher.SimpleDispatcher;
import com.sk89q.intake.parametric.Injector;
import com.sk89q.intake.parametric.Module;
import com.sk89q.intake.parametric.ParametricBuilder;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class IntakeModule extends AbstractModule {

  private final Injector injector;

  private IntakeModule(Injector injector) {
    this.injector = injector;
  }

  public static IntakeModule create(Module... intakeModules) {
    return create(createInjector(intakeModules));
  }

  public static IntakeModule create(Injector injector) {
    return new IntakeModule(injector);
  }

  private static Injector createInjector(Module... modules) {
    Injector injector = Intake.createInjector();
    for (Module module : modules)
      injector.install(module);
    return injector;
  }

  @Override
  protected void configure() {
    bind(Injector.class)
        .toInstance(injector);

    bind(ParametricBuilder.class)
        .toProvider(() -> new ParametricBuilder(injector))
        .in(Singleton.class);

    bind(Dispatcher.class)
        .to(SimpleDispatcher.class)
        .in(Singleton.class);
  }
}
