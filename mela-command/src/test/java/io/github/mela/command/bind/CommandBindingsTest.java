package io.github.mela.command.bind;

import io.github.mela.command.bind.map.ArgumentMapper;
import io.github.mela.command.bind.map.ArgumentMapperProvider;
import io.github.mela.command.bind.map.MappingInterceptor;
import io.github.mela.command.bind.map.MappingInterceptorAdapter;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandBindingsTest {

  @Test
  void testArgumentMapperBinding() {
    ArgumentMapper<String> mapper = (a, c) -> "first";
    ArgumentMapperProvider provider = new TestArgumentMapperProvider();
    CommandBindings bindings = CommandBindings.builder()
        .bindMapper(String.class, mapper)
        .bindMapperProvider(provider)
        .build();

    ArgumentMapper<?> boundMapper = bindings.getMapper(TargetType.of(AnnotatedTypes.STRING));
    assertEquals(mapper, boundMapper, "Type was not bound to the correct mapper");
  }

  @Test
  void testArgumentMapperProviderBinding() {
    ArgumentMapperProvider provider = new TestArgumentMapperProvider();
    CommandBindings bindings = CommandBindings.builder()
        .bindMapperProvider(provider)
        .build();
    ArgumentMapper<?> boundMapper = bindings.getMapper(TargetType.of(AnnotatedTypes.STRING));
    assertEquals(TestArgumentMapperProvider.PROVIDED_MAPPER, boundMapper,
        "Mapper was not provided by the bound ArgumentMapperProvider");
  }

  @Test
  void testCommandInterceptorBinding() {
    CommandInterceptor<TestAnnotation> interceptor = (t, a, c) -> {};
    CommandBindings bindings = CommandBindings.builder()
        .bindCommandInterceptor(TestAnnotation.class, interceptor)
        .build();
    CommandInterceptor<TestAnnotation> boundInterceptor = bindings.getCommandInterceptor(TestAnnotation.class);
    assertEquals(interceptor, boundInterceptor, "Annotation was not bound to the correct CommandInterceptor");
  }

  @Test
  void testMappingInterceptorBinding() {
    MappingInterceptor<TestAnnotation> interceptor = new TestMappingInterceptor();
    CommandBindings bindings = CommandBindings.builder()
        .bindMappingInterceptor(TestAnnotation.class, interceptor)
        .build();
    MappingInterceptor<TestAnnotation> boundInterceptor = bindings.getMappingInterceptor(TestAnnotation.class);
    assertEquals(interceptor, boundInterceptor, "Annotation was not bound to the correct MappingInterceptor");
  }

  @Test
  void testExceptionHandlerBinding() {
    ExceptionHandler<Throwable> handler = (a, c1, c2) -> {};
    CommandBindings bindings = CommandBindings.builder()
        .bindHandler(Throwable.class, handler)
        .build();
    ExceptionHandler<Throwable> boundHandler = bindings.getHandler(Throwable.class);
    assertEquals(handler, boundHandler, "Exception type was not bound to the correct ExceptionHandler");
    ExceptionHandler<RuntimeException> boundSubclassHandler = bindings.getHandler(RuntimeException.class);
    assertEquals(handler, boundSubclassHandler,
        "Subclass Exception type was not bound to the correct ExceptionHandler");
  }

  private @interface TestAnnotation {}

  private static class TestMappingInterceptor extends MappingInterceptorAdapter<TestAnnotation> {}

  private static class TestArgumentMapperProvider implements ArgumentMapperProvider {

    static final ArgumentMapper<?> PROVIDED_MAPPER = (a, c) -> "second";

    @Nonnull
    @Override
    public ArgumentMapper<?> provideFor(@Nonnull TargetType type, @Nonnull CommandBindings bindings) {
      return PROVIDED_MAPPER;
    }

    @Override
    public boolean canProvideFor(@Nonnull TargetType type) {
      return true;
    }
  }
}