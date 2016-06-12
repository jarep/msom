/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web.helpers;

import java.util.ArrayList;
import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;

/**
 *
 * @author debra
 */
public class ModuleForm {
    private Long id;
    private String name;
    private Integer cores;
    private Integer efficiency;
    private ControllerUnit controllerUnit;
    private List<Long> taskTypes = new ArrayList<>();

    /**
     * Constructor
     */
    public ModuleForm() {}

    /**
     * Contructor with settings
     * @param id
     * @param name
     * @param cores
     * @param efficiency
     * @param controllerUnit
     * @param taskTypes
     */
    public ModuleForm(Long id, String name, Integer cores, Integer efficiency, 
            ControllerUnit controllerUnit, List<Long> taskTypes) {
        this.id = id;
        this.name = name; 
        this.cores = cores;
        this.efficiency = efficiency;
        this.controllerUnit = controllerUnit;
        this.taskTypes = taskTypes;
    }
    
    /**
     *
     * @return id module form
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id module form
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return name module form
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name module form
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
     * @param cores to set
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
     *
     * @return list of task types
     */
    public List<Long> getTaskTypes() {
        return taskTypes;
    }

    /**
     *
     * @param taskTypes to set
     */
    public void setTaskTypes(List<Long> taskTypes) {
        this.taskTypes = taskTypes;
    }
}
