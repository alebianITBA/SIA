package gps.api;

import gps.exception.NotApplicableException;

/**
 * GpsRule interface.
 */
public interface GpsRule {

  /**
   * Provides the cost of the rule.
   *
   * @return The cost of the rule.
   */
  Integer getCost();

  /**
   * Provides the name of the rule so it can be clearly identified by an human being.
   *
   * @return The name of the rule.
   */
  String getName();

  /**
   * Computes the next state based on the previous one.
   * The next state is the result of applying the rule to the previous state.
   *
   * @param state The previous state of the problem.
   * @return The next state of the problem.
   * @throws NotApplicableException If the rule can not be applied to the received state.
   */
  GpsState evalRule(GpsState state) throws NotApplicableException;
}