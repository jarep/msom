/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;

/**
 * Generator distribution probability generators
 * @author Adam
 */
public interface TimeIntervalGenerator {
    
    /**
     * @return Generate a random value sampled from this distribution. 
     */
    public int getNext();
    
    /**
     * Set you distribution probability
     * @param A first
     * @param B second
     */
    public void setParamters(double A,double B);
    
    /**
     * @return list of parameters
     */
    public List<Double> getParameters();
    
    /**
     *
     * @return type of distribution
     */
    public DistributionType getDistributionType();
    
    
}
