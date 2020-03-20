package io.github.mela.command.bind;

import io.github.mela.command.core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class CommandInterceptorTest {

  private static final int ANNOTATION_VALUE = 42;

  private TestInterceptor interceptor;
  private Dispatcher dispatcher;

  @BeforeEach
  void setUp() {
    TestCommand command = new TestCommand();
    interceptor = new TestInterceptor();
    CommandBindings bindings = CommandBindings.builder()
        .bindCommandInterceptor(TestAnnotation.class, interceptor)
        .build();
    CommandGroup group = ImmutableGroup.builder()
        .withCommand(command)
        .compile(MethodHandleCompiler.withBindings(bindings));
    dispatcher = DefaultDispatcher.create(group);
  }

  @Test
  void testInterceptorExecution() {
    dispatcher.dispatch("foo", CommandContext.create());
    Assertions.assertEquals(ANNOTATION_VALUE, interceptor.annotationValue,
        "CommandInterceptor was incorrectly called or not called at all");
  }

  public static final class TestCommand {

    @Command(labels = "foo")
    @TestAnnotation(ANNOTATION_VALUE)
    public void execute() {

    }

  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  private @interface TestAnnotation {
    int value();
  }

  private static class TestInterceptor implements CommandInterceptor<TestAnnotation> {

    int annotationValue;

    @Override
    public void intercept(
        @Nonnull TestAnnotation annotation, @Nonnull Arguments arguments, @Nonnull CommandContext context) {
      annotationValue = annotation.value();
    }
  }



}
