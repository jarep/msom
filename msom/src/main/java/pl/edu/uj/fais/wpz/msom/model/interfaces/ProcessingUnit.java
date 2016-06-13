/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;

/**
 * Processing unit represents Module.
 *
 * @author jarep
 */
public interface ProcessingUnit extends IModelObject<Module>, IActivatable {

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
    @Override
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
     * Get number of cores in Processing Unit
     *
     * @return Number of cores
     */
    public Integer getNumberOfCores();

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
     * Get list of types which can be processed in this processing unit.
     *
     * @return Available types
     */
    public List<Type> getAvailableTypes();

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

    /**
     * Get list of waiting tasks in this processing unit
     *
     * @return list of tasks
     */
    public List<Task> getWaitingTasks();

    /**
     * Get list of currently processing tasks
     *
     * @return list of tasks
     */
    public List<Task> getProcessingTasks();

    /**
     * Get number of currently processing tasks
     *
     * @return No. of processing tasks
     */
    public int getNumberOfProcessingTasks();

    /**
     * Get average percentage of execution currently processing tasks.
     *
     * @return execution percentage
     */
    public double getPercentOfExecutionCurrentTasks();

    /**
     * Get average waiting time in the queue
     *
     * @return average waiting time
     */
    public int getAvgWaitingTime();

    /**
     * Get average processing time.
     *
     * @return average processing time
     */
    public int getAvgProcessingTime();
    
}
