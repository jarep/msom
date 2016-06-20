/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 * Entity represents controller unit
 *
 * @author jarep
 */
@Entity
public class ControllerUnit extends AbstractEntity {

    /**
     * Controller unit custom name
     */
    private String name;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "controllerUnit") 
    private Set<Module> modules = new HashSet<>();

    public ControllerUnit() {
    }

    public ControllerUnit(String name) {
        this.name = name;
    }
    
    public ControllerUnit(String name, Set<Module> modules) {
        this.name = name;
        this.modules = modules;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
    
    public Set<Module> getModules() {
        return modules;
    }

    public void setModules(Set<Module> modules) {
        this.modules = modules;
    }

}
