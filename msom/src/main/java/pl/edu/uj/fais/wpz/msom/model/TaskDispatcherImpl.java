/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
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
public class TaskDispatcherImpl extends AbstractModelObject<ControllerUnit> implements TaskDispatcher {

    private final SystemStorage systemStorage;
    // services
    private final ControllerUnitService controllerUnitService;
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;

    private final AtomicBoolean active = new AtomicBoolean(false);
    private final List<ProcessingUnit> processingUnits = new ArrayList<>();
    private final List<Path> comingOutPahts = new ArrayList<>();

    public TaskDispatcherImpl(ControllerUnit entityObject, SystemStorage systemStorage) {
        this.systemStorage = systemStorage;
        this.controllerUnitService = systemStorage.getControllerUnitService();
        this.moduleService = systemStorage.getModuleService();
        this.pathService = systemStorage.getPathService();
        setEntityObject(entityObject);
    }

    @Override
    public boolean activate() {
        PrintHelper.printMsg(getName(), "aktywuje sie...");
        active.set(true);
        activateProcessingUnits();
        PrintHelper.printMsg(getName(), "jestem aktywny.");
        return true;
    }

    @Override
    public boolean deactivate() {
        deactivateProcessingUnits();
        active.set(false);
        return false;
    }

    private boolean activateProcessingUnits() {
        PrintHelper.printMsg(getName(), "aktywuje processing units...");
        for (ProcessingUnit p : processingUnits) {
            p.activate();
        }
        PrintHelper.printMsg(getName(), "aktywowalem processing units.");
        return true;
    }

    private boolean deactivateProcessingUnits() {
        for (ProcessingUnit p : processingUnits) {
            p.deactivate();
        }
        return true;
    }

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public boolean reload() {
        if (active.get()) {
            return false;
        } else {
            reloadEntityObcject();
            reloadProcessingUnits();
            return true;
        }
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            controllerUnitService.refresh(getEntityObject());
        }
    }

    private void reloadProcessingUnits() {
        processingUnits.clear();
        if (getEntityObject() != null) {
            List<Module> modulesByControllerUnit = moduleService.getModulesByControllerUnit(getEntityObject());
            for (Module m : modulesByControllerUnit) {
                ProcessingUnitImpl p = new ProcessingUnitImpl(m, this, systemStorage);
                processingUnits.add(p);
            }
        }
    }

    protected boolean reloadComingOutPaths() {
        if (active.get()) {
            return false;
        } else {
            comingOutPahts.clear();
            if (getEntityObject() != null) {
                List<ProcessingPath> comingOutPaths = pathService.getProcessingPathsComingOutFromTheControllerUnit(getEntityObject());
                for (ProcessingPath entity : comingOutPaths) {
                    TypeImpl typeObject = systemStorage.getTypeObject(entity.getTaskType());
                    TaskDispatcher forwardTo = systemStorage.getTaskDispatcherObject(entity.getNextControllerUnit());
                    PathImpl pathImpl = new PathImpl(entity, typeObject, forwardTo, pathService);
                    comingOutPahts.add(pathImpl);
                }
            }
            return true;
        }

    }

    @Override
    public boolean save() {
        if (active.get()) {
            return false;
        } else {
            saveEntityObject();
            saveProcessingUnits();
            return true;
        }
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            controllerUnitService.update(getEntityObject());
        }
    }

    private void saveProcessingUnits() {
        for (ProcessingUnit p : processingUnits) {
            p.save();
        }
    }

    @Override
    public void setName(String name) {
        getEntityObject().setName(name);
    }

    @Override
    public String getName() {
        return getEntityObject().getName();
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
        return processingUnits;

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
    public List<Type> getAllSupportedTypes() {
        List<TaskType> supportedTaskTypesByControllerUnit = pathService.getSupportedTaskTypesByControllerUnit(getEntityObject());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            PrintHelper.printMsg(getName(), "Zadanie powinno byc zakonczone...");
            task.finishTask();
        } else {
            PrintHelper.printMsg(getName(), "ZADANIE POWINNO BYC PRZEKAZANE DO: " + forwardTo.getName() + " ...");
            forwardTo.receiveTask(task);
        }
    }

    private void forwardTaskOrFinish(Task task) throws PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion {
        forwardTaskOrFinish(task, getPathForTask(task));
    }

    @Override
    public void receiveTask(Task task) throws PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion {
        PrintHelper.printMsg(getName(), "Otrzymalem zadanie");
        Path pathForTask = getPathForTask(task);
        if (pathForTask != null) {
            if (pathForTask.isProcessing()) {
                try {
                    PrintHelper.printMsg(getName(), "Zadanie powinno byc przetworzone...");
                    chooseProcessingUnit().processTask(task);
                    /// trzeba je potem spowrotem odebrac...
                } catch (ProcessingAbilityException ex) {
                    Logger.getLogger(TaskDispatcherImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                forwardTaskOrFinish(task, pathForTask);
            }
        } else {
            PrintHelper.printAlert(getName(), "NIE ZNALEZIONO SCIEZKI DLA ZADANIA");
        }
    }

    private Path getPathForTask(Task task) {
        for (Path p : comingOutPahts) {
            if (p.getType().getId().equals(task.getType().getId())) {
                return p;
            }
        }
        return null;
    }

    private ProcessingUnitImpl chooseProcessingUnit() {
        Random r = new Random();
        int nextInt = r.nextInt(processingUnits.size());
        return (ProcessingUnitImpl) processingUnits.get(nextInt);
    }

    @Override
    public void definePath(Path path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Path> getComingOutPaths() {
        return comingOutPahts;
    }

}
