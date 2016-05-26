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
 *
 * @author jaroslaw
 */
@Entity
public class Task extends AbstractEntity {

    private String name;

    @ManyToOne
    @JoinColumn(name = "tasktype_id")
    private TaskType taskType;

    public Task() {
    }

    public Task(String name, TaskType taskType) {
        this.name = name;
        this.taskType = taskType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

}
