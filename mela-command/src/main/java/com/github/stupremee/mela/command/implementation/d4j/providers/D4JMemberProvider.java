package com.github.stupremee.mela.command.implementation.d4j.providers;

import com.github.stupremee.mela.command.implementation.MemberProvider;
import com.google.common.base.Preconditions;
import com.sk89q.intake.argument.ArgumentException;
import com.sk89q.intake.argument.ArgumentParseException;
import com.sk89q.intake.argument.CommandArgs;
import com.sk89q.intake.parametric.Provider;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.util.Snowflake;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 13.05.19
 */
public final class D4JMemberProvider implements MemberProvider<Member> {

  private static final class Lazy {
    private static final D4JMemberProvider INSTANCE = new D4JMemberProvider();
  }

  private D4JMemberProvider() {
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
    Matcher matcher = MENTION.matcher(name);
    if (matcher.matches()) {
      String userId = matcher.group(1);
      return guild.getMemberById(Snowflake.of(userId))
          .blockOptional()
          .orElseThrow(() -> new ArgumentParseException("Unknown member!"));
    }

    return guild.getMembers()
        .filter(member -> member.getUsername().startsWith(name))
        .toStream()
        .findFirst()
        .orElseThrow(() -> new ArgumentParseException("No member was found with this name!"));
  }

  @Override
  public List<String> getSuggestions(String prefix) {
    return Collections.emptyList();
  }

  public static Provider<Member> instance() {
    return Lazy.INSTANCE;
  }
}
