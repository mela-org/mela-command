package com.github.stupremee.mela.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Preconditions;
import com.google.inject.Singleton;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 05.05.19
 */
@Singleton
public final class ConfigSaver {

  private static final ObjectMapper MAPPER = new ObjectMapper(new YAMLFactory());

  ConfigSaver() {

  }

  public <T> void save(T config, Path path) throws IOException {
    Preconditions.checkNotNull(config, "config can't be null.");
    Preconditions.checkNotNull(path, "path can't be null.");

    if (!Files.exists(path)) {
      Files.createFile(path);
    }

    try (FileWriter writer = new FileWriter(path.toFile())) {
      MAPPER.writeValue(writer, config);
    }
  }
}
