package io.github.mela.command.bind.provided.mappers;

import io.github.mela.command.bind.parameter.TypeClassifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@TypeClassifier
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface Raw {
}
