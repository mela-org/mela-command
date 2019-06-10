package com.github.stupremee.mela.repository;

import com.github.stupremee.mela.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.06.19
 */
@SuppressWarnings("unused")
public interface ReactiveRepository<IdentifierT, EntityT> {

  /**
   * Finds all entities that match the given query.
   *
   * @param query The {@link Query}
   * @return The entities in a {@link Flux}
   */
  Flux<EntityT> query(Query query);

  /**
   * Finds the first entity that matches the given query.
   *
   * @param query The {@link Query}
   * @return A {@link Mono} that contains the entity
   */
  Mono<EntityT> queryFirst(Query query);

  /**
   * Finds the entity with the given id.
   *
   * @param id The id
   * @return A {@link Mono} containing the entity
   */
  Mono<EntityT> findWithId(IdentifierT id);

  /**
   * Finds all entities in this repository.
   *
   * @return A {@link Flux} containing all entities
   */
  Flux<EntityT> findAll();

  /**
   * Deletes all entities that match the query.
   *
   * @param query The {@link Query}
   * @return A {@link Mono} that completes when all entities are deleted
   */
  Mono<Void> delete(Query query);

  /**
   * Deletes the first entity that matches the given query.
   *
   * @param query The {@link Query}
   * @return A {@link Mono} that completes when the entity is deleted
   */
  Mono<Void> deleteFirst(Query query);

  /**
   * Deletes the entity with the given id.
   *
   * @param id The id
   * @return A {@link Mono} that completes when the entity is deleted
   */
  Mono<Void> deleteWithId(IdentifierT id);

  /**
   * Deletes all entities in this repository.
   *
   * @return A {@link Mono} that completes when all entities are deleted
   */
  Mono<Void> deleteAll();

  /**
   * Replaces the entity with the given id with the given entity.
   *
   * @param id The id
   * @param entity The entity
   * @return A {@link Mono} that completes when the entity is replaced
   */
  Mono<Void> replace(IdentifierT id, EntityT entity);
}
