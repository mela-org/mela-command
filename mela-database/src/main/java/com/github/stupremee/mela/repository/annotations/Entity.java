package com.github.stupremee.mela.repository.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {

  /**
   * The collection where the entity should be stored in.
   *
   * @return The collection name
   */
  String value();
}