package com.github.stupremee.mela.mongodb;

import com.google.inject.Inject;
import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import javax.annotation.Nonnull;
import org.bson.Document;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 14.05.19
 */
final class DefaultDatabase implements Database {

  private final DatabaseConfig config;
  private MongoClient client;

  @Inject
  DefaultDatabase(DatabaseConfig config) {
    this.config = config;
  }

  @Override
  public void connect() {
    checkConnection(false);

    var settings = MongoClientSettings.builder()
        .applyConnectionString(config.connectionString())
        .codecRegistry(Database.defaultCodecRegistry());
    if (config.credential().isEmpty()) {
      this.client = MongoClients.create(settings.build());
    } else {
      this.client = MongoClients.create(settings
          .credential(config.credential().get())
          .build());
    }
  }

  @Override
  public void close() {
    checkConnection(true);

    client.close();
    this.client = null;
  }

  @Override
  public boolean isConnected() {
    return client != null;
  }

  @Nonnull
  @Override
  public MongoClient getClient() {
    checkConnection(true);

    return client;
  }

  @Nonnull
  @Override
  public DatabaseConfig getDatabaseConfig() {
    return config;
  }

  @Nonnull
  @Override
  public MongoDatabase getDefaultDatabase() {
    checkConnection(true);

    return getDatabase(config.defaultDatabase());
  }

  @Nonnull
  @Override
  public MongoDatabase getDatabase(@Nonnull String databaseName) {
    checkConnection(true);

    return client.getDatabase(databaseName);
  }

  @Nonnull
  @Override
  public MongoCollection<Document> getCollection(@Nonnull String databaseName,
      @Nonnull String collection) {
    checkConnection(true);

    return getCollection(databaseName, collection, Document.class);
  }

  @Nonnull
  @Override
  public <DocumentT> MongoCollection<DocumentT> getCollection(@Nonnull String databaseName,
      @Nonnull String collection, @Nonnull Class<DocumentT> type) {
    checkConnection(true);

    return getDatabase(databaseName).getCollection(collection, type);
  }

  /**
   * Throws a exception when the database is not connected.
   *
   * @param connected True if the database should be connected, false to check for disconnected
   *     status
   */
  private void checkConnection(boolean connected) {
    if (connected) {
      if (client == null) {
        throw new IllegalStateException("Database is not connected.");
      }
    } else {
      if (client != null) {
        throw new IllegalStateException("Database is already connected.");
      }
    }
  }
}
