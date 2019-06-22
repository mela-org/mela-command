package com.github.stupremee.mela.command.compile;

import java.util.Collection;

public interface UnboundCommandTree {

  UnboundCommandTree merge(UnboundCommandTree other);

  CommandTree bind(Collection<?> commandObjects);

}
