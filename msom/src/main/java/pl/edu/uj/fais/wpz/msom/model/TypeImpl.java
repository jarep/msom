/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class TypeImpl extends ActivatableAbstractModelObject<TaskType, TaskTypeService> implements Type {

    private final AtomicInteger numberOfGeneratedTasks = new AtomicInteger();
    private final AtomicInteger numberOfFinishedTasks = new AtomicInteger();
    private final AtomicLong totalWaitingTime = new AtomicLong();
    private final AtomicLong totalProcessingTime = new AtomicLong();
    private final AtomicLong processingCounter = new AtomicLong();

    public TypeImpl(TaskType entityObject, TaskTypeService taskTypeService) {
        super(entityObject, taskTypeService);
    }

    @Override
    public String getName() {
        executionReadLock.lock();
        try {
            return getEntityObject().getName();
        } finally {
            executionReadLock.unlock();
        }
    }

    @Override
    public int getDifficulty() {
        executionReadLock.lock();
        try {
            return getEntityObject().getDifficulty();
        } finally {
            executionReadLock.unlock();
        }
    }

    protected void incrementNumberOfGeneratedTasks() {
        numberOfGeneratedTasks.incrementAndGet();
    }

    protected void incrementNumberOfFinishedTasks() {
        numberOfFinishedTasks.incrementAndGet();
    }

    protected void addProcessingTime(int millis) {
        totalProcessingTime.addAndGet(millis);
    }

    protected void incrementProcessingCounter() {
        processingCounter.incrementAndGet();
    }

    protected void addWaitingTime(int millis) {
        totalWaitingTime.addAndGet(millis);
    }

    @Override
    public double getAvgProcessingTime() {
        if (processingCounter.get() > 0) {
            return totalProcessingTime.get() / processingCounter.get();
        } else {
            return 0;
        }
    }

    @Override
    public double getAvgWaitingTime() {
        if (numberOfGeneratedTasks.get() > 0) {
            return totalWaitingTime.get() / numberOfGeneratedTasks.get();
        } else {
            return 0;
        }
    }

    @Override
    public int getNumberOfGeneratedTasks() {
        return numberOfGeneratedTasks.get();
    }

    @Override
    public int getNumberOfFinishedTasks() {
        return numberOfFinishedTasks.get();
    }

}
