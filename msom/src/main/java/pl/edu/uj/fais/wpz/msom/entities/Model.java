/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import javax.persistence.Entity;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 * Entity of processing system model
 * @author jarep
 */
@Entity
public class Model extends AbstractEntity {

    /**
     * Model custom name
     */
    private String name;

    /**
     * Default Constructor required by hibernate
     */
    public Model() {
    }

    /**
     * Constructor with name set
     * @param name to set
     */
    public Model(String name) {
        this.name = name;
    }

    /**
     * @return model name 
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

}
