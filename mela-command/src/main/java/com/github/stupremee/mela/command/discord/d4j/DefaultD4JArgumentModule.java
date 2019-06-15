package com.github.stupremee.mela.command.discord.d4j;

import com.github.stupremee.mela.command.annotation.Sender;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JMemberProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JRoleProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JTextChannelProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JUserProvider;
import com.sk89q.intake.parametric.AbstractModule;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.*;

import static com.github.stupremee.mela.command.provider.Providers.newNamespaceDataProvider;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 13.05.19
 */
// TODO: 15.06.2019 Move implementation to other module, or project even
public final class DefaultD4JArgumentModule extends AbstractModule {

  private final DiscordClient client;

  private DefaultD4JArgumentModule(DiscordClient client) {
    this.client = client;
  }

  @Override
  protected void configure() {
    bind(DiscordClient.class)
            .toInstance(client);

    bind(User.class)
            .annotatedWith(Sender.class)
            .toProvider(newNamespaceDataProvider(User.class));

    bind(MessageChannel.class)
            .annotatedWith(Sender.class)
            .toProvider(newNamespaceDataProvider(MessageChannel.class));

    bind(PrivateChannel.class)
            .annotatedWith(Sender.class)
            .toProvider(newNamespaceDataProvider(PrivateChannel.class));

    bind(TextChannel.class)
            .annotatedWith(Sender.class)
            .toProvider(newNamespaceDataProvider(TextChannel.class));

    bind(Guild.class)
            .annotatedWith(Sender.class)
            .toProvider(newNamespaceDataProvider(Guild.class));

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