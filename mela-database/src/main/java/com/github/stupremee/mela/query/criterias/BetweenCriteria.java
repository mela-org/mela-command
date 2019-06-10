package com.github.stupremee.mela.query.criterias;

import com.github.stupremee.mela.query.Criteria;
import com.github.stupremee.mela.query.CriteriaVisitor;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.06.19
 */
public final class BetweenCriteria implements Criteria {

  private final String key;
  private final Object lowerBound;
  private final Object upperBound;


  private BetweenCriteria(String key, Object lowerBound, Object upperBound) {
    this.key = key;
    this.lowerBound = lowerBound;
    this.upperBound = upperBound;
  }

  /**
   * Returns the lower bound of this between criteria.
   *
   * @return The lower bound
   */
  @Nonnull
  public Object getLowerBound() {
    return lowerBound;
  }

  /**
   * Returns the upper bound of this between criteria.
   *
   * @return The upper bound
   */
  @Nonnull
  public Object getUpperBound() {
    return upperBound;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void accept(CriteriaVisitor visitor) {
    visitor.visitBetween(this);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key", key)
        .add("upperBound", upperBound)
        .add("lowerBound", lowerBound)
        .toString();
  }

  public static Criteria create(String key, Object lowerBound, Object upperBound) {
    Preconditions.checkNotNull(key, "key can't be null.");
    Preconditions.checkNotNull(lowerBound, "lowerBound can't be null.");
    Preconditions.checkNotNull(upperBound, "upperBound can't be null.");
    return new BetweenCriteria(key, lowerBound, upperBound);
  }

}
