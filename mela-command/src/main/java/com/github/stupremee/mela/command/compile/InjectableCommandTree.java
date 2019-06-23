package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.inject.InjectionObjectHolder;

public interface InjectableCommandTree {

  InjectableCommandTree merge(InjectableCommandTree other);

  CommandTree inject(InjectionObjectHolder holder);

}
