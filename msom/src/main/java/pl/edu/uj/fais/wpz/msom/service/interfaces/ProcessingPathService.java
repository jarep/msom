/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jarep
 */
public interface ProcessingPathService extends IService<ProcessingPath> {

    /**
     * Get all Processing Paths related to given Controller Unit.
     *
     * @param controllerUnit Controller Unit
     * @return List of founded Processing Paths
     */
    public List<ProcessingPath> getProcessingPathsByControllerUnit(ControllerUnit controllerUnit);

    /**
     * Get Processing Paths which leading to given Controller Unit from another
     * Controller Unit (or from the same Controller Unit when task should be
     * finished in given Controller Unit).
     *
     * @param controllerUnit
     * @return
     */
    public List<ProcessingPath> getProcessingPathsLeadingToTheControllerUnit(ControllerUnit controllerUnit);

    /**
     * Get Processing Paths which coming out from given Controller Unit to
     * another Controller Unit (or the same Controller Unit when task should be
     * finished in given Controller Unit).
     *
     * @param controllerUnit Controller Unit
     * @return List of founded Processing Paths
     */
    public List<ProcessingPath> getProcessingPathsComingOutFromTheControllerUnit(ControllerUnit controllerUnit);

    /**
     * Get unique Processing Path related to given Controller Unit and Task
     * Type.
     *
     * @param controllerUnit Controller Unit
     * @param taskType Task Type
     * @return Founded Processing Path
     */
    public ProcessingPath getProcessingPathByTaskTypeAndControllerUnit(ControllerUnit controllerUnit, TaskType taskType);

    /**
     * Get Task Types which should be processing in given Controller Unit.
     *
     * @param controllerUnit
     * @return
     */
    public List<TaskType> getTaskTypesToProcessingByControllerUnit(ControllerUnit controllerUnit);

    /**
     * Get Task Types which should be finished by given Controller Unit, whether
     * they are to be processed or not. (For Processing Path related to returned
     * TaskType and given Controller Unit, NextControllerUnit equals to
     * ControllerUnit.)
     *
     * @see ProcessingPath
     *
     * @param controllerUnit Controller Unit
     * @return List of task types which should be finished.
     */
    public List<TaskType> getTaskTypesToFinishedByControllerUnit(ControllerUnit controllerUnit);

    /**
     * Get all Task Types known by given Controller Unit - all types for which
     * have defined path in given controller unit.
     *
     * @param controllerUnit
     * @return list of Task Types
     */
    public List<TaskType> getKnownTaskTypesByControllerUnit(ControllerUnit controllerUnit);

    /**
     * Remove Processing Paths coming out from given Controller Unit to another
     * Controller Unit (or to the same Controller Unit when task should be
     * finished in given Controller Unit).
     *
     * @param controllerUnit
     * @return {@code true} if success, else {@code false}.
     */
    public boolean removeProcessingPathsByControllerUnit(ControllerUnit controllerUnit);

    /**
     * Remove Processing Paths which leading to given Controller Unit from
     * another Controller Unit (or from the same Controller Unit when task
     * should be finished in given Controller Unit).
     *
     * @param nextControllerUnit Controller Unit
     * @return {@code true} if success, else {@code false}.
     */
    public boolean removeProcessingPathsByNextControllerUnit(ControllerUnit nextControllerUnit);

    /**
     * Add processing path if Controller Unit and Next Controller Unit are
     * assigned to the same Model.
     *
     * @param processingPath
     * @return {@code true} if success, else {@code false}.
     */
    @Override
    public boolean add(ProcessingPath processingPath);

}
