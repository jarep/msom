/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 *
 * @author jarep
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"task_id", "model_id"})})
public class TaskProbability extends AbstractEntity {

    /**
     * Distribution related with this probability.
     */
    @ManyToOne
    @JoinColumn(name = "distribution_id", nullable = false)
    private Distribution distribution;

    /**
     * Task related with this probability.
     */
    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    /**
     * Model related with this probability.
     */
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    public TaskProbability() {
    }

    public TaskProbability(Distribution distribution, Task task, Model model) {
        this.distribution = distribution;
        this.task = task;
        this.model = model;
    }

    public Distribution getDistribution() {
        return distribution;
    }

    public void setDistribution(Distribution distribution) {
        this.distribution = distribution;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

}
