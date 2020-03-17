package io.github.mela.command.bind.provided.interceptors;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface Flag {

  String[] value();

}
