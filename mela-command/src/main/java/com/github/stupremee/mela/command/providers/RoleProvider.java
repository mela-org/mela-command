package com.github.stupremee.mela.command.providers;

import com.google.common.base.Preconditions;
import com.sk89q.intake.argument.ArgumentParseException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.argument.MissingArgumentException;
import com.sk89q.intake.parametric.Provider;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Snowflake;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
public final class RoleProvider implements Provider<Role> {

  private static final Pattern PATTERN = Pattern.compile("<#?([0-9]+)>");

  private static final class Lazy {

    private static final RoleProvider INSTANCE = new RoleProvider();
  }

  private RoleProvider() {
  }

  @Override
  public boolean isProvided() {
    return false;
  }

  @Nullable
  @Override
  public Role get(CommandArgs arguments, List<? extends Annotation> modifiers)
      throws MissingArgumentException, ArgumentParseException {
    String name = arguments.next();
    Guild guild = Preconditions.checkNotNull(arguments.getNamespace().get(Guild.class));
    Matcher matcher = PATTERN.matcher(name);
    if (matcher.matches()) {
      String userId = matcher.group(1);
      return guild.getRoleById(Snowflake.of(userId))
          .blockOptional()
          .orElseThrow(() -> new ArgumentParseException("Unknown Role!"));
    }

    return guild.getRoles()
        .filter(channel -> channel.getName().startsWith(name))
        .toStream()
        .findFirst()
        .orElseThrow(() -> new ArgumentParseException("No role was found with this name!"));
  }

  @Override
  public List<String> getSuggestions(String prefix) {
    return Collections.emptyList();
  }

  public static Provider<Role> instance() {
    return Lazy.INSTANCE;
  }
}
