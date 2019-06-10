package com.github.stupremee.mela.event;

import com.google.inject.AbstractModule;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 05.05.19
 */
public final class EventBusModule extends AbstractModule {

  private static final class Lazy {

    private static final EventBusModule INSTANCE = new EventBusModule();
  }

  private EventBusModule() {

  }

  @Override
  protected void configure() {
    bind(IBusConfiguration.class).toInstance(new BusConfiguration()
        .addFeature(Feature.AsynchronousMessageDispatch.Default())
        .addFeature(Feature.AsynchronousHandlerInvocation.Default())
        .addFeature(Feature.SyncPubSub.Default()));
  }

  public static AbstractModule instance() {
    return Lazy.INSTANCE;
  }
}