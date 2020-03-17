package io.github.mela.command.bind.provided;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Localized {

  String value() default "en-US";

}
