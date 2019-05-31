package com.github.stupremee.mela.command.providers;

import com.google.common.base.Preconditions;
import com.sk89q.intake.argument.ArgumentException;
import com.sk89q.intake.argument.ArgumentParseException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.parametric.Provider;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.User;
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
 * @since 13.05.19
 */
public final class UserProvider implements Provider<User> {

  private static final Pattern PATTERN = Pattern.compile("<@!?([0-9]+)>");

  private static final class Lazy {

    private static final UserProvider INSTANCE = new UserProvider();
  }

  private UserProvider() {
  }

  @Override
  public boolean isProvided() {
    return false;
  }

  @Nullable
  @Override
  public User get(CommandArgs arguments, List<? extends Annotation> modifiers)
      throws ArgumentException {
    DiscordClient client = arguments.getNamespace().get(DiscordClient.class);
    Preconditions.checkNotNull(client, "client can't be null.");
    String name = arguments.next();
    Matcher matcher = PATTERN.matcher(name);
    if (matcher.matches()) {
      String userId = matcher.group(1);
      return client.getUserById(Snowflake.of(userId))
          .blockOptional()
          .orElseThrow(() -> new ArgumentParseException("Unknown user!"));
    }

    return client.getUsers()
        .filter(user -> user.getUsername().startsWith(name))
        .toStream()
        .findFirst()
        .orElseThrow(() -> new ArgumentParseException("No user found with this username."));
  }

  @Override
  public List<String> getSuggestions(String prefix) {
    return Collections.emptyList();
  }

  public static Provider<User> instance() {
    return Lazy.INSTANCE;
  }
}
