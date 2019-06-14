package com.github.stupremee.mela.command;

import com.google.inject.Binding;
import com.google.inject.Key;
import com.google.inject.MembersInjector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Scope;
import com.google.inject.Stage;
import com.google.inject.TypeLiteral;
import com.google.inject.binder.AnnotatedBindingBuilder;
import com.google.inject.binder.AnnotatedConstantBindingBuilder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.matcher.Matcher;
import com.google.inject.spi.Message;
import com.google.inject.spi.ProvisionListener;
import com.google.inject.spi.TypeConverter;
import com.google.inject.spi.TypeListener;
import com.sk89q.intake.parametric.binder.BindingBuilder;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
// TODO: 14.06.2019 tests, documentation
public abstract class AbstractCommandModule implements GuiceIntakeModuleBridge {

  private com.google.inject.Binder guiceBinder;
  private com.sk89q.intake.parametric.binder.Binder intakeBinder;

  @Override
  public synchronized void configure(com.google.inject.Binder binder) {
    configureBinder(binder, BinderType.GUICE);
  }

  @Override
  public synchronized void configure(com.sk89q.intake.parametric.binder.Binder binder) {
    configureBinder(binder, BinderType.INTAKE);
  }

  private void configureBinder(Object binder, BinderType type) {
    checkBinderAbsence(type);
    assignBinder(binder, type);
    try {
      configure();
    } finally {
      assignBinder(null, type);
    }
  }

  protected abstract void configure();

  private void checkBinderPresence(BinderType type) {
    checkState(getBinder(type) != null, "The binder can only be used inside configure()");
  }

  private void checkBinderAbsence(BinderType type) {
    checkState(getBinder(type) == null, "Re-entry is not allowed.");
  }

  private Object getBinder(BinderType type) {
    switch (checkNotNull(type, "BinderType")) {
      case GUICE:
        return guiceBinder();
      case INTAKE:
        return intakeBinder();
    }
    throw new AssertionError();
  }

  private void assignBinder(Object binder, BinderType type) {
    switch (checkNotNull(type, "BinderType")) {
      case GUICE:
        guiceBinder = (com.google.inject.Binder) binder;
        break;
      case INTAKE:
        intakeBinder = (com.sk89q.intake.parametric.binder.Binder) binder;
        break;
    }
    throw new AssertionError();
  }

  /** Gets direct access to the underlying {@code Binder}. */
  protected com.google.inject.Binder guiceBinder() {
    checkBinderPresence(BinderType.GUICE);
    return guiceBinder;
  }

  protected com.sk89q.intake.parametric.binder.Binder intakeBinder() {
    checkBinderPresence(BinderType.INTAKE);
    return intakeBinder;
  }

  protected <T> BindingBuilder<T> bindParameter(Class<T> type) {
    return intakeBinder().bind(type);
  }

  protected <T> BindingBuilder<T> bindParameter(com.sk89q.intake.parametric.Key<T> key) {
    return intakeBinder().bind(key);
  }

  /** @see com.google.inject.Binder#bindScope(Class, Scope) */
  protected void bindScope(Class<? extends Annotation> scopeAnnotation, Scope scope) {
    guiceBinder().bindScope(scopeAnnotation, scope);
  }

  /** @see com.google.inject.Binder#bind(Key) */
  protected <T> LinkedBindingBuilder<T> bind(Key<T> key) {
    return guiceBinder().bind(key);
  }

  /** @see com.google.inject.Binder#bind(TypeLiteral) */
  protected <T> AnnotatedBindingBuilder<T> bind(TypeLiteral<T> typeLiteral) {
    return guiceBinder().bind(typeLiteral);
  }

  /** @see com.google.inject.Binder#bind(Class) */
  protected <T> AnnotatedBindingBuilder<T> bind(Class<T> clazz) {
    return guiceBinder().bind(clazz);
  }

  /** @see com.google.inject.Binder#bindConstant() */
  protected AnnotatedConstantBindingBuilder bindConstant() {
    return guiceBinder().bindConstant();
  }

  /** @see com.google.inject.Binder#install(com.google.inject.Module) */
  protected void install(Module module) {
    guiceBinder().install(module);
  }

  /** @see com.google.inject.Binder#addError(String, Object[]) */
  protected void addError(String message, Object... arguments) {
    guiceBinder().addError(message, arguments);
  }

  /** @see com.google.inject.Binder#addError(Throwable) */
  protected void addError(Throwable t) {
    guiceBinder().addError(t);
  }

  /**
   * @see com.google.inject.Binder#addError(Message)
   * @since 2.0
   */
  protected void addError(Message message) {
    guiceBinder().addError(message);
  }

  /**
   * @see com.google.inject.Binder#requestInjection(Object)
   * @since 2.0
   */
  protected void requestInjection(Object instance) {
    guiceBinder().requestInjection(instance);
  }

  /** @see com.google.inject.Binder#requestStaticInjection(Class[]) */
  protected void requestStaticInjection(Class<?>... types) {
    guiceBinder().requestStaticInjection(types);
  }

  /*if[AOP]*/
  /**
   * @see com.google.inject.Binder#bindInterceptor(com.google.inject.matcher.Matcher,
   *     com.google.inject.matcher.Matcher, org.aopalliance.intercept.MethodInterceptor[])
   */
  protected void bindInterceptor(
          Matcher<? super Class<?>> classMatcher,
          Matcher<? super Method> methodMatcher,
          org.aopalliance.intercept.MethodInterceptor... interceptors) {
    guiceBinder().bindInterceptor(classMatcher, methodMatcher, interceptors);
  }
  /*end[AOP]*/

  /**
   * Adds a dependency from this module to {@code key}. When the injector is created, Guice will
   * report an error if {@code key} cannot be injected. Note that this requirement may be satisfied
   * by implicit binding, such as a public no-arguments constructor.
   *
   * @since 2.0
   */
  protected void requireBinding(Key<?> key) {
    guiceBinder().getProvider(key);
  }

  /**
   * Adds a dependency from this module to {@code type}. When the injector is created, Guice will
   * report an error if {@code type} cannot be injected. Note that this requirement may be satisfied
   * by implicit binding, such as a public no-arguments constructor.
   *
   * @since 2.0
   */
  protected void requireBinding(Class<?> type) {
    guiceBinder().getProvider(type);
  }

  /**
   * @see com.google.inject.Binder#getProvider(Key)
   * @since 2.0
   */
  protected <T> Provider<T> getProvider(Key<T> key) {
    return guiceBinder().getProvider(key);
  }

  /**
   * @see com.google.inject.Binder#getProvider(Class)
   * @since 2.0
   */
  protected <T> Provider<T> getProvider(Class<T> type) {
    return guiceBinder().getProvider(type);
  }

  /**
   * @see com.google.inject.Binder#convertToTypes
   * @since 2.0
   */
  protected void convertToTypes(
          Matcher<? super TypeLiteral<?>> typeMatcher, TypeConverter converter) {
    guiceBinder().convertToTypes(typeMatcher, converter);
  }

  /**
   * @see com.google.inject.Binder#currentStage()
   * @since 2.0
   */
  protected Stage currentStage() {
    return guiceBinder().currentStage();
  }

  /**
   * @see com.google.inject.Binder#getMembersInjector(Class)
   * @since 2.0
   */
  protected <T> MembersInjector<T> getMembersInjector(Class<T> type) {
    return guiceBinder().getMembersInjector(type);
  }

  /**
   * @see com.google.inject.Binder#getMembersInjector(TypeLiteral)
   * @since 2.0
   */
  protected <T> MembersInjector<T> getMembersInjector(TypeLiteral<T> type) {
    return guiceBinder().getMembersInjector(type);
  }

  /**
   * @see com.google.inject.Binder#bindListener(com.google.inject.matcher.Matcher, com.google.inject.spi.TypeListener)
   * @since 2.0
   */
  protected void bindListener(Matcher<? super TypeLiteral<?>> typeMatcher, TypeListener listener) {
    guiceBinder().bindListener(typeMatcher, listener);
  }

  /**
   * @see com.google.inject.Binder#bindListener(Matcher, ProvisionListener...)
   * @since 4.0
   */
  protected void bindListener(
          Matcher<? super Binding<?>> bindingMatcher, ProvisionListener... listener) {
    guiceBinder().bindListener(bindingMatcher, listener);
  }

  private enum BinderType {
    GUICE, INTAKE
  }
}
