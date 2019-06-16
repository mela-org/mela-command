package com.github.stupremee.mela.command.discord.d4j;

import com.github.stupremee.mela.command.AbstractArgumentModule;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JMemberProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JRoleProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JTextChannelProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JUserProvider;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.*;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 13.05.19
 */
// TODO: 15.06.2019 Move implementation to other module, or project even
public final class DefaultD4JArgumentModule extends AbstractArgumentModule {

  private final DiscordClient client;

  private DefaultD4JArgumentModule(DiscordClient client) {
    this.client = client;
  }

  @Override
  protected void configure() {
    bind(DiscordClient.class)
        .toInstance(client);

    bindNameSpaceData(
        User.class, MessageChannel.class,
        PrivateChannel.class, TextChannel.class,
        Guild.class, User.class
    );

    bind(User.class)
        .toProvider(D4JUserProvider.instance());

    bind(Member.class)
        .toProvider(D4JMemberProvider.instance());

    bind(TextChannel.class)
        .toProvider(D4JTextChannelProvider.instance());

    bind(Role.class)
        .toProvider(D4JRoleProvider.instance());
  }

  public static DefaultD4JArgumentModule create(DiscordClient client) {
    return new DefaultD4JArgumentModule(client);
  }
}