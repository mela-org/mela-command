package io.github.mela.command.bind.provided;

import io.github.mela.command.bind.guice.CommandModule;
import io.github.mela.command.bind.provided.interceptors.*;
import io.github.mela.command.bind.provided.mappers.*;
import io.github.mela.command.bind.provided.mappers.providers.ArrayMapperProvider;
import io.github.mela.command.bind.provided.mappers.providers.CollectionMapperProvider;
import io.github.mela.command.bind.provided.mappers.providers.MapMapperProvider;
import io.github.mela.command.bind.provided.mappers.providers.NumberMapperProvider;

import java.util.*;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class BasicModule extends CommandModule {

  @Override
  protected void configureModule() {
    bindMapper(String.class).to(StringMapper.class);
    bindMapper(char.class).to(CharacterMapper.class);
    bindMapper(Character.class).to(CharacterMapper.class);
    bindMapper(boolean.class).to(BooleanMapper.class);
    bindMapper(Boolean.class).to(BooleanMapper.class);

    bindMapperProvider().to(NumberMapperProvider.class);
    bindMapperProvider().toInstance(new CollectionMapperProvider<>(ArrayList.class, ArrayList::new));
    bindMapperProvider().toInstance(new CollectionMapperProvider<>(LinkedHashSet.class, LinkedHashSet::new));
    bindMapperProvider().to(ArrayMapperProvider.class);
    bindMapperProvider().toInstance(new MapMapperProvider<>(LinkedHashMap.class, LinkedHashMap::new));

    bindMapper(String.class, Raw.class).to(RawStringMapper.class);

    bindMappingInterceptor(Context.class).toInstance(new ContextInterceptor());
    bindMappingInterceptor(Default.class).toInstance(new DefaultInterceptor());
    bindMappingInterceptor(Flag.class).toInstance(new FlagInterceptor());
    bindMappingInterceptor(Match.class).toInstance(new MatchInterceptor());
    bindMappingInterceptor(Maybe.class).toInstance(new MaybeInterceptor());
    bindMappingInterceptor(Range.class).toInstance(new RangeInterceptor());
    bindMappingInterceptor(Rest.class).toInstance(new RestInterceptor());
  }
}
