package io.github.mela.command.bind.provided;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.CommandBindingsBuilder;
import io.github.mela.command.bind.provided.interceptors.Context;
import io.github.mela.command.bind.provided.interceptors.ContextInterceptor;
import io.github.mela.command.bind.provided.interceptors.Default;
import io.github.mela.command.bind.provided.interceptors.DefaultInterceptor;
import io.github.mela.command.bind.provided.interceptors.Flag;
import io.github.mela.command.bind.provided.interceptors.FlagInterceptor;
import io.github.mela.command.bind.provided.interceptors.Match;
import io.github.mela.command.bind.provided.interceptors.MatchInterceptor;
import io.github.mela.command.bind.provided.interceptors.Maybe;
import io.github.mela.command.bind.provided.interceptors.MaybeInterceptor;
import io.github.mela.command.bind.provided.interceptors.Range;
import io.github.mela.command.bind.provided.interceptors.RangeInterceptor;
import io.github.mela.command.bind.provided.interceptors.Rest;
import io.github.mela.command.bind.provided.interceptors.RestInterceptor;
import io.github.mela.command.bind.provided.mappers.BooleanMapper;
import io.github.mela.command.bind.provided.mappers.CharacterMapper;
import io.github.mela.command.bind.provided.mappers.CommandContextMapper;
import io.github.mela.command.bind.provided.mappers.Raw;
import io.github.mela.command.bind.provided.mappers.RawStringMapper;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.bind.provided.mappers.providers.ArrayMapperProvider;
import io.github.mela.command.bind.provided.mappers.providers.CollectionMapperProvider;
import io.github.mela.command.bind.provided.mappers.providers.MapMapperProvider;
import io.github.mela.command.bind.provided.mappers.providers.NeverReachMapperProvider;
import io.github.mela.command.bind.provided.mappers.providers.NumberMapperProvider;
import io.github.mela.command.bind.provided.mappers.providers.OptionalMapperProvider;
import io.github.mela.command.core.CommandContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public final class ProvidedBindings {

  private ProvidedBindings() {}

  // unbound: CommandInputMapper and LogInterceptor
  public static CommandBindingsBuilder createBuilder() {
    return CommandBindings.builder()
        .bindMapper(String.class, new StringMapper())
        .bindMapper(char.class, new CharacterMapper())
        .bindMapper(Character.class, new CharacterMapper())
        .bindMapper(boolean.class, new BooleanMapper())
        .bindMapper(String.class, Raw.class, new RawStringMapper())
        .bindMapper(CommandContext.class, new CommandContextMapper())
        .bindMapperProvider(new NeverReachMapperProvider(
            (type) -> type.getAnnotatedType().isAnnotationPresent(Context.class)))
        .bindMapperProvider(new NumberMapperProvider())
        .bindMapperProvider(new CollectionMapperProvider<>(ArrayList.class, Lists::newArrayList))
        .bindMapperProvider(new CollectionMapperProvider<>(
            LinkedHashSet.class, Sets::newLinkedHashSet))
        .bindMapperProvider(new ArrayMapperProvider())
        .bindMapperProvider(new MapMapperProvider<>(LinkedHashMap.class, Maps::newLinkedHashMap))
        .bindMapperProvider(new OptionalMapperProvider())
        .bindMappingInterceptor(Context.class, new ContextInterceptor())
        .bindMappingInterceptor(Default.class, new DefaultInterceptor())
        .bindMappingInterceptor(Flag.class, new FlagInterceptor())
        .bindMappingInterceptor(Match.class, new MatchInterceptor())
        .bindMappingInterceptor(Maybe.class, new MaybeInterceptor())
        .bindMappingInterceptor(Range.class, new RangeInterceptor())
        .bindMappingInterceptor(Rest.class, new RestInterceptor());
  }



}
