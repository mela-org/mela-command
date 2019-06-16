package com.github.stupremee.mela.command.intake;

import com.github.stupremee.mela.command.provider.ConsumingProvider;
import com.github.stupremee.mela.command.provider.EmptySuggestionProvider;
import com.sk89q.intake.argument.ArgumentException;
import com.sk89q.intake.argument.CommandArgs;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class CustomArgumentProvider implements EmptySuggestionProvider<CustomArgument>, ConsumingProvider<CustomArgument> {

  @Nullable
  @Override
  public CustomArgument get(CommandArgs arguments, List<? extends Annotation> modifiers) throws ArgumentException {
    return new CustomArgument(arguments.next());
  }

}
