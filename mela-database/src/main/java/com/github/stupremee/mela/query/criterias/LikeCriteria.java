package com.github.stupremee.mela.query.criterias;

import com.github.stupremee.mela.query.Criteria;
import com.github.stupremee.mela.query.CriteriaVisitor;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 11.06.19
 */
public final class LikeCriteria implements Criteria {

  private final String key;
  private final String pattern;


  private LikeCriteria(String key, String pattern) {
    this.key = key;
    this.pattern = pattern;
  }

  /**
   * Returns the pattern that this criteria should match.
   *
   * @return The pattern as an {@link Object}
   */
  @Nonnull
  public String getPattern() {
    return pattern;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void accept(CriteriaVisitor visitor) {
    visitor.visitLike(this);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("key", key)
        .add("pattern", pattern)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o == null) {
      return false;
    }

    if (o == this) {
      return true;
    }

    if (!(o instanceof LikeCriteria)) {
      return false;
    }

    var other = (LikeCriteria) o;
    return Objects.equal(this.key, other.key)
        && Objects.equal(this.pattern, other.pattern);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(key, pattern);
  }

  public static Criteria create(String key, String pattern) {
    Preconditions.checkNotNull(key, "key can't be null.");
    Preconditions.checkNotNull(pattern, "pattern can't be null.");
    return new LikeCriteria(key, pattern);
  }
}
