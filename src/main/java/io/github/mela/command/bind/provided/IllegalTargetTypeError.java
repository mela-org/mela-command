package io.github.mela.command.bind.provided;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class IllegalTargetTypeError extends PreconditionError {

  public IllegalTargetTypeError(Type type, Class<? extends Annotation> interceptorAnnotation) {
    super("Illegal target type: @" + interceptorAnnotation.getName()
        + " annotation cannot be applied to " + type + ".");
  }
}
