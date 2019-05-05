package com.github.stupremee.mela.event;

import com.google.inject.AbstractModule;
import net.engio.mbassy.bus.config.BusConfiguration;
import net.engio.mbassy.bus.config.Feature;
import net.engio.mbassy.bus.config.IBusConfiguration;
import net.engio.mbassy.bus.error.IPublicationErrorHandler;
import net.engio.mbassy.bus.error.PublicationError;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 05.05.19
 */
public final class EventBusModule extends AbstractModule {

  private EventBusModule() {

  }

  @Override
  protected void configure() {
    bind(IBusConfiguration.class).toInstance(new BusConfiguration()
        .addPublicationErrorHandler(new DefaultErrorHandler())
        .addFeature(Feature.AsynchronousMessageDispatch.Default())
        .addFeature(Feature.AsynchronousHandlerInvocation.Default())
        .addFeature(Feature.SyncPubSub.Default()));
  }

  private static final class DefaultErrorHandler implements IPublicationErrorHandler {

    @Override
    public void handleError(PublicationError error) {
      System.out.println("Error: " + error);
    }
  }

  public static EventBusModule create() {
    return new EventBusModule();
  }
}
