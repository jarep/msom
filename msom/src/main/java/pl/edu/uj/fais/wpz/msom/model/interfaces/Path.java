/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;

/**
 * Represents processing path coming out from task dispatcher.
 *
 * @author jarep
 */
public interface Path extends IModelObject<ProcessingPath> {

    /**
     * Get related type of task
     *
     * @return Type of task
     */
    public Type getType();

    /**
     * Check that this type should be processing.
     *
     * @return {@code true} or {@code false}
     */
    public boolean isProcessing();

    /**
     * Return next Task Dispatcher or null if task should be finished.
     *
     * @return TaskDispatcher or null
     */
    public TaskDispatcher getNextTaskDispatcher();
    
}
