/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.entities.Task;

/**
 *
 * @author jarep
 */
public class TaskEntityWithTypeId {

    private final Task taskEntity;
    private final Long typeId;

    public TaskEntityWithTypeId(Task taskEntity, Long typeId) {
        this.taskEntity = taskEntity;
        this.typeId = typeId;
    }

    public Task getTaskEntity() {
        return taskEntity;
    }

    public Long getTypeId() {
        return typeId;
    }

}
