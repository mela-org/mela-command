package com.github.stupremee.mela.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 05.05.19
 */
@Singleton
public final class ConfigLoader {

  private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

  ConfigLoader() {

  }

  public <T> Optional<T> load(Path path, Class<T> type) throws IOException {
    Preconditions.checkNotNull(path, "path can't be null.");
    Preconditions.checkNotNull(type, "type can't be null.");

    if (!Files.exists(path)) {
      return Optional.empty();
    }

    return Optional.ofNullable(MAPPER.readValue(path.toFile(), type));
  }
}
