package com.github.stupremee.mela.repository;

import com.github.stupremee.mela.mongodb.Database;
import com.github.stupremee.mela.repository.annotations.Entity;
import com.google.inject.Inject;
import com.mongodb.reactivestreams.client.MongoCollection;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 31.05.19
 */
final class DefaultRepositoryFactory implements RepositoryFactory {

  @Inject
  DefaultRepositoryFactory() {

  }

  @Nonnull
  @Override
  public <T> ReactiveRepository<T> reactiveRepository(@Nonnull Database database,
      @Nonnull Class<T> type) {
    validateClass(type);
    Entity entity = type.getAnnotation(Entity.class);
    MongoCollection<T> collection = database.getDefaultDatabase()
        .getCollection(entity.value(), type);
    return new DefaultReactiveRepository<>(collection, type);
  }

  private static void validateClass(Class<?> clazz) {
    if (!clazz.isAnnotationPresent(Entity.class)) {
      throw new IllegalArgumentException(
          "Only classes with @Entity annotation can be used to create a Repository!");
    }
  }
}
