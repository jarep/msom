/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class TypeImpl extends ActivatableAbstractModelObject<TaskType, TaskTypeService> implements Type {

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
    
    @Override
    public double getAvgProcessingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getAvgWaitingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfGeneratedTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfFinishedTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
