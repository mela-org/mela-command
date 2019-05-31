package com.github.stupremee.mela.repository;

import com.google.common.base.Preconditions;
import com.mongodb.client.model.DeleteOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndDeleteOptions;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.InsertOneOptions;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.Success;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.bson.conversions.Bson;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 14.05.19
 */
public final class DefaultReactiveRepository<EntityT> implements ReactiveRepository<EntityT> {

  private final MongoCollection<EntityT> collection;
  private final Class<EntityT> type;

  DefaultReactiveRepository(MongoCollection<EntityT> collection,
      Class<EntityT> type) {
    this.collection = collection;
    this.type = type;
  }

  @Nonnull
  @Override
  public Mono<Success> insert(@Nonnull EntityT entity) {
    return insert(entity, new InsertOneOptions());
  }

  @Nonnull
  @Override
  public Mono<Success> insert(@Nonnull EntityT entity, @Nonnull InsertOneOptions options) {
    Preconditions.checkNotNull(entity, "entity can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");

    return publisherToMono(collection.insertOne(entity, options));
  }

  @Nonnull
  @Override
  public Mono<Success> insertAll(@Nonnull List<? extends EntityT> entities) {
    return insertAll(entities, new InsertManyOptions());
  }

  @Nonnull
  @Override
  public Mono<Success> insertAll(@Nonnull List<? extends EntityT> entities,
      @Nonnull InsertManyOptions options) {
    Preconditions.checkNotNull(entities, "entities can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");

    return publisherToMono(collection.insertMany(entities, options));
  }

  @Nonnull
  @Override
  public Mono<EntityT> findById(@Nonnull Object id) {
    Preconditions.checkNotNull(id, "id can't be null.");

    return publisherToMono(collection.find(Filters.eq(id)).limit(1));
  }

  @Nonnull
  @Override
  public Mono<Boolean> existsWithId(@Nonnull Object id) {
    Preconditions.checkNotNull(id, "id can't be null.");

    return findById(id)
        .hasElement();
  }

  @Nonnull
  @Override
  public Flux<EntityT> findAll() {
    return publisherToFlux(collection.find());
  }

  @Nonnull
  @Override
  public Flux<EntityT> findAllById(@Nonnull List<Object> ids) {
    Preconditions.checkNotNull(ids, "ids can't be null.");

    List<Bson> filters = new ArrayList<>();
    ids.forEach(id -> filters.add(Filters.eq(id)));
    return publisherToFlux(collection.find(Filters.or(filters)));
  }

  @Nonnull
  @Override
  public Mono<Long> count() {
    return publisherToMono(collection.countDocuments());
  }

  @Nonnull
  @Override
  public Mono<Long> count(@Nonnull Bson filter) {
    Preconditions.checkNotNull(filter, "filter can't be null.");

    return publisherToMono(collection.countDocuments(filter));
  }

  @Nonnull
  @Override
  public Mono<DeleteResult> deleteById(@Nonnull Object id) {
    Preconditions.checkNotNull(id, "id can't be null.");

    return publisherToMono(collection.deleteOne(Filters.eq(id)));
  }

  @Nonnull
  @Override
  public Mono<DeleteResult> deleteById(@Nonnull Object id, @Nonnull DeleteOptions options) {
    Preconditions.checkNotNull(id, "id can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");

    return publisherToMono(collection.deleteOne(Filters.eq(id), options));
  }

  @Nonnull
  @Override
  public Mono<DeleteResult> deleteOne(@Nonnull Bson filter) {
    Preconditions.checkNotNull(filter, "filter can't be null.");

    return publisherToMono(collection.deleteOne(filter));
  }

  @Nonnull
  @Override
  public Mono<DeleteResult> deleteOne(@Nonnull Bson filter, @Nonnull DeleteOptions options) {
    Preconditions.checkNotNull(filter, "filter can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");

    return publisherToMono(collection.deleteOne(filter, options));
  }

  @Nonnull
  @Override
  public Mono<DeleteResult> deleteAll(@Nonnull Bson filter) {
    Preconditions.checkNotNull(filter, "filter can't be null.");

    return publisherToMono(collection.deleteMany(filter));
  }

  @Nonnull
  @Override
  public Mono<DeleteResult> deleteAll(@Nonnull Bson filter, @Nonnull DeleteOptions options) {
    Preconditions.checkNotNull(filter, "filter can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");

    return publisherToMono(collection.deleteMany(filter, options));
  }

  @Nonnull
  @Override
  public Flux<EntityT> find(@Nonnull Bson query) {
    Preconditions.checkNotNull(query, "query can't be null.");

    return publisherToFlux(collection.find(query));
  }

  @Nonnull
  @Override
  public Mono<EntityT> findOne(@Nonnull Bson query) {
    Preconditions.checkNotNull(query, "query can't be null.");

    return publisherToMono(collection.find(query).limit(1));
  }

  @Nonnull
  @Override
  public Mono<EntityT> findOneAndDelete(@Nonnull Bson query,
      @Nonnull FindOneAndDeleteOptions options) {
    Preconditions.checkNotNull(query, "query can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");

    return publisherToMono(collection.findOneAndDelete(query, options));
  }

  @Nonnull
  @Override
  public Mono<EntityT> findOneAndReplace(@Nonnull Bson query,
      @Nonnull FindOneAndReplaceOptions options,
      @Nonnull EntityT replacement) {
    Preconditions.checkNotNull(query, "query can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");
    Preconditions.checkNotNull(replacement, "replacement can't be null.");

    return publisherToMono(collection.findOneAndReplace(query, replacement, options));
  }

  @Nonnull
  @Override
  public Mono<EntityT> findOneAndUpdate(@Nonnull Bson query,
      @Nonnull FindOneAndUpdateOptions options,
      @Nonnull Bson update) {
    Preconditions.checkNotNull(query, "query can't be null.");
    Preconditions.checkNotNull(options, "options can't be null.");
    Preconditions.checkNotNull(update, "update can't be null.");

    return publisherToMono(collection.findOneAndUpdate(query, update, options));
  }

  private static <T> Mono<T> publisherToMono(Publisher<T> publisher) {
    return Mono.from(publisher);
  }

  private static <T> Flux<T> publisherToFlux(Publisher<T> publisher) {
    return Flux.from(publisher);
  }
}
