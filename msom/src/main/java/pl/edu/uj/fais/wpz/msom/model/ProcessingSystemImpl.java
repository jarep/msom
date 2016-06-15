/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingSystem;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.DistributionService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class ProcessingSystemImpl extends ActivatableAbstractModelObject<Model, ModelService> implements ProcessingSystem {

    private final SystemStorage systemStorage;

    // extra services (main service as a "service" in abstract class)
    private final ControllerUnitService controllerUnitService;
    private final DistributionService distributionService;
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private TaskGeneratorImpl taskGenerator;

    public ProcessingSystemImpl(Model entityObject, ControllerUnitService controllerUnitService, DistributionService distributionService, ModelService modelService, ModuleService moduleService, ProcessingPathService pathService, TaskService taskService, TaskTypeService taskTypeService) {
        super(entityObject, modelService);
        this.controllerUnitService = controllerUnitService;
        this.distributionService = distributionService;
        this.moduleService = moduleService;
        this.pathService = pathService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        this.systemStorage = new SystemStorage(controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
        initializeSystemStorage();
    }

    private void initializeSystemStorage() {
        reloadTypes(); // types are reloaded before reloaded task dispatchers, because we need type objects for task dispatchers
        reloadTaskDispatchers();
        reloadPaths(); // paths are reloaded after reloaded task dispatchers, because we need all task dispatchers to create connection between them
    }

    @Override
    protected void reloadData() {
        reloadEntityObcject();
        reloadTypes(); // types are reloaded before reloaded task dispatchers, because we need type objects for task dispatchers
        reloadTaskDispatchers();
        reloadPaths(); // paths are reloaded after reloaded task dispatchers, because we need all task dispatchers to create connection between them
    }

    private void reloadTaskDispatchers() {
        systemStorage.cleanTasksDispatchers();
        if (getEntityObject() != null) {
            List<ControllerUnit> controllersByModel = controllerUnitService.getControllersByModel(getEntityObject());
            ControllerUnit firstControllerUnit = getEntityObject().getFirstControllerUnit();
            for (ControllerUnit controllerUnit : controllersByModel) {
                TaskDispatcherImpl td = new TaskDispatcherImpl(controllerUnit, systemStorage);
                systemStorage.addTaskDispatcher(td);
                if (firstControllerUnit != null && firstControllerUnit.getId().equals(td.getId())) {
                    td.markAsFirst();
                    systemStorage.setFirstTaskDispatcher(td);
                }
            }
        }
        if (getFirstTaskDispatcher() == null) {
            PrintHelper.printAlert(getName(), "Nie wskazano pierwszego kontrolera!");
        }
    }

    /**
     * Reload all types available in this processing system - used by any
     * processing unit or any processing path
     */
    private void reloadTypes() {
        systemStorage.cleanTypes();
        if (getEntityObject() != null) {
            List<TaskType> allTaskTypes = taskTypeService.findAll(); // UWAGA - powinny byc tylko te, ktore sa powiazane z tym modelem!!! 
            for (TaskType taskType : allTaskTypes) {
                TypeImpl type = new TypeImpl(taskType, taskTypeService);
                systemStorage.addType(type);
            }
        }
    }

    private void reloadPaths() {
        List<TaskDispatcher> taskDispatchers = systemStorage.getTaskDispatchers();
        for (TaskDispatcher dispatcher : taskDispatchers) {
            ((TaskDispatcherImpl) dispatcher).reloadComingOutPaths();
        }
    }

    @Override
    protected boolean saveData() {
        saveEntityObject();
        saveTaskDispatchers();
        return true;
    }

    private void saveTaskDispatchers() {
        for (TaskDispatcher td : systemStorage.getTaskDispatchers()) {
            td.save();
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

    @Override
    public boolean startSimulation() {
        PrintHelper.printMsg(getName(), "Rozpoczynam symulacje ...");
        boolean result = activate();
        PrintHelper.printMsg(getName(), "Symulacja rozpoczeta.");
        return result;
    }

    @Override
    protected boolean activateObject() {
        active.set(true);
        activateTaskDispatchers();
        activateGenerator();
        return true;

    }

    private void activateTaskDispatchers() {
        PrintHelper.printMsg(getName(), "Aktywuje task dispatchers ...");
        for (TaskDispatcher td : systemStorage.getTaskDispatchers()) {
            td.activate();
        }
        PrintHelper.printMsg(getName(), "Aktywowalem task dispatchers.");
    }

    private void activateGenerator() {
        PrintHelper.printMsg(getName(), "Aktywuje generator ...");
        taskGenerator = new TaskGeneratorImpl(systemStorage, taskService);
        taskGenerator.activate();
        PrintHelper.printMsg(getName(), "Aktywowalem generator.");
    }

    @Override
    public boolean stopSimulation() {
        PrintHelper.printMsg(getName(), "Zatrzymuje symulacje ...");
        boolean result = deactivate();
        PrintHelper.printMsg(getName(), "Symulacja zatrzymana.");
        return result;
    }

    @Override
    protected boolean deactivateObject() {
        active.set(false);
        deactivateGenerator();
        deactivateTaskDispatchers();
        return true;
    }

    private void deactivateGenerator() {
        if (taskGenerator != null) {
            taskGenerator.deactivate();
        }
        taskGenerator = null;
    }

    private void deactivateTaskDispatchers() {
        for (TaskDispatcher td : systemStorage.getTaskDispatchers()) {
            td.deactivate();
        }
    }

    public boolean cleanSimulationData() {
        return systemStorage.clean();
    }

    @Override
    public TaskDispatcher createTaskDispatcher(String name) {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
            return null;
        }
        executionWriteLock.lock();
        try {
            if (active.get()) {
                PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
                return null;
            } else {
                return createAndAddTaskDispatcher(name);
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    private TaskDispatcher createAndAddTaskDispatcher(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
            return false;
        }
        executionWriteLock.lock();
        try {
            if (active.get()) {
                PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
                return false;
            } else {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    @Override
    public List<TaskDispatcher> getTaskDispatchers() {
        return systemStorage.getTaskDispatchers();
    }

    @Override
    public boolean setFirstTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }, executionWriteLock);
    }

    @Override
    public TaskDispatcher getFirstTaskDispatcher() {
        return systemStorage.getFirstTaskDispatcher();
    }

    @Override
    public boolean validate() throws SystemIntegrityException, ProcessingAbilityException, PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion {
        if(systemStorage.getFirstTaskDispatcher()==null){
            throw new SystemIntegrityException("Nie zdefiniowano pierwszego kontrolera.");
        } 
        // required other validations ...
        return true;
    }

    @Override
    public boolean setDistributionType(DistributionType distributionType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DistributionType getDistributionType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isLocked() {
        return active.get();
    }
}
