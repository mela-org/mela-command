package com.github.stupremee.mela.query.criterias;

import com.github.stupremee.mela.query.Criteria;
import com.github.stupremee.mela.query.CriteriaVisitor;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import java.util.regex.Pattern;
import javax.annotation.Nonnull;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.06.19
 */
public final class RegExCriteria implements Criteria {

  private final String key;
  private final Pattern pattern;


  private RegExCriteria(String key, Pattern pattern) {
    this.key = key;
    this.pattern = pattern;
  }

  /**
   * Returns the pattern that this criteria should match.
   *
   * @return The pattern as an {@link Object}
   */
  @Nonnull
  public Pattern getPattern() {
    return pattern;
  }

  @Override
  public String getKey() {
    return key;
  }

  @Override
  public void accept(CriteriaVisitor visitor) {
    visitor.visitRegEx(this);
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

    if (!(o instanceof RegExCriteria)) {
      return false;
    }

    var other = (RegExCriteria) o;
    return Objects.equal(this.key, other.key)
        && Objects.equal(this.pattern, other.pattern);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(key, pattern);
  }

  /**
   * Static factory method to create a new {@link RegExCriteria}.
   */
  public static Criteria create(String key, Pattern pattern) {
    Preconditions.checkNotNull(key, "key can't be null.");
    Preconditions.checkNotNull(pattern, "pattern can't be null.");
    return new RegExCriteria(key, pattern);
  }
}
