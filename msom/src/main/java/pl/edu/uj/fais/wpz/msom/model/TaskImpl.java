/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
public class TaskImpl extends AbstractModelObject<pl.edu.uj.fais.wpz.msom.entities.Task> implements Task {

    private final TaskService taskService;
    private final AtomicBoolean active = new AtomicBoolean(false);
    private Type type;

    public TaskImpl(pl.edu.uj.fais.wpz.msom.entities.Task entityObject, TaskService TaskService) {
        this.taskService = TaskService;
        setEntityObject(entityObject);
    }

    @Override
    public boolean reload() {
        if (active.get()) {
            return false;
        } else {
            reloadEntityObcject();
            return true;
        }
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            taskService.refresh(getEntityObject());
        }
    }

    @Override
    public boolean save() {
        if (active.get()) {
            return false;
        } else {
            saveEntityObject();
            return true;
        }
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            taskService.update(getEntityObject());
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    public boolean setType(TypeImpl type) {
        if (active.get()) {
            return false;
        } else if (type == null || getEntityObject() == null) {
            return false;
        } else {
            this.type = type;
            getEntityObject().setTaskType(type.getEntityObject());
            return true;
        }
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
        try {
            PrintHelper.printMsg(getName(), "ktos mnie bedrze przetwarzal...");
            int millis = 1000 * type.getDifficulty();
            Thread.sleep(millis);
            PrintHelper.printMsg(getName(), "wykonano mnie.");
            return true;
        } catch (InterruptedException ex) {
            PrintHelper.printAlert(getName(), "Przerwano w trakcie przetwarzania. (interrupted exception)");
        }
        return false;
    }

    @Override
    public void finishTask() {
        PrintHelper.printMsg(getName(), "zakonczono mnie...");
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
    public ProcessingUnitImpl getLastProcessingUnit() {
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

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public boolean activate() {
        active.set(true); // do rozbudowania
        return true;
    }

    @Override
    public boolean deactivate() {
        active.set(false); // do rozbudowania
        return true;
    }

    @Override
    public String toString() {
        return "TaskImpl{" + "name=" + getName() + ", active=" + active + ", type=" + type.getName() + '}';
    }

}
