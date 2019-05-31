package com.github.stupremee.mela.mongodb;

import com.google.auto.value.AutoValue;
import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Optional;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 14.05.19
 */
@AutoValue
public abstract class DatabaseConfig {

  /**
   * Creates a new builder.
   *
   * @return The builder
   */
  public static Builder builder() {
    return new AutoValue_DatabaseConfig.Builder();
  }

  /**
   * Returns the connection string that should be used to connect to MongoDB.
   *
   * @return The {@link ServerAddress}
   */
  @Nonnull
  public abstract ConnectionString connectionString();

  /**
   * Returns the name of the Database that should be used for everything.
   *
   * @return The name of the default Database
   */
  @Nonnull
  public abstract String defaultDatabase();

  /**
   * Returns the credentials that should be used to connect to MongoDB.
   *
   * @return The {@link MongoCredential}
   */
  @Nonnull
  public abstract Optional<MongoCredential> credential();

  @AutoValue.Builder
  public abstract static class Builder {

    /**
     * Sets the connection string.
     *
     * @param connectionString The connection string
     * @return The builder
     */
    public abstract Builder connectionString(ConnectionString connectionString);

    /**
     * Sets the default database.
     *
     * @param defaultDatabase The default database
     * @return The builder
     */
    public abstract Builder defaultDatabase(String defaultDatabase);

    /**
     * Sets the credential.
     *
     * @param credential The credential
     * @return The builder
     */
    public abstract Builder credential(MongoCredential credential);

    /**
     * Builds a new {@link DatabaseConfig} with all the given values.
     *
     * @return The created {@link DatabaseConfig}
     */
    public abstract DatabaseConfig build();
  }
}
