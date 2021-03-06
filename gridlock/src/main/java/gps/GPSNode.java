package gps;

import gps.api.GPSRule;
import gps.api.GPSState;

public class GPSNode {

  private GPSState state;

  private GPSNode parent;

  private Integer cost;

  private GPSRule generationRule;

  public GPSNode(GPSState state, Integer cost, GPSRule generationRule) {
    this.state = state;
    this.cost = cost;
    this.generationRule = generationRule;
  }

  public GPSNode getParent() {
    return parent;
  }

  public void setParent(GPSNode parent) {
    this.parent = parent;
  }

  public GPSState getState() {
    return state;
  }

  public Integer getCost() {
    return cost;
  }

  @Override
  public String toString() {
    return state.toString();
  }

  /**
   * TODO
   * @return solution as string.
   */
  public String getSolution() {
    if (this.parent == null) {
      return this.state.toString();
    }
    return this.parent.getSolution() + this.generationRule.getName() + "\n";
  }

  public GPSRule getGenerationRule() {
    return generationRule;
  }

  public void setGenerationRule(GPSRule generationRule) {
    this.generationRule = generationRule;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    GPSNode other = (GPSNode) obj;
    if (state == null) {
      if (other.state != null) {
        return false;
      }
    } else if (!state.equals(other.state)) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = state.hashCode();
    if (parent != null) {
      result = 31 * result + parent.hashCode();
    }
    result = 31 * result + cost.hashCode();
    return result;
  }
}
