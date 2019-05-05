package com.github.stupremee.mela.config;

import static com.google.common.base.Preconditions.checkNotNull;

import com.google.inject.Inject;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import reactor.util.Logger;
import reactor.util.Loggers;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 05.05.19
 */
public final class ConfigProvider {

  private static final Logger LOGGER = Loggers.getLogger("ConfigProvider");
  private final ConfigSaver configSaver;
  private final ConfigLoader configLoader;

  @Inject
  private ConfigProvider(ConfigSaver configSaver, ConfigLoader configLoader) {
    this.configSaver = configSaver;
    this.configLoader = configLoader;
  }

  /**
   * Saves a config to the given path
   *
   * @param config The config
   * @param path The path
   * @param <T> The type of the config
   */
  public <T> void save(T config, Path path) {
    checkNotNull(config, "config can't be null.");
    checkNotNull(path, "path can't be null.");

    try {
      this.configSaver.save(config, path);
    } catch (IOException cause) {
      LOGGER.error("Failed to save config!", cause);
    }
  }

  /**
   * Loads a config with the given type from the given path.
   *
   * @param path The path
   * @param type The class of the config
   * @param <T> The type
   * @return The config
   */
  public <T> Optional<T> load(Path path, Class<T> type) {
    checkNotNull(path, "path can't be null.");
    checkNotNull(type, "type can't be null.");

    try {
      return this.configLoader.load(path, type);
    } catch (IOException cause) {
      LOGGER.error("Failed to load config!", cause);
      return Optional.empty();
    }
  }
}
