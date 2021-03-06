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
 * Basic Module (Single server) entity structure
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

    public Module() {
    }

    public Module(Integer cores, Integer efficiency) {
        this.name = "";
        this.cores = cores;
        this.efficiency = efficiency;
    }

    public Module(String name, Integer cores, Integer efficiency) {
        this.name = name;
        this.cores = cores;
        this.efficiency = efficiency;
    }

    public Module(String name, Integer cores, Integer efficiency, ControllerUnit controllerUnit) {
        this.name = name;
        this.cores = cores;
        this.efficiency = efficiency;
        this.controllerUnit = controllerUnit;
    }

    public ControllerUnit getControllerUnit() {
        return controllerUnit;
    }

    public void setControllerUnit(ControllerUnit controllerUnit) {
        this.controllerUnit = controllerUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCores() {
        return cores;
    }

    public void setCores(Integer cores) {
        this.cores = cores;
    }

    public Integer getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    public boolean addTaskType(TaskType taskType) {
        return this.taskTypes.add(taskType);
    }

    public boolean removeTaskType(TaskType taskType) {
        return this.taskTypes.remove(taskType);
    }

    public boolean containsTaskType(TaskType taskType) {
        return this.taskTypes.contains(taskType);
    }

    public Set<TaskType> getTaskTypes() {
        return taskTypes;
    }

    public void setTaskTypes(Set<TaskType> taskTypes) {
        this.taskTypes = taskTypes;
    }

}
