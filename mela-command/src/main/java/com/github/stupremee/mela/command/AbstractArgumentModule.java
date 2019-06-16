package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.annotation.Sender;
import com.github.stupremee.mela.command.provider.Providers;
import com.sk89q.intake.parametric.AbstractModule;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 16.06.2019 come up with a better name
public abstract class AbstractArgumentModule extends AbstractModule {

  // bindings use the same class object, so no exceptions will occur; when using <?>, type inference fails
  @SuppressWarnings("unchecked")
  protected void bindNameSpaceData(Class... types) {
    for (Class type : types) {
      bind(type)
          .annotatedWith(Sender.class)
          .toProvider(Providers.newNamespaceDataProvider(type));
    }
  }

}
