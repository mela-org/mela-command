package com.github.stupremee.mela.repository.mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.mongodb.MongoClientSettings;
import java.io.IOException;
import java.io.UncheckedIOException;
import javax.annotation.Nonnull;
import org.bson.BsonReader;
import org.bson.BsonWriter;
import org.bson.RawBsonDocument;
import org.bson.codecs.Codec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
public final class JacksonCodec<T> implements Codec<T> {

  private final Codec<RawBsonDocument> rawBsonDocumentCodec;
  private final ObjectMapper bsonMapper;
  private final Class<T> type;

  private JacksonCodec(ObjectMapper bsonMapper,
      CodecRegistry codecRegistry,
      Class<T> type) {
    this.bsonMapper = bsonMapper;
    this.rawBsonDocumentCodec = codecRegistry.get(RawBsonDocument.class);
    this.type = type;
  }

  @Override
  public T decode(BsonReader reader, DecoderContext decoderContext) {
    try {
      RawBsonDocument document = rawBsonDocumentCodec.decode(reader, decoderContext);
      return bsonMapper.readValue(document.getByteBuffer().array(), type);
    } catch (IOException cause) {
      throw new UncheckedIOException(cause);
    }
  }

  @Override
  public void encode(BsonWriter writer, T value, EncoderContext encoderContext) {
    try {
      byte[] data = bsonMapper.writeValueAsBytes(value);
      rawBsonDocumentCodec.encode(writer, new RawBsonDocument(data), encoderContext);
    } catch (JsonProcessingException cause) {
      throw new UncheckedIOException(cause);
    }
  }

  @Override
  public Class<T> getEncoderClass() {
    return type;
  }

  /**
   * Creates a new {@link JacksonCodec} that will use Jackson with bson4jackson to encode and decode
   * objects. This method will use {@link ObjectMapperFactory#createObjectMapper()} to create a new
   * {@link ObjectMapper} and {@link MongoClientSettings#getDefaultCodecRegistry()} to get the
   * {@link CodecRegistry}.
   *
   * @param type The class of the codec
   * @param <T> The type of the codec
   * @return The {@link JacksonCodec}
   * @see #create(ObjectMapper, CodecRegistry, Class)
   */
  public static <T> Codec<T> create(@Nonnull Class<T> type) {
    return create(ObjectMapperFactory.createObjectMapper(),
        MongoClientSettings.getDefaultCodecRegistry(), type);
  }

  /**
   * Creates a new {@link JacksonCodec} that will use Jackson with bson4jackson to encode and decode
   * objects. This method will use {@link ObjectMapperFactory#createObjectMapper()} to create a new
   * {@link ObjectMapper}.
   *
   * @param codecRegistry The {@link CodecRegistry} will be used to get a {@link Codec} for the
   *     {@link RawBsonDocument} class
   * @param type The class of the codec
   * @param <T> The type of the codec
   * @return The {@link JacksonCodec}
   * @see #create(ObjectMapper, CodecRegistry, Class)
   */
  public static <T> Codec<T> create(@Nonnull CodecRegistry codecRegistry,
      @Nonnull Class<T> type) {
    return create(ObjectMapperFactory.createObjectMapper(), codecRegistry, type);
  }

  /**
   * Creates a new {@link JacksonCodec} that will use Jackson with bson4jackson to encode and decode
   * objects.
   *
   * @param mapper The mapper that should be used to map objects.
   * @param codecRegistry The {@link CodecRegistry} will be used to get a {@link Codec} for the
   *     {@link RawBsonDocument} class
   * @param type The class of the codec
   * @param <T> The type of the codec
   * @return The {@link JacksonCodec}
   */
  public static <T> Codec<T> create(@Nonnull ObjectMapper mapper,
      @Nonnull CodecRegistry codecRegistry,
      @Nonnull Class<T> type) {
    Preconditions.checkNotNull(mapper, "mapper can't be null.");
    Preconditions.checkNotNull(codecRegistry, "codecRegistry can't be null.");
    Preconditions.checkNotNull(type, "type can't be null.");

    return new JacksonCodec<>(mapper, codecRegistry, type);
  }
}
