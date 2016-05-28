/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException;

/**
 * Task dispatcher represents Controller Unit
 *
 * @author jarep
 */
public interface TaskDispatcher  extends IModelObject<ControllerUnit> {

    /**
     * Set name for this Task Dispatcher
     *
     * @param name Task Dispatcher name
     */
    public void setName(String name);

    /**
     * Get name of this Task Dispatcher
     *
     * @return Task Dispatcher name
     */
    public String getName();

    /**
     * Create new Processing Unit for this Task Dispatcher.
     *
     * @param name Name of Processing Unit
     * @param cores Number of cores
     * @param Efficiency Value of efficiency
     * @return Created Processing Unit
     */
    public ProcessingUnit createProcessingUnit(String name, Integer cores, Integer Efficiency);

    /**
     * Add existing Processing Unit to this Task Dispatcher.
     *
     * @param processingUnit Processing Unit
     */
    public void addProcessingUnit(ProcessingUnit processingUnit);

    /**
     * Get all Processing Units assigned to this Task Dispatcher
     *
     * @return List of Processing Units
     */
    public List<ProcessingUnit> getProcessingUnits();

    /**
     * Define processing path, if forwardTo is this Task Dispatcher or null,
     * task should be finished.
     *
     * @param type Type of task
     * @param processing
     * @param forwardTo
     */
    public void definePath(Type type, boolean processing, TaskDispatcher forwardTo);

    /**
     * Define processing path, if forwardTo is this Task Dispatcher or null,
     * task should be finished.
     *
     * @param path
     */
    public void definePath(Path path);

    /**
     * Get processing paths coming out from this task dispatcher.
     *
     * @return List of paths
     */
    public List<Path> getComingOutPaths();

    /**
     * Remove Processing Paths coming out from this Task Dispatcher to another
     * (or to this when task should be finished in this Task Dispatcher).
     *
     * @return {@code true} if success, else {@code false}.
     */
    public boolean removeAllPathFromThisTaskDispatcher();

    /**
     * Remove Processing Paths which leading to this Task Dispatcher from
     * another (or from the this Task Dispatcher when task should be finished).
     *
     * @return {@code true} if success, else {@code false}.
     */
    public boolean removeAllPathsLeadsToThisTaskDispatcher();

    /**
     * Set given Type to processing by change existing processing path or create
     * new processing path if doesn't exist. When create new path also set given
     * type to finished.
     *
     * @param type
     */
    public void setTypeToProcessing(Type type);

    /**
     * Set whether given Type should be processed in this Task Dispatcher by
     * change existing processing path or create new processing path if doesn't
     * exist. When create new path also set given type to finished.
     *
     * @param type
     * @param processing
     */
    public void setTypeToProcessing(Type type, boolean processing);

    /**
     * Set given Type to finished by change existing processing path or create
     * new processing path if doesn't exist. When create new path also set given
     * type to not processing in this Task Dispatcher.
     *
     * @param type
     */
    public void setTypeToFinished(Type type);

    /**
     * Set given Type to forwaRrding to given Task Dispatcher by change existing
     * processing path or create new processing path if doesn't exist. When
     * create new path also set given type to not processing in this Task
     * Dispatcher.
     *
     * If given Task Dispatcher is this Task Dispatcher, task should be
     * finished.
     *
     * @param type
     * @param forwardTo
     */
    public void setTypeToForwarding(Type type, TaskDispatcher forwardTo);

    /**
     * Check that given Controller Unit can processing all types (posses
     * required modules) which should be processed in this Controller based on
     * Processing Paths.
     *
     * Check that for all received types was defined appropriate Processing
     * Path.
     *
     * @return
     * @throws pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion
     * @throws
     * pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException
     */
    public boolean validate() throws PathDefinitionExcpetion, ProcessingAbilityException;

    /**
     * Get list of types to processing in this Task Dispatcher
     *
     * @return List of types to processing
     */
    public List<Type> getTypesToProcessing();

    /**
     * Get list of types to finished in this Task Dispatcher
     *
     * @return List of types to finished
     */
    public List<Type> getTypesToFinished();

    /**
     * Receive task from Processing Unit after processing.
     *
     * @param task Processed task
     * @throws pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException
     * when task was processed by foreign Processing Unit
     */
    public void returnTaskFromProcessingUnit(Task task) throws SystemIntegrityException;

    /**
     * Receive task from other Task Dispatcher.
     *
     * @param task Task to handle
     * @throws
     * pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion
     * when this task was received second time in this simulation
     * @throws pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion
     * when not defined path which leads given Type of task to this Task
     * Dispatcher or not defined what to do with this task.
     */
    public void receiveTask(Task task) throws PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion;

}
