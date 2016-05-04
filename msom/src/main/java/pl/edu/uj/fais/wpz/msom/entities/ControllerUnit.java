/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import java.util.List;
import javax.persistence.Entity;
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

    @OneToMany
    private List<Module> modules;

    public ControllerUnit() {
    }

    public ControllerUnit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

}
