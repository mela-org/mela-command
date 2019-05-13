package com.github.stupremee.mela.command.providers;

import com.google.common.base.Preconditions;
import com.sk89q.intake.argument.ArgumentException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.parametric.Provider;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
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
public final class MemberProvider implements Provider<Member> {

  private static final Pattern PATTERN = Pattern.compile("<@!?([0-9]+)>");

  private MemberProvider() {
  }

  @Override
  public boolean isProvided() {
    return false;
  }

  @Nullable
  @Override
  public Member get(CommandArgs arguments, List<? extends Annotation> modifiers)
      throws ArgumentException {
    String name = arguments.next();
    Guild guild = Preconditions.checkNotNull(arguments.getNamespace().get(Guild.class));
    Matcher matcher = PATTERN.matcher(name);
    if (matcher.matches()) {
      String userId = matcher.group(1);
      return guild.getMemberById(Snowflake.of(userId))
          .blockOptional()
          .orElseThrow(() -> new IllegalArgumentException("Unknown member!"));
    }

    return guild.getMembers()
        .filter(member -> member.getUsername().startsWith(name))
        .toStream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("No member was found with this name!"));
  }

  @Override
  public List<String> getSuggestions(String prefix) {
    return Collections.emptyList();
  }

  public static Provider<Member> create() {
    return new MemberProvider();
  }
}
