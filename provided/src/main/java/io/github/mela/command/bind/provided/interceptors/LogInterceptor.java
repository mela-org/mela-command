package io.github.mela.command.bind.provided.interceptors;

import com.google.inject.Inject;
import io.github.mela.command.bind.CommandInterceptor;
import io.github.mela.command.core.CommandContext;
import io.github.mela.command.guice.CommandLogger;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Johnny_JayJay (https://www.github.com/JohnnyJayJay)
 */
public class LogInterceptor implements CommandInterceptor<Log> {

  private final Logger commandLogger;

  @Inject
  public LogInterceptor(@Nullable @CommandLogger Logger commandLogger) {
    this.commandLogger = commandLogger == null
        ? LoggerFactory.getLogger("Commands")
        : commandLogger;
  }

  @Override
  public void intercept(@Nonnull Log annotation, @Nonnull CommandContext context) {
    String message = "A command was dispatched. Context: " + context;
    switch (annotation.value()) {
      case ERROR:
        commandLogger.error(message);
        break;
      case WARN:
        commandLogger.warn(message);
        break;
      case INFO:
        commandLogger.info(message);
        break;
      case DEBUG:
        commandLogger.debug(message);
        break;
      case TRACE:
        commandLogger.trace(message);
        break;
    }
  }
}
