/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;

/**
 * Processing unit represents Module.
 *
 * @author jarep
 */
public interface ProcessingUnit {

    /**
     * Set name for this Processing Unit
     *
     * @param name Processing Unit name
     */
    public void setName(String name);

    /**
     * Get name of this Processing Unit
     *
     * @return Processing Unit name
     */
    public String getName();
    
    /**
     * Return identity number for this Processing Unit
     *
     * @return Processing Unit ID
     */
    public Long getId();

    /**
     * Set Processing Unit efficiency
     *
     * @param efficiency Processing Unit efficiency
     */
    public void setEfficiency(Integer efficiency);

    /**
     * Get Processing Unit efficiency
     *
     * @return Processing Unit efficiency
     */
    public Integer getEfficiency();

    /**
     * Set number of cores in Processing Units
     *
     * @param cores Number of cores
     */
    public void setCores(Integer cores);

    /**
     * Get number of cores in Processing Units
     *
     * @return Number of cores
     */
    public Integer getCores();

    /**
     * Set given type as supported by this Processing Unit.
     *
     * @param type Type to support
     */
    public void addType(Type type);

    /**
     * Set given type as not supported by this Processing Unit.
     *
     * @param type Type to not support
     */
    public void removeType(Type type);

    /**
     * Assign given Task Dispatcher to this Processing Unit.
     *
     * @param taskDispatcher
     */
    public void setTaskDispatcher(TaskDispatcher taskDispatcher);

    /**
     * Add task to processing queue.
     *
     * @param task Task to process
     * @throws
     * pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException when
     * this Processing Unit doesn't support this type of task.
     */
    public void processTask(Task task) throws ProcessingAbilityException;

    /**
     * Get count of tasks in processing queue.
     *
     * @return Queue length
     */
    public int getQueueLength();

    /**
     * Get value of processing queue - sum of tasks difficulty.
     *
     * @return Queue value
     */
    public int getQueueValue();

}
