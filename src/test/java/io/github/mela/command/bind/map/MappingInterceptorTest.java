package io.github.mela.command.bind.map;

import static org.junit.jupiter.api.Assertions.assertEquals;


import io.github.mela.command.bind.Command;
import io.github.mela.command.bind.CommandBindings;
import io.github.mela.command.bind.MethodHandleCompiler;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.core.CommandGroup;
import io.github.mela.command.core.DefaultDispatcher;
import io.github.mela.command.core.CommandDispatcher;
import io.github.mela.command.core.ImmutableGroup;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class MappingInterceptorTest {

  private static final int ANNOTATION_VALUE = 35;

  private TestInterceptor interceptor;
  private CommandDispatcher dispatcher;

  @BeforeEach
  void setUp() {
    interceptor = new TestInterceptor();
    CommandBindings bindings = CommandBindings.builder()
        .bindMapper(int.class, (a, c) -> 0)
        .bindMappingInterceptor(TestAnnotation.class, interceptor)
        .build();
    CommandGroup group = ImmutableGroup.builder()
        .add(new TestCommand())
        .compile(new MethodHandleCompiler(bindings));
    dispatcher = new DefaultDispatcher(group);
  }

  @Test
  void testInterceptorExecution() {
    dispatcher.dispatch("foo", CommandContext.create());
    assertEquals(ANNOTATION_VALUE, interceptor.preprocessedValue,
        "preprocess method was incorrectly called or not called at all");
    assertEquals(ANNOTATION_VALUE, interceptor.postProcessedValue,
        "postprocess method was incorrectly called or not called at all");
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target( {ElementType.TYPE_USE, ElementType.PARAMETER})
  private @interface TestAnnotation {
    int value();
  }

  public static final class TestCommand {

    @Command(labels = "foo")
    public void execute(@TestAnnotation(ANNOTATION_VALUE) int i) {

    }
  }

  private static class TestInterceptor implements MappingInterceptor<TestAnnotation> {

    int preprocessedValue, postProcessedValue;

    @Override
    public void preprocess(
        @Nonnull TestAnnotation annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
      preprocessedValue = annotation.value();
    }

    @Override
    public void postprocess(
        @Nonnull TestAnnotation annotation, @Nonnull MappingProcess process, @Nonnull CommandContext context) {
      postProcessedValue = annotation.value();
    }
  }

}
