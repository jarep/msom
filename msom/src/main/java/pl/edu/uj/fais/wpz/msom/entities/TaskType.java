/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import javax.persistence.Entity;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 * Entity represents type of task.
 *
 * @author jaroslaw
 */
@Entity
public class TaskType extends AbstractEntity {

    private String name;
    private Integer difficulty;
    
    /**
     * Constructor
     */
    public TaskType() {
    }

    /**
     * Constructor with name and difficulty set
     * @param name
     * @param difficulty
     */
    public TaskType(String name, Integer difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    /**
     *
     * @return task type name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name task type
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return level of difficulty
     */
    public Integer getDifficulty() {
        return difficulty;
    }
    /**
     *
     * @param difficulty to set
     */
    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

}
