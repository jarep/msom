/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
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

    public TaskType() {
    }

    public TaskType(String name, Integer difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

}
