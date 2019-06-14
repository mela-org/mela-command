package com.github.stupremee.mela.command.implementation.d4j;

import com.github.stupremee.mela.command.AbstractCommandModule;
import com.github.stupremee.mela.command.annotations.Sender;
import com.github.stupremee.mela.command.implementation.NamespaceDataProvider;
import com.github.stupremee.mela.command.implementation.d4j.providers.D4JMemberProvider;
import com.github.stupremee.mela.command.implementation.d4j.providers.D4JRoleProvider;
import com.github.stupremee.mela.command.implementation.d4j.providers.D4JTextChannelProvider;
import com.github.stupremee.mela.command.implementation.d4j.providers.D4JUserProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.*;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 13.05.19
 */
public final class DefaultD4JCommandModule extends AbstractCommandModule {

  private final DiscordClient client;

  @Inject
  DefaultD4JCommandModule(DiscordClient client) {
    this.client = client;
  }

  @Override
  protected void configure() {
    bind(DiscordClient.class)
            .toInstance(client);

    bindParameter(DiscordClient.class)
        .toInstance(client);

    bindParameter(User.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceDataProvider.create(User.class));

    bindParameter(TextChannel.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceDataProvider.create(TextChannel.class));

    bindParameter(Guild.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceDataProvider.create(Guild.class));

    bindParameter(User.class)
        .toProvider(D4JUserProvider.instance());

    bindParameter(Member.class)
        .toProvider(D4JMemberProvider.instance());

    bindParameter(TextChannel.class)
        .toProvider(D4JTextChannelProvider.instance());

    bindParameter(Role.class)
        .toProvider(D4JRoleProvider.instance());
  }
}