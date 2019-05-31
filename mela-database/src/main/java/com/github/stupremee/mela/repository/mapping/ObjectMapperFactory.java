package com.github.stupremee.mela.repository.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.undercouch.bson4jackson.BsonFactory;
import de.undercouch.bson4jackson.BsonParser;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
public final class ObjectMapperFactory {

  private ObjectMapperFactory() {

  }

  /**
   * Creates a new {@link ObjectMapper} that will use a {@link BsonFactory} to do stuff.
   *
   * @return The {@link ObjectMapper}
   */
  public static ObjectMapper createObjectMapper() {
    BsonFactory bsonFactory = new BsonFactory();
    bsonFactory.enable(BsonParser.Feature.HONOR_DOCUMENT_LENGTH);
    return new ObjectMapper(bsonFactory);
  }
}
