/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jarep
 */
public interface Type extends IModelObject<TaskType>, IActivatable {

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
    public int getDifficulty();

    
    /**
     * Get the average processing time for tasks of this type in current
     * simulation.
     *
     * @return The number of milliseconds
     */
    public double getAvgProcessingTime();

    /**
     * Get the average waiting time for tasks of this type in current
     * simulation.
     *
     * @return The number of milliseconds
     */
    public double getAvgWaitingTime();

    /**
     * Get number of generated tasks of this type.
     *
     * @return
     */
    public int getNumberOfGeneratedTasks();

    /**
     * Get number of finished tasks of this type.
     *
     * @return
     */
    public int getNumberOfFinishedTasks();

    
}
