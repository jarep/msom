/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

/**
 *
 * @author paweldylag
 */
public enum DistributionType {
  
  LINEAR("linear"),
  GAUSSIAN("gaussian"),
  POISSON("poisson"),
  UNKNOWN("unknown");  
    
  public final String text;

  DistributionType(String text) {
    this.text = text;
  }

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
