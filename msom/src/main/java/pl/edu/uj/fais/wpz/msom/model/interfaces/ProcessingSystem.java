/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException;

/**
 * Processing system represents Model.
 *
 * @author jarep
 */
public interface ProcessingSystem {

    /**
     * Set name for this Processing System
     *
     * @param name Processing System name
     */
    public void setName(String name);

    /**
     * Start simulation. Validate Processing System before start.
     *
     * @return {@code true} if successfully started, otherwise {@code false}
     */
    public boolean startSimulation();

    /**
     * Stop simulation.
     */
    public void stopSimulation();

    /**
     * Create new Task Dispatcher and assign to this Processing System.
     *
     * @param name Name of task dispatcher
     * @return created task dispatcher object
     */
    public TaskDispatcher createTaskDispatcher(String name);

    /**
     * Add existing Task Dispatcher to this Processing System.
     *
     * @param taskDispatcher Task Dispatcher
     * @throws SystemIntegrityException when correlated processing paths leads
     * to other Processing System
     */
    public void addTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException;

    /**
     * Get list of Task Dispatchers assigned to this Processing System.
     *
     * @return List of founded task dispatchers
     */
    public List<TaskDispatcher> getTaskDispatchers();

    /**
     * Set first Task Dispatcher, which should receive tasks from task
     * generator.
     *
     * @param taskDispatcher First task dispatcher
     * @throws SystemIntegrityException if given Task Dispatcher is assigned to
     * other Processing System
     */
    public void setFirstTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException;

    /**
     * Get first Task Dispatcher, which should receive tasks from task generator
     * or null if not defined.
     *
     * @return First task dispatcher or null
     */
    public TaskDispatcher getFirstTaskDispatcher();

    /**
     * Validate Processing System, validate all Task Dispatcher and check for
     * infinitive loop in processing paths.
     *
     * @throws SystemIntegrityException when some processing path leads to other
     * Processing System
     * @throws
     * pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException
     * @throws pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion
     * @throws
     * pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion
     *
     * @return {@code true} if validation is successful, otherwise {@code false}
     */
    public boolean validate() throws SystemIntegrityException, ProcessingAbilityException, PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion;

    /**
     * Set type of distribution using in this Processing System to generate
     * tasks.
     *
     * @param distributionType Type of distribution
     * @return {@code true} if successfully changed, otherwise {@code false}
     */
    public boolean setDistributionType(DistributionType distributionType);

    /**
     * Get type of distribution using in this Processing System to generate
     * tasks.
     *
     * @return Type of distribution
     */
    public DistributionType getDistributionType();

}
