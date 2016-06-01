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
    public ModuleForm() {}
    public ModuleForm(Long id, String name, Integer cores, Integer efficiency, 
            ControllerUnit controllerUnit) {
        this.id = id;
        this.name = name; 
        this.cores = cores;
        this.efficiency = efficiency;
        this.controllerUnit = controllerUnit;
    }
    public ModuleForm(Long id, String name, Integer cores, Integer efficiency, 
            ControllerUnit controllerUnit, List<Long> taskTypes) {
        this.id = id;
        this.name = name; 
        this.cores = cores;
        this.efficiency = efficiency;
        this.controllerUnit = controllerUnit;
        this.taskTypes = taskTypes;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public List<Long> getTaskTypes() {
        return taskTypes;
    }
    public void setTaskTypes(List<Long> taskTypes) {
        this.taskTypes = taskTypes;
    }
}
