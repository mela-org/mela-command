package io.github.mela.command.bind.provided;

import io.github.mela.command.bind.guice.CommandModule;
import io.github.mela.command.bind.provided.interceptors.*;
import io.github.mela.command.bind.provided.mappers.BooleanMapper;
import io.github.mela.command.bind.provided.mappers.CharacterMapper;
import io.github.mela.command.bind.provided.mappers.NumberMapper;
import io.github.mela.command.bind.provided.mappers.StringMapper;
import io.github.mela.command.bind.provided.mappers.providers.IntegerMapperProvider;

import java.math.BigDecimal;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class BasicModule extends CommandModule {

  @Override
  protected void configureModule() {
    bindMapper(String.class).toInstance(new StringMapper());
    bindMapper(float.class).toInstance(new NumberMapper<>(float.class, Float::parseFloat));
    bindMapper(Float.class).toInstance(new NumberMapper<>(Float.class, Float::valueOf));
    bindMapper(double.class).toInstance(new NumberMapper<>(double.class, Double::parseDouble));
    bindMapper(Double.class).toInstance(new NumberMapper<>(Double.class, Double::valueOf));
    bindMapper(BigDecimal.class).toInstance(new NumberMapper<>(BigDecimal.class, BigDecimal::new));
    bindMapperProvider().toInstance(new IntegerMapperProvider());
    CharacterMapper characterMapper = new CharacterMapper();
    bindMapper(char.class).toInstance(characterMapper);
    bindMapper(Character.class).toInstance(characterMapper);
    BooleanMapper booleanMapper = new BooleanMapper();
    bindMapper(boolean.class).toInstance(booleanMapper);
    bindMapper(Boolean.class).toInstance(booleanMapper);

    bindMappingInterceptor(Context.class).toInstance(new ContextInterceptor());
    bindMappingInterceptor(Default.class).toInstance(new DefaultInterceptor());
    bindMappingInterceptor(Flag.class).toInstance(new FlagInterceptor());
    bindMappingInterceptor(Range.class).toInstance(new RangeInterceptor());
    bindMappingInterceptor(Rest.class).toInstance(new RestInterceptor());
  }
}
