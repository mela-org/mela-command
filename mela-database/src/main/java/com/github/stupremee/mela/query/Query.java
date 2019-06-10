package com.github.stupremee.mela.query;

import com.github.stupremee.mela.query.criterias.BetweenCriteria;
import com.github.stupremee.mela.query.criterias.EqualsCriteria;
import com.github.stupremee.mela.query.criterias.GreaterThanCriteria;
import com.github.stupremee.mela.query.criterias.GreaterThanOrEqualCriteria;
import com.github.stupremee.mela.query.criterias.LessThanCriteria;
import com.github.stupremee.mela.query.criterias.LessThanOrEqualCriteria;
import com.github.stupremee.mela.query.criterias.NotEqualsCriteria;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.06.19
 */
public final class Query {

  private final String key;
  private final List<Criteria> criteria;

  private Query(String key, List<Criteria> criteria) {
    this.key = key;
    this.criteria = criteria;
  }

  /**
   * Adds another {@link Criteria} to this {@link Query}.
   *
   * @param key The key of the new {@link Criteria}
   * @return The {@link CriteriaFactory} to create the {@link Criteria}
   */
  public CriteriaFactory and(String key) {
    Preconditions.checkNotNull(key, "key can't be null.");
    return new CriteriaFactory(this);
  }

  /**
   * Creates a new {@link Query} with the given key as key.
   *
   * @param key The key
   * @return The {@link Query}
   */
  public static CriteriaFactory where(String key) {
    Preconditions.checkNotNull(key, "key can't be null.");

    Query query = new Query(key, new ArrayList<>());
    return new CriteriaFactory(query);
  }

  /**
   * This class is used to create {@link Criteria} objects and automatically add them to the given
   * {@link Query}.
   */
  public static final class CriteriaFactory {

    private final Query query;

    private CriteriaFactory(Query query) {
      this.query = query;
    }

    /**
     * Adds a {@link EqualsCriteria} to the {@link Query}.
     *
     * @param value The value
     * @return The {@link Query}
     */
    @Nonnull
    public Query eq(Object value) {
      Preconditions.checkNotNull(value, "value can't be null.");
      query.criteria.add(EqualsCriteria.create(query.key, value));
      return query;
    }

    /**
     * Adds a {@link NotEqualsCriteria} to the {@link Query}.
     *
     * @param value The value
     * @return The {@link Query}
     */
    @Nonnull
    public Query neq(Object value) {
      Preconditions.checkNotNull(value, "value can't be null.");
      query.criteria.add(NotEqualsCriteria.create(query.key, value));
      return query;
    }

    /**
     * Adds a {@link LessThanCriteria} to the {@link Query}.
     *
     * @param number The upper bound
     * @return The {@link Query}
     */
    @Nonnull
    public Query lt(Object number) {
      Preconditions.checkNotNull(number, "value can't be null.");
      query.criteria.add(LessThanCriteria.create(query.key, number));
      return query;
    }

    /**
     * Adds a {@link LessThanOrEqualCriteria} to the {@link Query}.
     *
     * @param number The upper bound
     * @return The {@link Query}
     */
    @Nonnull
    public Query lte(Object number) {
      Preconditions.checkNotNull(number, "value can't be null.");
      query.criteria.add(LessThanOrEqualCriteria.create(query.key, number));
      return query;
    }

    /**
     * Adds a {@link GreaterThanCriteria} to the {@link Query}.
     *
     * @param number The lower bound
     * @return The {@link Query}
     */
    @Nonnull
    public Query gt(Object number) {
      Preconditions.checkNotNull(number, "value can't be null.");
      query.criteria.add(GreaterThanCriteria.create(query.key, number));
      return query;
    }

    /**
     * Adds a {@link GreaterThanOrEqualCriteria} to the {@link Query}.
     *
     * @param number The lower bound
     * @return The {@link Query}
     */
    @Nonnull
    public Query gte(Object number) {
      Preconditions.checkNotNull(number, "value can't be null.");
      query.criteria.add(GreaterThanOrEqualCriteria.create(query.key, number));
      return query;
    }

    /**
     * Adds a {@link BetweenCriteria} to the {@link Query}.
     *
     * @param lowerBound The lower bound of the {@link BetweenCriteria}
     * @param upperBound The upper bound of the {@link BetweenCriteria}
     * @return The {@link Query}
     */
    @Nonnull
    public Query between(Object lowerBound, Object upperBound) {
      Preconditions.checkNotNull(lowerBound, "lowerBound can't be null.");
      Preconditions.checkNotNull(upperBound, "upperBound can't be null.");
      query.criteria.add(BetweenCriteria.create(query.key, lowerBound, upperBound));
      return query;
    }
  }
}
