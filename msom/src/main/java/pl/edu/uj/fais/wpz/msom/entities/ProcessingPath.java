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

    public ProcessingPath() {
    }

    public ProcessingPath(ControllerUnit controllerUnit, TaskType taskType, Boolean processing, ControllerUnit nextControllerUnit) {
        this.controllerUnit = controllerUnit;
        this.taskType = taskType;
        this.processing = processing;
        this.nextControllerUnit = nextControllerUnit;
    }

    public ControllerUnit getControllerUnit() {
        return controllerUnit;
    }

    public void setControllerUnit(ControllerUnit controllerUnit) {
        this.controllerUnit = controllerUnit;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public Boolean getProcessing() {
        return processing;
    }

    public void setProcessing(Boolean processing) {
        this.processing = processing;
    }

    public ControllerUnit getNextControllerUnit() {
        return nextControllerUnit;
    }

    public void setNextControllerUnit(ControllerUnit nextControllerUnit) {
        this.nextControllerUnit = nextControllerUnit;
    }

}
