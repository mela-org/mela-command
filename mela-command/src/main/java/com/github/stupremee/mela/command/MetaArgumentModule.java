package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.annotation.ArgumentSize;
import com.github.stupremee.mela.command.annotation.Flags;
import com.github.stupremee.mela.command.provider.Providers;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.argument.Namespace;
import com.sk89q.intake.parametric.AbstractModule;

import java.util.Map;


/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class MetaArgumentModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(Namespace.class)
            .toProvider(Providers.newNamespaceProvider());

    bind(CommandArgs.class)
            .toProvider(Providers.newCommandArgsProvider());

    bind(int.class)
            .annotatedWith(ArgumentSize.class)
            .toProvider(Providers.newArgSizeProvider());

    bind(Map.class)
            .annotatedWith(Flags.class)
            .toProvider(Providers.newFlagsProvider());
  }
}
