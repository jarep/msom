/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 * entity of processing module
 *
 * @author paweldylag
 */
@Entity
public class Module extends AbstractEntity {

    /**
     * Module custom name
     */
    private String name;
    /**
     * Number of servers cores
     */
    private Integer cores;
    /**
     * Server's efficiency value
     */
    private Integer efficiency;
    /** Possible task types to process */
    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="task_types_per_module", 
                joinColumns={@JoinColumn(name="module_id")}, 
                inverseJoinColumns={@JoinColumn(name="tasktype_id")})
    private Set<TaskType> taskTypes = new HashSet<>(0);

    @ManyToOne
    @JoinColumn(name = "controllerunit_id")
    private ControllerUnit controllerUnit;

    /**
     * Default Constructor required by hibernate
     */
    public Module() {
    }

    /**
     * Constructor with cores and efficiency set
     * @param cores
     * @param efficiency
     */
    public Module(Integer cores, Integer efficiency) {
        this.name = "";
        this.cores = cores;
        this.efficiency = efficiency;
    }

    /**
     *Constructor with name and cores and efficiency set
     * @param name
     * @param cores
     * @param efficiency
     */
    public Module(String name, Integer cores, Integer efficiency) {
        this.name = name;
        this.cores = cores;
        this.efficiency = efficiency;
    }

    /**
     * Constructor with name, cores, efficiency and controller unit set
     * @param name
     * @param cores
     * @param efficiency
     * @param controllerUnit
     */
    public Module(String name, Integer cores, Integer efficiency, ControllerUnit controllerUnit) {
        this.name = name;
        this.cores = cores;
        this.efficiency = efficiency;
        this.controllerUnit = controllerUnit;
    }

    /**
     *
     * @return controller unit
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
     * @return name of model
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return number of cores
     */
    public Integer getCores() {
        return cores;
    }

    /**
     *
     * @param cores
     */
    public void setCores(Integer cores) {
        this.cores = cores;
    }

    /**
     *
     * @return efficiency
     */
    public Integer getEfficiency() {
        return efficiency;
    }

    /**
     *
     * @param efficiency to set
     */
    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    /**
     * Add task type to nodule
     * @param taskType to add
     * @return true if executed, else false
     */
    public boolean addTaskType(TaskType taskType) {
        return this.taskTypes.add(taskType);
    }

    /**
     * Remove task type to nodule
     * @param taskType to remove
     * @return true if executed, else false
     */
    public boolean removeTaskType(TaskType taskType) {
        return this.taskTypes.remove(taskType);
    }

    /**
     * Checks if it has a type of task
     * @param taskType
     * @return true if contains, else false
     */
    public boolean containsTaskType(TaskType taskType) {
        return this.taskTypes.contains(taskType);
    }

    /**
     *
     * @return module task types
     */
    public Set<TaskType> getTaskTypes() {
        return taskTypes;
    }

    /**
     *
     * @param taskTypes to set
     */
    public void setTaskTypes(Set<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }

}
