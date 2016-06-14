/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 * Entity represents processing path, which describes the rules for processing a
 * particular type of tasks in Controller Unit.
 *
 * @author jarep
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"controllerunit_id", "tasktype_id"})})
public class ProcessingPath extends AbstractEntity {

    /**
     * Controller unit related with this processing path.
     */
    @ManyToOne
    @JoinColumn(name = "controllerunit_id", nullable = false)
    private ControllerUnit controllerUnit;

    /**
     * Type of task related with this processing path.
     */
    @ManyToOne
    @JoinColumn(name = "tasktype_id", nullable = false)
    private TaskType taskType;

    /**
     * Whether the tasks of specific type should be executed in any modules
     * plugged in to this controller unit.
     */
    private Boolean processing;

    /**
     * Next controller unit for type of task. If it is the same controller unit,
     * task should be finished. Otherwise task should be forwarded to the next
     * controller unit.
     */
    @ManyToOne
    @JoinColumn(name = "nextcontrollerunit_id", nullable = false)
    private ControllerUnit nextControllerUnit;

    /**
     * Default Constructor required by hibernate
     */
    public ProcessingPath() {
    }

    /**
     * Constructor with settings
     * @param controllerUnit to set
     * @param taskType to set
     * @param processing to set
     * @param nextControllerUnit to set
     */
    public ProcessingPath(ControllerUnit controllerUnit, TaskType taskType, Boolean processing, ControllerUnit nextControllerUnit) {
        this.controllerUnit = controllerUnit;
        this.taskType = taskType;
        this.processing = processing;
        this.nextControllerUnit = nextControllerUnit;
    }

    /**
     *
     * @return unit controller
     */
    public ControllerUnit getControllerUnit() {
        return controllerUnit;
    }

    /**
     *
     * @param controllerUnit to set
     */
    public void setControllerUnit(ControllerUnit controllerUnit) {
        this.controllerUnit = controllerUnit;
    }

    /**
     *
     * @return task type of processing type 
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     *
     * @param taskType to set on path
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    /**
     *
     * @return processing status
     */
    public Boolean getProcessing() {
        return processing;
    }

    /**
     *
     * @param processing
     */
    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }

    /**
     *
     * @return next controller unit
     */
    public ControllerUnit getNextControllerUnit() {
        return nextControllerUnit;
    }

    /**
     * Set next unit controller
     * @param nextControllerUnit to set next unit conntroller
     */
    public void setNextControllerUnit(ControllerUnit nextControllerUnit) {
        this.nextControllerUnit = nextControllerUnit;
    }

}
