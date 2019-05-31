package com.github.stupremee.mela.mongodb;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import com.github.stupremee.mela.repository.mapping.JacksonCodecProvider;
import com.google.inject.ImplementedBy;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import javax.annotation.Nonnull;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 14.05.19
 */
@ImplementedBy(DefaultDatabase.class)
public interface Database {

  /**
   * Returns the default {@link CodecRegistry} that ALWAYS should be used.
   *
   * @return The {@link CodecRegistry}
   */
  @Nonnull
  static CodecRegistry defaultCodecRegistry() {
    return fromRegistries(
        getDefaultCodecRegistry(),
        fromProviders(JacksonCodecProvider.create()));
  }

  /**
   * Tries to connect with the given credentials.
   *
   * @throws IllegalStateException If the database is already connected
   */
  void connect();

  /**
   * Closes the MongoDb Session.
   *
   * @throws IllegalStateException If the database is not connected
   */
  void close();

  /**
   * Checks if this instance is connected to MongoDB.
   *
   * @return True if connected, false if not connected
   */
  boolean isConnected();

  /**
   * Returns the client that will be used for all operations.
   *
   * @return The {@link MongoClient}
   */
  @Nonnull
  MongoClient getClient();

  /**
   * Returns the {@link DatabaseConfig} that is used to connect to MongoDB.
   *
   * @return The {@link DatabaseConfig}
   */
  @Nonnull
  DatabaseConfig getDatabaseConfig();

  /**
   * Returns the default database that should be used for all operations.
   *
   * @return The default {@link MongoDatabase}
   */
  @Nonnull
  MongoDatabase getDefaultDatabase();

  /**
   * Returns a {@link MongoDatabase} instance for the given database name.
   *
   * @param databaseName The name of the database
   * @return A {@link MongoDatabase}
   * @throws IllegalStateException If the database is not connected
   */
  @Nonnull
  MongoDatabase getDatabase(@Nonnull String databaseName);

  /**
   * Returns a {@link MongoCollection} by getting a {@link MongoDatabase} and then getting a {@link
   * MongoCollection} by using {@link MongoDatabase#getCollection(String)}.
   *
   * @param databaseName The name of the database
   * @param collection The name of the collection
   * @return A {@link MongoCollection}
   * @throws IllegalStateException If the database is not connected
   */
  @Nonnull
  MongoCollection<Document> getCollection(@Nonnull String databaseName, @Nonnull String collection);

  /**
   * Returns a {@link MongoCollection} instance by first getting a {@link MongoDatabase} instance
   * and then getting a {@link MongoCollection} instance by using {@link
   * MongoDatabase#getCollection(String)} but with the given type.
   *
   * @param <DocumentT> The type of the {@link MongoCollection}
   * @param databaseName The name of the database
   * @param collection The name of the collection
   * @param type The class of the type
   * @return A {@link MongoCollection}
   * @throws IllegalStateException If the database is not connected
   */
  @Nonnull
  <DocumentT> MongoCollection<DocumentT> getCollection(@Nonnull String databaseName,
      @Nonnull String collection,
      @Nonnull Class<DocumentT> type);
}
