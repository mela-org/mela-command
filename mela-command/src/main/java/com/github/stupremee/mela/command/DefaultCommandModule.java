package com.github.stupremee.mela.command;

import com.github.stupremee.mela.command.annotations.Sender;
import com.github.stupremee.mela.command.providers.MemberProvider;
import com.github.stupremee.mela.command.providers.NamespaceProvider;
import com.github.stupremee.mela.command.providers.RoleProvider;
import com.github.stupremee.mela.command.providers.TextChannelProvider;
import com.github.stupremee.mela.command.providers.UserProvider;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.sk89q.intake.parametric.AbstractModule;
import discord4j.core.DiscordClient;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Role;
import discord4j.core.object.entity.TextChannel;
import discord4j.core.object.entity.User;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 13.05.19
 */
public final class DefaultCommandModule extends AbstractModule {

  private final DiscordClient client;
  private final Injector injector;

  @Inject
  DefaultCommandModule(Injector injector, DiscordClient client) {
    this.client = client;
    this.injector = injector;
  }

  @Override
  protected void configure() {
    bind(DiscordClient.class)
        .toInstance(client);

    bind(Injector.class)
        .toInstance(injector);

    bind(User.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceProvider.create(User.class));

    bind(TextChannel.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceProvider.create(TextChannel.class));

    bind(Guild.class)
        .annotatedWith(Sender.class)
        .toProvider(NamespaceProvider.create(Guild.class));

    bind(User.class)
        .toProvider(UserProvider.instance());

    bind(Member.class)
        .toProvider(MemberProvider.instance());

    bind(TextChannel.class)
        .toProvider(TextChannelProvider.instance());

    bind(Role.class)
        .toProvider(RoleProvider.instance());
  }
}