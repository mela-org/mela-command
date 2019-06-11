package com.github.stupremee.mela.repository;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 11.06.19
 */
public interface RepositoryFactory {

  /**
   * Creates a new {@link ReactiveRepository}.
   *
   * @return The created {@link ReactiveRepository}
   */
  <IdentifierT, EntityT> ReactiveRepository<IdentifierT, EntityT> createReactiveRepository(
      Class<IdentifierT> identifierClass,
      Class<EntityT> entityClass);

}