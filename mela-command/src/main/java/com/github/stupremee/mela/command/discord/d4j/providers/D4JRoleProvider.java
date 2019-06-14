package com.github.stupremee.mela.command.discord.d4j.providers;

import com.github.stupremee.mela.command.discord.RoleProvider;
import com.google.common.base.Preconditions;
import com.sk89q.intake.argument.ArgumentParseException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.argument.MissingArgumentException;
import com.sk89q.intake.parametric.Provider;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Role;
import discord4j.core.object.util.Snowflake;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.regex.Matcher;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
public final class D4JRoleProvider implements RoleProvider<Role> {

  private static final class Lazy {

    private static final D4JRoleProvider INSTANCE = new D4JRoleProvider();
  }

  private D4JRoleProvider() {
  }

  @Nullable
  @Override
  public Role get(CommandArgs arguments, List<? extends Annotation> modifiers)
      throws MissingArgumentException, ArgumentParseException {
    String name = arguments.next();
    Guild guild = Preconditions.checkNotNull(arguments.getNamespace().get(Guild.class));
    Matcher matcher = MENTION.matcher(name);
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

  public static Provider<Role> instance() {
    return Lazy.INSTANCE;
  }
}
