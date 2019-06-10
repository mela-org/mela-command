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
public final class LessThanOrEqualCriteria implements Criteria {

  private final String key;
  private final Object value;


  private LessThanOrEqualCriteria(String key, Object value) {
    this.key = key;
    this.value = value;
  }

  /**
   * Returns the value that this criteria should match.
   *
   * @return The value as an {@link Object}
   */
  @Nonnull
  public Object getNumber() {
    return value;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void accept(CriteriaVisitor visitor) {
    visitor.visitLessThanOrEqual(this);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key", key)
        .add("value", value)
        .toString();
  }

  public static Criteria create(String key, Object value) {
    Preconditions.checkNotNull(key, "key can't be null.");
    Preconditions.checkNotNull(value, "value can't be null.");
    return new LessThanOrEqualCriteria(key, value);
  }

}
