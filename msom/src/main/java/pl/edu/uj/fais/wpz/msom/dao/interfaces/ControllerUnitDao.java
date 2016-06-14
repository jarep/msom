/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 * Data access object for Controller unit
 * @author jarep
 */
public interface ControllerUnitDao extends IDao<ControllerUnit, Long> {

    /**
     * Tries to remove controller unit from the data base.
     *
     * @param controllerUnit
     * @return {@code true} if controllerUnit is not assigned to any module or
     * processing path, else {@code false}.
     */
    public boolean removeControllerUnit(ControllerUnit controllerUnit);

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
     * Get all Controller Units assigned to given Model.
     *
     * @param model Model
     * @return List of founded Controller Units
     */
    public List<ControllerUnit> getControllersByModel(Model model);

}
