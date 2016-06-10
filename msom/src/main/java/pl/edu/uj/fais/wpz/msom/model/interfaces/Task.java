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
public interface Task extends IModelObject<pl.edu.uj.fais.wpz.msom.entities.Task>, Activatable {

    /**
     * Get type of task
     *
     * @return Type of task
     */
    public Type getType();

    /**
     * Get name of task
     *
     * @return Name of task
     */
    public String getName();

    /**
     * Get difficulty value of task.
     *
     * @return Integer value represents difficulty
     */
    public Integer getDifficulty();

    /**
     * Process task.
     *
     * @return {@code true} when successfully processed, {@code false} if task
     * was already finished
     */
    public boolean processTask();

    /**
     * Finish task.
     */
    public void finishTask();

    /**
     * Check that the task is finished.
     *
     * @return {@code true} if finished, else {@code false}
     */
    public boolean isFinished();

    /**
     * Get current sum of processing times.
     *
     * @return The number of milliseconds
     */
    public Long getProcessingTime();

    /**
     * Get current sum of waiting times.
     *
     * @return The number of milliseconds
     */
    public Long getWaitingTime();

    /**
     * Get how many times this task was executed.
     *
     * @return execution counter
     */
    public int getExecutionCounter();

    /**
     * Get percentage of current execution.
     *
     * @return percentage of execution
     */
    public double getPercentageOfCurrentExecution();

    /**
     * Get Processing Unit which processed this task as the last.
     *
     * @return
     */
    public ProcessingUnit getLastProcessingUnit();

}
