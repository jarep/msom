/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class ProcessingUnitImpl extends AbstractModelObject<Module> implements ProcessingUnit {

    private final ControllerUnitService controllerUnitService;
    private final ModuleService moduleService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private final AtomicBoolean active = new AtomicBoolean(false);
    private final List<Core> cores = new ArrayList<>();
    private final List<Thread> coreThreads = new ArrayList<>();
    private final BlockingQueue<Task> tasksBlockingQueue = new LinkedBlockingQueue<>();

    private final List<Type> availableTypes = new ArrayList<>();

    public ProcessingUnitImpl(Module entityObject, ControllerUnitService controllerUnitService, ModuleService moduleService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.moduleService = moduleService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        setEntityObject(entityObject);
    }

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public boolean activate() {
        PrintHelper.printMsg(getName(), "aktywuje sie...");
        active.set(true);
        activateCores();
        PrintHelper.printMsg(getName(), "jestem aktywny.");
        return true;
    }

    @Override
    public boolean deactivate() {
        active.set(false);
        deactivateCores();
        return true;
    }

    private void activateCores() {
        PrintHelper.printMsg(getName(), "aktywuje rdzenie...");
        for (int i = 0; i < getCores(); i++) {
            Core core = new Core(tasksBlockingQueue, this);
            cores.add(core);
            Thread coreThread = new Thread(core);
            coreThreads.add(coreThread);
            coreThread.setDaemon(true);
            coreThread.start();
        }
        PrintHelper.printMsg(getName(), "aktywowalem rdzenie.");
    }

    private void deactivateCores() {
        PrintHelper.printMsg(getName(), "deaktywuje rdzenie...");
        for (Thread thread : coreThreads) {
            thread.interrupt();
        }
        coreThreads.clear();
        cores.clear();
        PrintHelper.printMsg(getName(), "deaktywowalem rdzenie.");

    }

    @Override
    public boolean reload() {
        if (active.get()) {
            return false;
        } else {
            reloadEntityObcject();
            reloadAvailableTypes();
            return true;
        }
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            moduleService.refresh(getEntityObject());
        }
    }

    private void reloadAvailableTypes() {
        availableTypes.clear();
        if (getEntityObject() != null) {
            Set<TaskType> taskTypes = getEntityObject().getTaskTypes();
            for (TaskType t : taskTypes) {
                TypeImpl type = new TypeImpl(t, taskTypeService);
                availableTypes.add(type);
            }
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
//        saveTaskTypes();
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            moduleService.update(getEntityObject());
        }
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return getEntityObject().getName();
    }

    @Override
    public void setEfficiency(Integer efficiency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getEfficiency() {
        return getEntityObject().getEfficiency();
    }

    @Override
    public void setCores(Integer cores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getCores() {
        return getEntityObject().getCores();
    }

    @Override
    public void addType(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeType(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Type> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTask(Task task) throws ProcessingAbilityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getQueueLength() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getQueueValue() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getWaitingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getProcessingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfProcessingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getPercentOfExecutionCurrentTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAvgWaitingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAvgProcessingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
