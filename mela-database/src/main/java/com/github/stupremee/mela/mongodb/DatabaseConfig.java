package com.github.stupremee.mela.mongodb;

import com.github.stupremee.mela.mongodb.ImmutableDatabaseConfig.Builder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.immutables.value.Value;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 14.05.19
 */
@Value.Immutable
public abstract class DatabaseConfig {

  /**
   * Creates a new builder.
   *
   * @return The builder
   */
  public static Builder builder() {
    return ImmutableDatabaseConfig.builder();
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
}
