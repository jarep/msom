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
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class TaskImpl extends AbstractModelObject<pl.edu.uj.fais.wpz.msom.entities.Task> implements Task {
    
    public enum State {IDLE, QUEUED, IN_PROCESS, FINISHED}
    
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;
    
    // Task state info parameters
    private State currentState;
    private long timeInQueue;
    private long timeOfProcessing;

    public TaskImpl(pl.edu.uj.fais.wpz.msom.entities.Task entityObject, TaskService taskService, TaskTypeService taskTypeService) {
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        setEntityObject(entityObject);
    }
    
    @Override
    public void reload() {
        reloadEntityObcject();
        currentState = State.IDLE;
        timeInQueue = 0;
        timeOfProcessing = 0;
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            taskService.refresh(getEntityObject());
        }
    }
    
    private void changeState(State newState){
        this.currentState = newState;
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
        if (getEntityObject() != null) {
          return new TypeMockup(getEntityObject().getTaskType(), taskTypeService);
        } else return null;
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
    public void queueTask() {
        if (currentState == State.IDLE) {
            changeState(State.QUEUED);
            this.timeInQueue = System.currentTimeMillis();
        } else throw new IllegalStateException("Cannot queue task which is in " + currentState + " state");
    }

    
    
    @Override
    public boolean processTask() {
        if (currentState == State.QUEUED) {
            // pop from queue
            changeState(State.IN_PROCESS);
            // save queue time
            this.timeInQueue = System.currentTimeMillis() - this.timeInQueue;
            // start processing timer
            this.timeOfProcessing =  System.currentTimeMillis();
            return true;
        } else throw new IllegalStateException("Cannot process task which is in " + currentState + " state");
    }

    @Override
    public void finishTask() {
        if (currentState == State.IN_PROCESS) {
            changeState(State.FINISHED);
            this.timeOfProcessing = System.currentTimeMillis() - this.timeOfProcessing;
        } else throw new IllegalStateException("Cannot finish task which is in " + currentState + " state on object " + this.hashCode());
    }

    @Override
    public boolean isFinished() {
        return this.currentState == State.FINISHED;
    }

    @Override
    public Integer getProcessingTime() {
        if (currentState == State.FINISHED) {
            return (int) this.timeOfProcessing;
        } else return 0;
    }

    @Override
    public Integer getWaitingTime() {
        if (currentState == State.FINISHED || currentState == State.IN_PROCESS) {
            return (int) this.timeInQueue;
        } else return 0;
    }

    @Override
    public ProcessingUnit getLastProcessingUnit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getExecutionCounter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getPercentageOfCurrentExecution() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
}

