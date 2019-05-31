package com.github.stupremee.mela.repository.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
public final class JacksonCodecProvider implements CodecProvider {

  private final ObjectMapper mapper;

  private JacksonCodecProvider(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public <T> Codec<T> get(Class<T> clazz, CodecRegistry registry) {
    return JacksonCodec.create(mapper, registry, clazz);
  }

  /**
   * Creates a new {@link JacksonCodecProvider} that can be "injected" in the MongoClient to use
   * Jackson. This method will call {@link ObjectMapperFactory#createObjectMapper()} to create a new
   * {@link ObjectMapper}.
   *
   * @return The {@link CodecProvider}
   * @see #create(ObjectMapper)
   */
  public static CodecProvider create() {
    return create(ObjectMapperFactory.createObjectMapper());
  }

  /**
   * Creates a new {@link JacksonCodecProvider} that can be "injected" in the MongoClient to use
   * Jackson.
   *
   * @param mapper The {@link ObjectMapper} that should be used.
   * @return The {@link CodecProvider}
   */
  public static CodecProvider create(@Nonnull ObjectMapper mapper) {
    Preconditions.checkNotNull(mapper, "mapper can't be null.");
    return new JacksonCodecProvider(mapper);
  }
}
