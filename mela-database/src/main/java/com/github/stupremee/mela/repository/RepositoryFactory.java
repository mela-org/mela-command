package com.github.stupremee.mela.repository;

import com.github.stupremee.mela.mongodb.Database;
import com.github.stupremee.mela.mongodb.DatabaseConfig;
import com.google.inject.ImplementedBy;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 14.05.19
 */
@ImplementedBy(DefaultRepositoryFactory.class)
public interface RepositoryFactory {

  /**
   * Returns a {@link RepositoryFactory}.
   *
   * @return The {@link RepositoryFactory}
   */
  static RepositoryFactory create() {
    return new DefaultRepositoryFactory();
  }

  /**
   * Creates a new {@link ReactiveRepository} by using the default database that will come from the
   * given {@link Database} instance via {@link DatabaseConfig#defaultDatabase()}.
   *
   * @param database The {@link Database} instance
   * @param type The class of the repository
   * @param <T> The type of the repository
   * @return The new {@link ReactiveRepository}
   */
  @Nonnull
  <T> ReactiveRepository<T> reactiveRepository(@Nonnull Database database,
      @Nonnull Class<T> type);
}
