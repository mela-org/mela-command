package io.github.mela.command.bind.provided;

import io.github.mela.command.bind.guice.CommandModule;
import io.github.mela.command.bind.provided.interceptors.*;
import io.github.mela.command.bind.provided.mappers.*;
import io.github.mela.command.bind.provided.mappers.providers.*;

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
    bindMapperProvider().to(OptionalMapperProvider.class);

    bindMapper(String.class, Raw.class).to(RawStringMapper.class);

    bindMappingInterceptor(Context.class).to(ContextInterceptor.class);
    bindMappingInterceptor(Default.class).to(DefaultInterceptor.class);
    bindMappingInterceptor(Flag.class).to(FlagInterceptor.class);
    bindMappingInterceptor(Match.class).to(MatchInterceptor.class);
    bindMappingInterceptor(Maybe.class).to(MaybeInterceptor.class);
    bindMappingInterceptor(Range.class).to(RangeInterceptor.class);
    bindMappingInterceptor(Rest.class).to(RestInterceptor.class);
  }
}
