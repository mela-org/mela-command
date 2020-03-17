package io.github.mela.command.bind.provided;

import io.github.mela.command.bind.guice.CommandModule;
import io.github.mela.command.bind.provided.interceptors.*;
import io.github.mela.command.bind.provided.mappers.BooleanMapper;
import io.github.mela.command.bind.provided.mappers.CharacterMapper;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.bind.provided.mappers.providers.NumberMapperProvider;

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

    bindMappingInterceptor(Context.class).toInstance(new ContextInterceptor());
    bindMappingInterceptor(Default.class).toInstance(new DefaultInterceptor());
    bindMappingInterceptor(Flag.class).toInstance(new FlagInterceptor());
    bindMappingInterceptor(Match.class).toInstance(new MatchInterceptor());
    bindMappingInterceptor(Maybe.class).toInstance(new MaybeInterceptor());
    bindMappingInterceptor(Range.class).toInstance(new RangeInterceptor());
    bindMappingInterceptor(Rest.class).toInstance(new RestInterceptor());
  }
}
