package com.github.stupremee.mela.command.annotation;

import com.sk89q.intake.parametric.annotation.Classifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Classifier
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ArgumentSize {
}
