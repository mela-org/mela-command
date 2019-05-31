package com.github.stupremee.mela.repository;

import com.mongodb.client.model.DeleteOptions;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.reactivestreams.client.Success;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import org.bson.conversions.Bson;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Reactive version of a repository.
 *
 * @author Stu
 * @since 14.05.19
 */
public interface ReactiveRepository<EntityT> {

  /**
   * Saves a given entity.
   *
   * @param entity must not be {@literal null}.
   */
  @Nonnull
  Mono<Success> insert(@Nonnull EntityT entity);

  /**
   * Saves a given entity.
   *
   * @param entity must not be {@literal null}.
   */
  @Nonnull
  Mono<Success> insert(@Nonnull EntityT entity, @Nonnull InsertOneOptions options);

  /**
   * Saves all given entities.
   *
   * @param entities must not be {@literal null}.
   */
  @Nonnull
  Mono<Success> insertAll(@Nonnull List<? extends EntityT> entities);

  /**
   * Saves all given entities.
   *
   * @param entities must not be {@literal null}.
   */
  @Nonnull
  Mono<Success> insertAll(@Nonnull List<? extends EntityT> entities,
      @Nonnull InsertManyOptions options);

  /**
   * Retrieves an entity by its id.
   *
   * @param id must not be {@literal null}.
   * @return the entity with the given id or {@literal Optional#empty()} if none found
   */
  @Nonnull
  Mono<EntityT> findById(@Nonnull Object id);

  /**
   * Returns whether an entity with the given id exists.
   *
   * @param id must not be {@literal null}.
   * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
   */
  @Nonnull
  Mono<Boolean> existsWithId(@Nonnull Object id);

  /**
   * Returns all instances of the type.
   *
   * @return all entities
   */
  @Nonnull
  Flux<EntityT> findAll();

  /**
   * Returns all instances of the type with the given IdentifierTs.
   *
   * @param ids The ids of the entities to find
   * @return A {@link Collection} that contains all found entities
   */
  @Nonnull
  Flux<EntityT> findAllById(@Nonnull List<Object> ids);

  /**
   * Returns the number of entities available.
   *
   * @return the number of entities
   */
  @Nonnull
  Mono<Long> count();

  /**
   * Returns the number of entities available.
   *
   * @param filter The filter
   * @return the number of entities
   */
  @Nonnull
  Mono<Long> count(@Nonnull Bson filter);

  /**
   * Deletes the entity with the given id.
   *
   * @param id must not be {@literal null}.
   */
  @Nonnull
  Mono<DeleteResult> deleteById(@Nonnull Object id);

  /**
   * Deletes the entity with the given id.
   *
   * @param id must not be {@literal null}.
   */
  @Nonnull
  Mono<DeleteResult> deleteById(@Nonnull Object id, @Nonnull DeleteOptions options);

  /**
   * Deletes a given entity.
   *
   * @param filter The filer
   */
  @Nonnull
  Mono<DeleteResult> deleteOne(@Nonnull Bson filter);

  /**
   * Deletes a given entity.
   *
   * @param filter The filter
   */
  @Nonnull
  Mono<DeleteResult> deleteOne(@Nonnull Bson filter, @Nonnull DeleteOptions options);

  /**
   * Deletes the given entities by his id.
   *
   * @param filter The filter
   */
  @Nonnull
  Mono<DeleteResult> deleteAll(@Nonnull Bson filter);

  /**
   * Deletes the given entities by his id.
   *
   * @param filter The filter
   */
  @Nonnull
  Mono<DeleteResult> deleteAll(@Nonnull Bson filter, @Nonnull DeleteOptions options);

  /**
   * Finds all entities that match the given query.
   *
   * @param query The query
   * @return The list of entities
   */
  @Nonnull
  Flux<EntityT> find(@Nonnull Bson query);

  /**
   * Returns the first found entity for the given query.
   *
   * @param query The query
   * @return If no entity was found {@link Optional#empty()} otherwise the {@link Optional}
   *     containing the entity
   */
  @Nonnull
  Mono<EntityT> findOne(@Nonnull Bson query);

  /**
   * Finds and deletes the first entity that matches the given query.
   *
   * @param query The query
   * @return If no entity was found {@link Optional#empty()} otherwise the {@link Optional}
   *     containing the entity
   */
  @Nonnull
  default Mono<EntityT> findOneAndDelete(@Nonnull Bson query) {
    return findOneAndDelete(query, new FindOneAndDeleteOptions());
  }

  /**
   * Finds and deletes the first entity that matches the given query.
   *
   * @param query The query
   * @return If no entity was found {@link Optional#empty()} otherwise the {@link Optional}
   *     containing the entity
   */
  @Nonnull
  Mono<EntityT> findOneAndDelete(@Nonnull Bson query,
      @Nonnull FindOneAndDeleteOptions options);

  /**
   * Finds and deletes the first entity that matches the given query.
   *
   * @param query The query
   * @return If no entity was found {@link Optional#empty()} otherwise the {@link Optional}
   *     containing the entity
   */
  @Nonnull
  default Mono<EntityT> findOneAndReplace(@Nonnull Bson query, @Nonnull EntityT replacement) {
    return findOneAndReplace(query, new FindOneAndReplaceOptions(), replacement);
  }

  /**
   * Finds and deletes the first entity that matches the given query.
   *
   * @param query The query
   * @return If no entity was found {@link Optional#empty()} otherwise the {@link Optional}
   *     containing the entity
   */
  @Nonnull
  Mono<EntityT> findOneAndReplace(@Nonnull Bson query,
      @Nonnull FindOneAndReplaceOptions options,
      @Nonnull EntityT replacement);

  /**
   * Finds and deletes the first entity that matches the given query.
   *
   * @param query The query
   * @return If no entity was found {@link Optional#empty()} otherwise the {@link Optional}
   *     containing the entity
   */
  @Nonnull
  default Mono<EntityT> findOneAndUpdate(@Nonnull Bson query, @Nonnull Bson update) {
    return findOneAndUpdate(query, new FindOneAndUpdateOptions(), update);
  }

  /**
   * Finds and deletes the first entity that matches the given query.
   *
   * @param query The query
   * @return If no entity was found {@link Optional#empty()} otherwise the {@link Optional}
   *     containing the entity
   */
  @Nonnull
  Mono<EntityT> findOneAndUpdate(@Nonnull Bson query,
      @Nonnull FindOneAndUpdateOptions options,
      @Nonnull Bson update);
}
