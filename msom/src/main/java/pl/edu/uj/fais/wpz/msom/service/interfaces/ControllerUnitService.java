/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jarep
 */
public interface ControllerUnitService extends IService<ControllerUnit> {

    /**
     * Tries to remove Controller Unit from the system.
     *
     * @param controllerUnit Controller Unit to remove
     * @return {@code true} if controllerUnit is not assigned to any module or
     * processing path, else {@code false}.
     */
    @Override
    public boolean remove(ControllerUnit controllerUnit);

    /**
     * Tries to remove Controller Unit and referenced Modules and Processing
     * Paths
     *
     * @see Module
     * @see ProcessingPath
     *
     * @param controllerUnit Controller Unit to remove
     * @return {@code true} if success, else {@code false}.
     */
    public boolean removeWithModulesAndPaths(ControllerUnit controllerUnit);

    /**
     * Get Controller Unit to which must be transferred tasks of a particular
     * type from given Controller Unit.
     *
     * If returned Controller Unit is equal to given Controller Unit, task
     * should be finished in given Controller Unit.
     *
     * @param controllerUnit
     * @param taskType Type of tasks
     * @return ControllerUnit to which must be transferred task (can be equals
     * to given controllerUnit)
     */
    public ControllerUnit getNextControllerUnit(ControllerUnit controllerUnit, TaskType taskType);

    /**
     * Get Task Types which can be processing in given Controller Unit (based on
     * Modules related to this Controller Unit).
     *
     * @param controllerUnit Controller Unit
     * @return List of available types
     */
    public List<TaskType> getAvailableTaskTypesToProcessingInControllerUnit(ControllerUnit controllerUnit);

    /**
     * Check that given Controller Unit can processing all types (posses
     * required modules) which should be processed in this Controller based on
     * Processing Paths.
     *
     * @param controllerUnit Controller Unit to validate
     * @return
     */
    public boolean validateControllerUnitProcessingPotentiality(ControllerUnit controllerUnit);

    /**
     * Check that for all received types was defined appropriate Processing
     * Path.
     *
     * @param controllerUnit Controller Unit to validate
     * @return
     */
    public boolean validateControllerUnitPathDefinitions(ControllerUnit controllerUnit);

}
