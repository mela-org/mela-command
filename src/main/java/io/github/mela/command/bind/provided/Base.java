package io.github.mela.command.bind.provided;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Base {
  int value();
}
