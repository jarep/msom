/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.atomic.AtomicLong;
import pl.edu.uj.fais.wpz.msom.entities.Distribution;
import pl.edu.uj.fais.wpz.msom.entities.Task;

/**
 *
 * @author jarep
 */
public class TaskEntityWrapper {

    private final Task taskEntity;
    private final Long typeId;
    private final AtomicLong instanceCounter = new AtomicLong();
    private final Distribution distributionEntity;
    
    public TaskEntityWrapper(Task taskEntity, Long typeId, Distribution distributionEntity) {
        this.taskEntity = taskEntity;
        this.typeId = typeId;
        this.distributionEntity = distributionEntity;
    }

    public Task getTaskEntity() {
        return taskEntity;
    }

    public Long getTypeId() {
        return typeId;
    }
    
    public Long incrementAndGetInstanceCounter(){
        return instanceCounter.incrementAndGet();
    }

    public Distribution getDistributionEntity() {
        return distributionEntity;
    }

    

}
