package com.github.stupremee.mela.command.discord.d4j;

import com.github.stupremee.mela.command.annotation.Sender;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JMemberProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JRoleProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JTextChannelProvider;
import com.github.stupremee.mela.command.discord.d4j.providers.D4JUserProvider;
import com.github.stupremee.mela.command.provider.Providers;
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

  // bindings use the same class object, so no exceptions will occur; when using <?>, type inference fails
  @SuppressWarnings("unchecked")
  private void bindNameSpaceData(Class... types) {
    for (Class type : types) {
      bind(type)
              .annotatedWith(Sender.class)
              .toProvider(Providers.newNamespaceDataProvider(type));
    }
  }

  public static DefaultD4JArgumentModule create(DiscordClient client) {
    return new DefaultD4JArgumentModule(client);
  }
}