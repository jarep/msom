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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 *
 * @author jarep
 */
@Entity
public class Model extends AbstractEntity {

    /**
     * Model custom name
     */
    private String name;
    
    @OneToOne
    private ControllerUnit firstControllerUnit;
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy="model") // not sure
    private Set<ControllerUnit> controllerUnits = new HashSet<>();

    public Model() {
    }
    
    public Model(String name) {
        this.name = name;
    }

    public Model(String name, ControllerUnit firstControllerUnit, Set<ControllerUnit> controllerUnits) {
        this.name = name;
        this.firstControllerUnit = firstControllerUnit;
        this.controllerUnits = controllerUnits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ControllerUnit getFirstControllerUnit() {
        return firstControllerUnit;
    }
    
    public void setFirstControllerUnit(ControllerUnit firstControllerUnit) {
        this.firstControllerUnit = firstControllerUnit;
    }
    
    public Set<ControllerUnit> getControllerUnits() {
        return controllerUnits;
    }
    
    public void setControllerUnits(Set<ControllerUnit> controllerUnits) {
        this.controllerUnits = controllerUnits;
    }
    

}
