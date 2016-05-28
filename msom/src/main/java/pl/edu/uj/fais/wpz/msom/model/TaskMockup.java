/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
public class TaskMockup extends AbstractModelObject<pl.edu.uj.fais.wpz.msom.entities.Task> implements Task {
    
    private final TaskService taskService;

    public TaskMockup(pl.edu.uj.fais.wpz.msom.entities.Task entityObject, TaskService TaskService) {
        this.taskService = TaskService;
        setEntityObject(entityObject);
    }
    
    @Override
    public void reload() {
        reloadEntityObcject();
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            taskService.refresh(getEntityObject());
        }
    }

    @Override
    public void save() {
        saveEntityObject();
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            taskService.update(getEntityObject());
        }
    }

    @Override
    public Type getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return getEntityObject().getName();
    }

    @Override
    public Integer getDifficulty() {
        return getEntityObject().getTaskType().getDifficulty();
    }

    @Override
    public boolean processTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void finishTask() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFinished() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getProcessingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getWaitingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ProcessingUnit getLastProcessingUnit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}
