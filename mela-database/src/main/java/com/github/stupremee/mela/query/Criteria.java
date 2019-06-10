package com.github.stupremee.mela.query;

/**
 * https://github.com/Stupremee
 *
 * @author Stu
 * @since 10.06.19
 */
public interface Criteria {

  /**
   * Returns the key of this {@link Criteria}.
   *
   * @return The key name
   */
  String getKey();

  /**
   * Visits this criteria via the given {@link CriteriaVisitor}.
   *
   * @param visitor The visitor to visit
   */
  void accept(CriteriaVisitor visitor);

}
