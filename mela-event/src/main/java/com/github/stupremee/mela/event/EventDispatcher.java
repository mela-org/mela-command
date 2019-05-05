package com.github.stupremee.mela.event;

import com.github.stupremee.mela.event.annotations.AutoSubscribe;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import com.google.errorprone.annotations.CheckReturnValue;
import com.google.inject.Inject;
import com.google.inject.Injector;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import java.util.logging.Level;
import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.config.IBusConfiguration.Properties;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;
import net.engio.mbassy.bus.publication.SyncAsyncPostCommand;
import net.engio.mbassy.listener.Listener;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxProcessor;
import reactor.core.publisher.SignalType;
import reactor.core.scheduler.Scheduler;
import reactor.scheduler.forkjoin.ForkJoinPoolScheduler;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 05.05.19
 */
public final class EventDispatcher {

  private static final Logger LOG = Loggers.getLogger("EventDispatcher");

  private final FluxProcessor<Object, Object> processor;
  private final Scheduler scheduler;
  private final Injector injector;
  private final MBassador<Object> bus;

  @Inject
  EventDispatcher(Injector injector,
      IBusConfiguration configuration) {
    this.injector = injector;
    this.processor = EmitterProcessor.create(false);
    this.scheduler = ForkJoinPoolScheduler.create("events");
    this.bus = createBus(configuration);
    this.initialize();
  }

  private void initialize() {

    // Scan and register all classes with @AutoSubscribe annotation
    try (ScanResult scanResult = new ClassGraph()
        .enableAnnotationInfo()
        .enableClassInfo()
        .scan()) {
      scanResult.getClassesWithAnnotation(AutoSubscribe.class.getName())
          .loadClasses()
          .stream()
          .filter(clazz -> clazz.isAnnotationPresent(Listener.class))
          .map(this.injector::getInstance)
          .forEach(this::subscribe);
    }

    this.on(Object.class)
        .doOnNext(this::post)
        .subscribe();
  }

  /**
   * Subscribe all handlers of the given listener. Any listener is only subscribed once ->
   * subsequent subscriptions of an already subscribed listener will be silently ignored
   *
   * @param listener the listener to register
   * @see MBassador#subscribe(Object)
   */
  public void subscribe(Object listener) {
    Preconditions.checkNotNull(listener, "listener can't be null.");
    LOG.debug("Registered new listener {}", listener.getClass().getSimpleName());
    this.bus.subscribe(listener);
  }

  /**
   * Immediately remove all registered message handlers (if any) of the given listener. When this
   * call returns all handlers have effectively been removed and will not receive any messages
   * (provided that running publications (iterators) in other threads have not yet obtained a
   * reference to the listener)
   * <p/>
   * A call to this method passing any object that is not subscribed will not have any effect and is
   * silently ignored.
   *
   * @param listener the listener to remove
   * @return true, if the listener was found and successfully removed false otherwise
   * @see MBassador#unsubscribe(Object)
   */
  @CanIgnoreReturnValue
  public boolean unsubscribe(Object listener) {
    Preconditions.checkNotNull(listener, "listener can't be null.");
    return this.bus.unsubscribe(listener);
  }

  /**
   * Posts a event.
   *
   * @param event The event to post
   * @return The {@link SyncAsyncPostCommand}
   * @see MBassador#post(Object)
   */
  public PostCommand<Object> post(Object event) {
    Preconditions.checkNotNull(event, "event can't be null.");
    return PostCommand.create(processor, this, event);
  }

  /**
   * Retrieves a {@link reactor.core.publisher.Flux} with elements of the given {@link
   * discord4j.core.event.domain.Event} type.
   *
   * @param eventClass the event class to obtain events from
   * @param <T> the type of the event class
   * @return a new {@link reactor.core.publisher.Flux} with the requested events
   */
  @CheckReturnValue
  public <T> Flux<T> on(Class<T> eventClass) {
    return processor.publishOn(scheduler)
        .ofType(eventClass)
        .log("mela.dispatch." + eventClass.getSimpleName(), Level.FINE,
            SignalType.ON_NEXT, SignalType.ON_SUBSCRIBE, SignalType.ON_ERROR, SignalType.CANCEL);
  }

  /**
   * Accessor for the {@link FluxProcessor} that will be used by the {@link EventDispatcher}.
   *
   * @return The {@link FluxProcessor}
   */
  public FluxProcessor<Object, Object> getProcessor() {
    return processor;
  }

  /**
   * Accessor for the {@link Scheduler} that will be used by the {@link EventDispatcher}.
   *
   * @return The {@link Scheduler}
   */
  public Scheduler getScheduler() {
    return scheduler;
  }

  MBassador<Object> getEventBus() {
    return bus;
  }

  private static class DefaultErrorHandler implements IPublicationErrorHandler {

    private DefaultErrorHandler() {

    }

    @Override
    public void handleError(PublicationError error) {
      EventDispatcher.LOG.error("An unknown error occurred!", error.getCause());
    }
  }

  private static MBassador<Object> createBus(IBusConfiguration config) {
    config.addPublicationErrorHandler(new DefaultErrorHandler());
    MBassador<Object> eventBus = new MBassador<>(config);
    LOG.debug("Initialize new event bus with id {}",
        eventBus.getRuntime()
            .<String>get(Properties.BusId));
    return eventBus;
  }
}