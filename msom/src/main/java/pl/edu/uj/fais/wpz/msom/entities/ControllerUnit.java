/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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

    /**
     * Default Constructor required by hibernate
     */
    public ControllerUnit() {
    }

    /**
     * Constructor with set name
     * @param name to set
     */
    public ControllerUnit(String name) {
        this.name = name;
    }

    /**
     * 
     * @return name of controller
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return model
     */
    public Model getModel() {
        return model;
    }

    /**
     *
     * @param model to set
     */
    public void setModel(Model model) {
        this.model = model;
    }

}
