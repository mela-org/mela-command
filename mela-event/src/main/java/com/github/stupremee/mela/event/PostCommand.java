package com.github.stupremee.mela.event;

import java.util.concurrent.TimeUnit;
import net.engio.mbassy.bus.IMessagePublication;
import net.engio.mbassy.bus.publication.ISyncAsyncPublicationCommand;
import reactor.core.publisher.FluxProcessor;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 05.05.19
 */
final class PostCommand<T> implements ISyncAsyncPublicationCommand {

  static <T> PostCommand<T> create(FluxProcessor<Object, Object> processor, EventDispatcher bus,
      T message) {
    return new PostCommand<>(processor, bus, message);
  }

  private final FluxProcessor<Object, Object> processor;
  private final T message;
  private final EventDispatcher bus;

  private PostCommand(FluxProcessor<Object, Object> processor, EventDispatcher bus, T message) {
    this.processor = processor;
    this.bus = bus;
    this.message = message;
  }

  @Override
  public IMessagePublication now() {
    processor.onNext(message);
    return bus.getEventBus().publish(message);
  }

  @Override
  public IMessagePublication asynchronously() {
    processor.onNext(message);
    return bus.getEventBus().publishAsync(message);
  }

  @Override
  public IMessagePublication asynchronously(long timeout, TimeUnit unit) {
    bus.getScheduler().schedule(() -> processor.onNext(message), timeout, unit);
    return bus.getEventBus().publishAsync(message, timeout, unit);
  }
}
