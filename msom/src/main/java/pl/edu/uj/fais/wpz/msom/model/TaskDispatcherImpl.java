/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Path;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;

/**
 *
 * @author jarep
 */
public class TaskDispatcherImpl extends ActivatableAbstractModelObject<ControllerUnit, ControllerUnitService> implements TaskDispatcher {

    private final SystemStorage systemStorage;
    // extra services (main service as a "service" in abstract class)
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;

    private final List<ProcessingUnit> processingUnits = new ArrayList<>();
    private final List<Path> comingOutPahts = new ArrayList<>();
    private final List<Type> knownTypes = new ArrayList<>();
    private final AtomicBoolean first = new AtomicBoolean(false);

    public TaskDispatcherImpl(ControllerUnit entityObject, SystemStorage systemStorage) {
        super(entityObject, systemStorage.getControllerUnitService());
        this.systemStorage = systemStorage;
        this.moduleService = systemStorage.getModuleService();
        this.pathService = systemStorage.getPathService();
        reloadProcessingUnits();
        reloadKnownTypes();
    }

    @Override
    protected boolean activateObject() {
        PrintHelper.printMsg(getName(), "Activating processing units");
        activateProcessingUnits();
        PrintHelper.printMsg(getName(), "Processing units activated");
        active.set(true);
        return true;
    }

    @Override
    protected boolean deactivateObject() {
        PrintHelper.printMsg(getName(), "Deactivating processing units");
        deactivateProcessingUnits();
        PrintHelper.printMsg(getName(), "Processing units deactivated");
        active.set(false);
        return true;
    }

    private boolean activateProcessingUnits() {
        for (ProcessingUnit p : processingUnits) {
            p.activate();
        }
        return true;
    }

    private boolean deactivateProcessingUnits() {
        for (ProcessingUnit p : processingUnits) {
            p.deactivate();
        }
        return true;
    }

    @Override
    protected void reloadData() {
        reloadEntityObcject();
        reloadProcessingUnits();
        reloadKnownTypes();
    }

    private void reloadProcessingUnits() {
        processingUnits.clear();
        if (getEntityObject() != null) {
            List<Module> modulesByControllerUnit = moduleService.getModulesByControllerUnit(getEntityObject());
            for (Module m : modulesByControllerUnit) {
                ProcessingUnitImpl p = new ProcessingUnitImpl(m, this, systemStorage);
                processingUnits.add(p);
            }
            System.out.println("Processing units loaded");
        } else {
            PrintHelper.printAlert(getName(), "Processing units not loaded - controller is null");
        }
    }

    private void reloadKnownTypes() {
        knownTypes.clear();
        if (getEntityObject() != null) {
            List<TaskType> knownTaskTypesByControllerUnit = pathService.getKnownTaskTypesByControllerUnit(getEntityObject());
            for (TaskType type : knownTaskTypesByControllerUnit) {
                TypeImpl typeObject = systemStorage.getTypeObject(type);
                if (typeObject != null) {
                    knownTypes.add(typeObject);
                } else {
                    typeObject = systemStorage.addAndGetExtraType(type);
                    knownTypes.add(typeObject);
                    PrintHelper.printAlert(toString(), "Added extra type: " + typeObject.getName());
                }
            }
            System.out.println("Loaded types for controller");
        } else {
            PrintHelper.printAlert(getName(), "Controller types not loaded - controller is null");
        }
    }

    /**
     * Thread-safe method to reload coming out paths.
     *
     * @return
     */
    protected boolean reloadComingOutPaths() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                comingOutPahts.clear();
                if (getEntityObject() != null) {
                    List<ProcessingPath> comingOutPaths = pathService.getProcessingPathsComingOutFromTheControllerUnit(getEntityObject());
                    for (ProcessingPath entity : comingOutPaths) {
                        TypeImpl typeObject = systemStorage.getTypeObject(entity.getTaskType());
                        TaskDispatcher forwardTo = systemStorage.getTaskDispatcherObject(entity.getNextControllerUnit());
                        PathImpl pathImpl = new PathImpl(entity, typeObject, forwardTo, pathService);
                        comingOutPahts.add(pathImpl);
                    }
                    PrintHelper.printMsg(getName(), "Reloading controller's paths");
                    return true;
                } else {
                    PrintHelper.printAlert(getName(), "Controller's paths not loaded - controller is null");
                    return false;
                }

            }
        }, executionWriteLock);
    }

    @Override
    protected boolean saveData() {
        saveEntityObject();
        saveProcessingUnits();
        return true;
    }

    private void saveProcessingUnits() {
        for (ProcessingUnit p : processingUnits) {
            p.save();
        }
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
    
    protected void markAsFirst(){
        first.set(true);
    }
    
    public boolean isFirst(){
        return first.get();
    }

    @Override
    public ProcessingUnit createProcessingUnit(String name, Integer cores, Integer Efficiency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addProcessingUnit(ProcessingUnit processingUnit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ProcessingUnit> getProcessingUnits() {
        executionReadLock.lock();
        try {
            return processingUnits;

        } finally {
            executionReadLock.unlock();
        }
    }

    @Override
    public void definePath(Type type, boolean processing, TaskDispatcher forwardTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAllPathFromThisTaskDispatcher() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean removeAllPathsLeadsToThisTaskDispatcher() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTypeToProcessing(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTypeToProcessing(Type type, boolean processing) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTypeToFinished(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setTypeToForwarding(Type type, TaskDispatcher forwardTo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validate() throws PathDefinitionExcpetion, ProcessingAbilityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Type> getTypesToProcessing() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Type> getTypesToFinished() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Type> getAllKnownTypes() {
        executionReadLock.lock();
        try {
            return knownTypes;
        } finally {
            executionReadLock.unlock();
        }
    }

    @Override
    public void returnTaskFromProcessingUnit(Task task) throws SystemIntegrityException {
        try {
            forwardTaskOrFinish(task);
        } catch (PathDefinitionExcpetion ex) {
            Logger.getLogger(TaskDispatcherImpl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PathDefinitionInfinityLoopExcpetion ex) {
            Logger.getLogger(TaskDispatcherImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void forwardTaskOrFinish(Task task, Path pathForTask) throws PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion {
        TaskDispatcher forwardTo = pathForTask.getNextTaskDispatcher();
        if (forwardTo == null || forwardTo.getId().equals(this.getId())) {
            PrintHelper.printMsg(getName(), "Task should be finished");
            task.finishTask();
        } else {
            PrintHelper.printMsg(getName(), "Task should be dispatched to: " + forwardTo.getName() + " ...");
            forwardTo.receiveTask(task);
        }
    }

    private void forwardTaskOrFinish(Task task) throws PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion {
        forwardTaskOrFinish(task, getPathForTask(task));
    }

    @Override
    public void receiveTask(Task task) throws PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion {
        PrintHelper.printMsg(getName(), "Received task " + ((TaskImpl) task).toString());
        Path pathForTask = getPathForTask(task);
        if (pathForTask != null) {
            if (pathForTask.isProcessing()) {
                try {
                    PrintHelper.printMsg(getName(), "Task should be processed");
                    ProcessingUnit unit = chooseProcessingUnit(task.getType());
                    if (unit != null) {
                        PrintHelper.printMsg(getName(), "Processing unit selected - processing task");
                        unit.processTask(task);
                    } else {
                        PrintHelper.printAlert(getName(), "Processing unit for task not found");
                        throw new ProcessingAbilityException();
                    }
                } catch (ProcessingAbilityException ex) {
                    Logger.getLogger(TaskDispatcherImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                forwardTaskOrFinish(task, pathForTask);
            }
        } else {
            PrintHelper.printAlert(getName(), "PATH FOR TASK NOT FOUND");
        }
    }

    private Path getPathForTask(Task task) {
        executionReadLock.lock();
        try {
            for (Path p : comingOutPahts) {
                if (p.getType().getId().equals(task.getType().getId())) {
                    return p;
                }
            }
            return null;
        } finally {
            executionReadLock.unlock();
        }
    }

    private ProcessingUnit chooseProcessingUnit(Type type) {
        executionReadLock.lock();
        try {
            PrintHelper.printMsg(getName(), "Selecting processing unit");
            int bestQueueValue = Integer.MAX_VALUE;
            ProcessingUnit processingUnitToReturn = null;
            for (ProcessingUnit unit : findProcessingUnitsForType(type)) {
                int currentQueueValue = unit.getQueueValue();
                if (currentQueueValue < bestQueueValue) {
                    bestQueueValue = currentQueueValue;
                    processingUnitToReturn = unit;
                }
            }
            return processingUnitToReturn;
        } finally {
            executionReadLock.unlock();
        }
    }

    private List<ProcessingUnit> findProcessingUnitsForType(Type type) {
        executionReadLock.lock();
        try {
            PrintHelper.printMsg(getName(), "Checking available processing units");
            List<ProcessingUnit> foundedProcessingUnits = new ArrayList<>();
            for (ProcessingUnit unit : processingUnits) {
                List<Type> availableTypes = unit.getAvailableTypes();
                for (Type t : availableTypes) {
                    if (t.getId().equals(type.getId())) {
                        foundedProcessingUnits.add(unit);
                        break;
                    }
                }
            }
            PrintHelper.printMsg(getName(), "Found " + foundedProcessingUnits.size() + " possible processing units.");
            return foundedProcessingUnits;
        } finally {
            executionReadLock.unlock();
        }

    }

    @Override
    public void definePath(Path path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Path> getComingOutPaths() {
        executionReadLock.lock();
        try {
            return comingOutPahts;
        } finally {
            executionReadLock.unlock();
        }
    }

}
