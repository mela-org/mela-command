package com.github.stupremee.mela.event.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * All classes that are marked with this and {@link net.engio.mbassy.listener.Listener} annotation
 * will automatically be subscribed to the {@link com.github.stupremee.mela.event.EventDispatcher}
 * by using the {@link com.google.inject.Injector} to create a new instance.
 *
 * @author Stu
 * @since 05.05.19
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AutoSubscribe {

}
