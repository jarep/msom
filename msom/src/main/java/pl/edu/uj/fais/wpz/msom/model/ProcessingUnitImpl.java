/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class ProcessingUnitImpl extends ActivatableAbstractModelObject<Module, ModuleService> implements ProcessingUnit {

    private final SystemStorage systemStorage;
    // extra services (main service as a "service" in abstract class)
    private final TaskTypeService taskTypeService;

    private final List<Core> cores = new ArrayList<>();
    private final List<Thread> coreThreads = new ArrayList<>();
    private final BlockingQueue<Task> tasksBlockingQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Task> processingTasksBlockingQueue = new LinkedBlockingQueue<>();
    private final AtomicInteger queueValue = new AtomicInteger();

    private final TaskDispatcherImpl taskDispatcher;

    private final List<Type> availableTypes = new ArrayList<>();

    public ProcessingUnitImpl(Module entityObject, TaskDispatcherImpl taskDispatcher, SystemStorage systemStorage) {
        super(entityObject, systemStorage.getModuleService());
        this.systemStorage = systemStorage;
        this.taskTypeService = systemStorage.getTaskTypeService();
        this.taskDispatcher = taskDispatcher;
        reloadAvailableTypes();
    }

    @Override
    public boolean activateObject() {
        active.set(true);
        activateCores();
        return true;
    }

    @Override
    public boolean deactivateObject() {
        active.set(false);
        deactivateCores();
        return true;
    }

    private void activateCores() {
        PrintHelper.printMsg(toString(), "aktywuje rdzenie...");
        for (int i = 0; i < getNumberOfCores(); i++) {
            Core core = new Core(tasksBlockingQueue, processingTasksBlockingQueue, this, queueValue, i);
            cores.add(core);
            Thread coreThread = new Thread(core);
            coreThreads.add(coreThread);
            coreThread.setDaemon(true);
            coreThread.start();
        }
        PrintHelper.printMsg(toString(), "aktywowalem rdzenie.");
    }

    private void deactivateCores() {
        PrintHelper.printMsg(toString(), "deaktywuje rdzenie...");
        for (Thread thread : coreThreads) {
            thread.interrupt();
        }
        coreThreads.clear();
        cores.clear();
        PrintHelper.printMsg(toString(), "deaktywowalem rdzenie.");

    }

    @Override
    public void reloadData() {
        reloadEntityObcject();
        reloadAvailableTypes();
    }

    // should be common objects (from SystemStorage)
    private void reloadAvailableTypes() {
        PrintHelper.printMsg(toString(), "Przeladowywanie typow...");
        availableTypes.clear();
        if (getEntityObject() != null) {
            Set<TaskType> taskTypes = getEntityObject().getTaskTypes();
            for (TaskType t : taskTypes) {
                TypeImpl type = systemStorage.getTypeObject(t);
                if (type != null) {
                    availableTypes.add(type);
                    PrintHelper.printMsg(toString(), "Dodano typ: " + type.getName());
                } else {
                    TypeImpl newType = systemStorage.addAndGetExtraType(t);
                    availableTypes.add(newType);
                    PrintHelper.printAlert(toString(), "Dodano extra typ: " + newType.getName());
                }
            }
        }
        PrintHelper.printMsg(toString(), "Przeladowano liste typow.");
    }

    @Override
    public void setName(String name) {
        executionWriteLock.lock();
        try {
            getEntityObject().setName(name);
        } finally {
            executionWriteLock.unlock();
        }
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
    public String toString() {
        return taskDispatcher.getName() + " - " + getEntityObject().getName();
    }

    @Override
    public void setEfficiency(Integer efficiency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getEfficiency() {
        executionReadLock.lock();
        try {
            return getEntityObject().getEfficiency();
        } finally {
            executionReadLock.unlock();
        }
    }

    @Override
    public void setCores(Integer cores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getNumberOfCores() {
        executionReadLock.lock();
        try {
            return getEntityObject().getCores();
        } finally {
            executionReadLock.unlock();
        }
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
        executionReadLock.lock();
        try {
            return availableTypes;
        } finally {
            executionReadLock.unlock();
        }
    }

    @Override
    public void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTask(Task task) throws ProcessingAbilityException {
        PrintHelper.printMsg(toString(), "Odebralem zadanie - przekazuje do kolejki.");
        queueValue.addAndGet(task.getDifficulty());
        tasksBlockingQueue.add(task);
    }

    @Override
    public int getQueueLength() {
        return tasksBlockingQueue.size();
    }

    @Override
    public int getQueueValue() {
        return queueValue.get();
    }

    @Override
    public List<Task> getWaitingTasks() {
        executionWriteLock.lock();
        try {
            List<Task> tasks = new ArrayList<>();
            tasks.addAll(tasksBlockingQueue);
            return tasks;
        } finally {
            executionWriteLock.unlock();
        }
    }

    @Override
    public List<Task> getProcessingTasks() {
        executionWriteLock.lock();
        try {
            List<Task> tasks = new ArrayList<>();
            tasks.addAll(processingTasksBlockingQueue);
            return tasks;
        } finally {
            executionWriteLock.unlock();
        }
    }

    @Override
    public int getNumberOfProcessingTasks() {
        return processingTasksBlockingQueue.size();
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

    /**
     * Return task from core.
     *
     * @param task
     */
    protected void returnTask(Task task) {
        executionWriteLock.lock();
        try {
            processingTasksBlockingQueue.remove(task);
            taskDispatcher.returnTaskFromProcessingUnit(task);
        } catch (SystemIntegrityException ex) {
            Logger.getLogger(ProcessingUnitImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            executionWriteLock.unlock();
        }
    }

}
