package com.github.stupremee.mela.command.compile;

import com.github.stupremee.mela.command.inject.InjectionObjectHolder;

public interface UninjectedCommandTree {

  UninjectedCommandTree merge(UninjectedCommandTree other);

  CommandTree inject(InjectionObjectHolder holder);

}
