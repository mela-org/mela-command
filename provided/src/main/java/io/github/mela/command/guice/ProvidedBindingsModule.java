package io.github.mela.command.guice;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.github.mela.command.provided.interceptors.Context;
import io.github.mela.command.provided.interceptors.ContextInterceptor;
import io.github.mela.command.provided.interceptors.Default;
import io.github.mela.command.provided.interceptors.DefaultInterceptor;
import io.github.mela.command.provided.interceptors.Flag;
import io.github.mela.command.provided.interceptors.FlagInterceptor;
import io.github.mela.command.provided.interceptors.Match;
import io.github.mela.command.provided.interceptors.MatchInterceptor;
import io.github.mela.command.provided.interceptors.Maybe;
import io.github.mela.command.provided.interceptors.MaybeInterceptor;
import io.github.mela.command.provided.interceptors.Range;
import io.github.mela.command.provided.interceptors.RangeInterceptor;
import io.github.mela.command.provided.interceptors.Remaining;
import io.github.mela.command.provided.interceptors.RemainingInterceptor;
import io.github.mela.command.provided.mappers.BooleanMapper;
import io.github.mela.command.provided.mappers.CharacterMapper;
import io.github.mela.command.provided.mappers.CommandContextMapper;
import io.github.mela.command.provided.mappers.CommandInputMapper;
import io.github.mela.command.provided.mappers.Raw;
import io.github.mela.command.provided.mappers.RawStringMapper;
import io.github.mela.command.provided.mappers.StringMapper;
import io.github.mela.command.provided.mappers.providers.ArrayMapperProvider;
import io.github.mela.command.provided.mappers.providers.CollectionMapperProvider;
import io.github.mela.command.provided.mappers.providers.EnumMapperProvider;
import io.github.mela.command.provided.mappers.providers.MapMapperProvider;
import io.github.mela.command.provided.mappers.providers.NeverReachMapperProvider;
import io.github.mela.command.provided.mappers.providers.NumberMapperProvider;
import io.github.mela.command.provided.mappers.providers.OptionalMapperProvider;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandInput;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class ProvidedBindingsModule extends CommandBindingsModule {

  // unbound: log interceptor
  @Override
  protected void configure() {
    bindMapper(String.class).to(StringMapper.class);
    bindMapper(char.class).to(CharacterMapper.class);
    bindMapper(Character.class).to(CharacterMapper.class);
    bindMapper(boolean.class).to(BooleanMapper.class);
    bindMapper(Boolean.class).to(BooleanMapper.class);

    bindMapperProvider()
        .toInstance(new NeverReachMapperProvider(
            (type) -> type.getAnnotatedType().isAnnotationPresent(Context.class)));
    bindMapperProvider().to(NumberMapperProvider.class);
    bindMapperProvider()
        .toInstance(new CollectionMapperProvider<>(ArrayList.class, Lists::newArrayList));
    bindMapperProvider()
        .toInstance(new CollectionMapperProvider<>(LinkedHashSet.class, Sets::newLinkedHashSet));
    bindMapperProvider().to(ArrayMapperProvider.class);
    bindMapperProvider()
        .toInstance(new MapMapperProvider<>(LinkedHashMap.class, Maps::newLinkedHashMap));
    bindMapperProvider().to(OptionalMapperProvider.class);
    bindMapperProvider().to(EnumMapperProvider.class);

    bindMapper(String.class, Raw.class).to(RawStringMapper.class);

    bindMapper(CommandContext.class).to(CommandContextMapper.class);
    bindMapper(CommandInput.class).to(CommandInputMapper.class);

    bindMappingInterceptor(Context.class).to(ContextInterceptor.class);
    bindMappingInterceptor(Default.class).to(DefaultInterceptor.class);
    bindMappingInterceptor(Flag.class).to(FlagInterceptor.class);
    bindMappingInterceptor(Match.class).to(MatchInterceptor.class);
    bindMappingInterceptor(Maybe.class).to(MaybeInterceptor.class);
    bindMappingInterceptor(Range.class).to(RangeInterceptor.class);
    bindMappingInterceptor(Remaining.class).to(RemainingInterceptor.class);
  }
}
