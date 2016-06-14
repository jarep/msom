/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

/**
 * Defines types of probability distribution
 * @author paweldylag
 */
public enum DistributionType {
  
    /**
     *Linear type of distribution
     */
    LINEAR("linear"),

    /**
     * Gaussian type of distribution
     */
    GAUSSIAN("gaussian"),
  
    /**
     * Unkown type of distribution
     */
    UNKNOWN("unknown");  
    
    /**
     * Name of distribution type
     */
    public final String text;

  DistributionType(String text) {
    this.text = text;
  }

    /**
     * Change string to type of distribution
     * @param typeString text value
     * @return type of distribution
     */
    public static DistributionType fromString(String typeString) {
    if (typeString == null) throw new IllegalArgumentException();
    for (DistributionType type : DistributionType.values()) {
      if (typeString.equals(type.text)) {
        return type;
      }
    }
    return UNKNOWN;
  }

  @Override
  public String toString() {
    return this.text;
  }
    
}
