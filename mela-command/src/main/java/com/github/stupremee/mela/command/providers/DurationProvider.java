package com.github.stupremee.mela.command.providers;

import com.sk89q.intake.argument.ArgumentException;
import com.sk89q.intake.argument.ArgumentParseException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.parametric.Provider;
import com.sk89q.intake.parametric.ProvisionException;
import java.lang.annotation.Annotation;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.annotation.Nullable;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
public final class DurationProvider implements Provider<Duration> {

  private static final class Lazy {

    private static final DurationProvider INSTANCE = new DurationProvider();
  }

  private DurationProvider() {

  }

  @Override
  public boolean isProvided() {
    return false;
  }

  @Nullable
  @Override
  public Duration get(CommandArgs arguments, List<? extends Annotation> modifiers)
      throws ArgumentException {

    String argument = arguments.next();
    try {
      return Duration.parse(argument);
    } catch (DateTimeParseException cause) {
      // Ignored
    }

    try {
      return Duration.parse("p" + argument);
    } catch (DateTimeParseException cause) {
      // Ignored
    }

    try {
      return Duration.parse("pt" + argument);
    } catch (DateTimeParseException cause) {
      throw new ArgumentParseException("Invalid duration specified.");
    }
  }

  @Override
  public List<String> getSuggestions(String prefix) {
    return null;
  }

  public static Provider<Duration> instance() {
    return Lazy.INSTANCE;
  }
}
