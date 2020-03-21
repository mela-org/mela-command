package io.github.mela.command.bind;

import io.github.mela.command.core.Arguments;
import io.github.mela.command.core.CommandContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.annotation.Nonnull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
class CommandInterceptorTest extends BindingTest<CommandInterceptorTest.TestCommand> {

  private static final int ANNOTATION_VALUE = 42;

  private TestInterceptor interceptor;

  protected CommandInterceptorTest() {
    super(TestCommand::new);
  }

  @Override
  protected CommandBindingsBuilder configure(CommandBindingsBuilder builder) {
    interceptor = new TestInterceptor();
    return builder.bindCommandInterceptor(TestAnnotation.class, interceptor);
  }

  @Test
  void testInterceptorExecution() {
    dispatcher.dispatch("foo", CommandContext.create());
    Assertions.assertEquals(ANNOTATION_VALUE, interceptor.annotationValue,
        "CommandInterceptor was incorrectly called or not called at all");
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  private @interface TestAnnotation {
    int value();
  }

  public static final class TestCommand {

    @Command(labels = "foo")
    @TestAnnotation(ANNOTATION_VALUE)
    public void execute() {

    }

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
