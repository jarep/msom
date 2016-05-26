/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

/**
 *
 * @author jarep
 */
public interface Type {

    /**
     * Get name of type
     *
     * @return Name of type
     */
    public String getName();

    /**
     * Get difficulty value of type.
     *
     * @return Integer value represents difficulty
     */
    public Integer getDifficulty();

    /**
     * Get the average processing time for tasks of this type in current
     * simulation.
     *
     * @return The number of milliseconds
     */
    public Integer getAvgProcessingTime();

    /**
     * Get the average waiting time for tasks of this type in current
     * simulation.
     *
     *
     * @return The number of milliseconds
     */
    public Integer getAvgWaitingTime();

}
