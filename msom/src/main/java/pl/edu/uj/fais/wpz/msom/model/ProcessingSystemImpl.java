/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskProbability;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Path;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingSystem;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.DistributionService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskProbabilityService;
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
    private final TaskProbabilityService taskProbabilityService;

    private TaskGeneratorImpl taskGenerator;

    public ProcessingSystemImpl(Model entityObject, ControllerUnitService controllerUnitService, DistributionService distributionService, ModelService modelService, ModuleService moduleService, ProcessingPathService pathService, TaskService taskService,TaskProbabilityService taskProbabilityService, TaskTypeService taskTypeService) {
        super(entityObject, modelService);
        this.controllerUnitService = controllerUnitService;
        this.distributionService = distributionService;
        this.moduleService = moduleService;
        this.pathService = pathService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        this.taskProbabilityService = taskProbabilityService;
        this.systemStorage = new SystemStorage(entityObject.getId(), controllerUnitService, distributionService, modelService, moduleService, pathService, taskService,taskProbabilityService, taskTypeService);
        initializeSystemStorage();
    }

    private void initializeSystemStorage() {
        reloadTypes(); // types are reloaded before reloaded task dispatchers, because we need type objects for task dispatchers
        reloadTaskDispatchers();
        reloadPaths(); // paths are reloaded after reloaded task dispatchers, because we need all task dispatchers to create connection between them
        reloadTaskEntityWrappers();
    }

    @Override
    protected void reloadData() {
        reloadEntityObcject();
        reloadTypes(); // types are reloaded before reloaded task dispatchers, because we need type objects for task dispatchers
        reloadTaskDispatchers();
        reloadPaths(); // paths are reloaded after reloaded task dispatchers, because we need all task dispatchers to create connection between them
        reloadTaskEntityWrappers();
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
            PrintHelper.printAlert(getName(), "First controller not selected");
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
            ((TaskDispatcherImpl) dispatcher).reloadPaths();
        }
    }

    private void reloadTaskEntityWrappers() {
        systemStorage.cleanTaskEntityWrappers();
        if (getEntityObject() != null) {
            PrintHelper.printMsg(getName(), "Initiallizing task list");
            List<TaskProbability> taskToGen = taskProbabilityService.getTaskProbabilitiesByModel(getEntityObject());
            for (TaskProbability taskPropabilyty : taskToGen) {
                PrintHelper.printMsg(getName(), "Task: " + taskPropabilyty.getTask().getName()+ "...");
                TaskEntityWrapper entityWrapper = new TaskEntityWrapper(taskPropabilyty.getTask(), taskPropabilyty.getTask().getTaskType().getId(), taskPropabilyty.getDistribution());
                systemStorage.addTaskEntityWrapper(entityWrapper);
            }
            PrintHelper.printMsg(getName(), "Task list ready - saved " + taskToGen.size() + " tasks");
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
        PrintHelper.printMsg(getName(), "Starting simulation");
        boolean result = activate();
        PrintHelper.printMsg(getName(), "Simulation started");
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
        PrintHelper.printMsg(getName(), "Activating task dispatchers");
        for (TaskDispatcher td : systemStorage.getTaskDispatchers()) {
            td.activate();
        }
        PrintHelper.printMsg(getName(), "Task dispatchers activated");
    }

    private void activateGenerator() {
        PrintHelper.printMsg(getName(), "Activating task generator");
        taskGenerator = new TaskGeneratorImpl(systemStorage);
        taskGenerator.activate();
        PrintHelper.printMsg(getName(), "Task generator activated");
    }

    @Override
    public boolean stopSimulation() {
        PrintHelper.printMsg(getName(), "Stopping simulation");
        boolean result = deactivate();
        PrintHelper.printMsg(getName(), "Simulation stopped");
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
            PrintHelper.printAlert(getName(), "Simulation is in progress - cannot modify system structure");
            return null;
        }
        executionWriteLock.lock();
        try {
            if (active.get()) {
                PrintHelper.printAlert(getName(), "Simulation is in progress - cannot modify system structure");
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
            PrintHelper.printAlert(getName(), "Simulation is in progress - cannot modify system structure");
            return false;
        }
        executionWriteLock.lock();
        try {
            if (active.get()) {
                PrintHelper.printAlert(getName(), "Simulation is in progress - cannot modify system structure");
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
    public boolean validate() throws SystemIntegrityException, ProcessingAbilityException, PathDefinitionException, PathDefinitionInfinityLoopExcpetion {
        executionReadLock.lock();
        try {
            PrintHelper.startSection("VALIDATION");
            validateFirstControllerUnit();
            validateProcessingPaths();
            validateGeneratedTasks();
            validateInfinityLoop();
            PrintHelper.endSection("VALIDATION");
            return true;
        } finally {
            executionReadLock.unlock();
        }
    }

    private void validateFirstControllerUnit() throws SystemIntegrityException {
        PrintHelper.startSubSection("validation first controller unit");
        if (systemStorage.getFirstTaskDispatcher() == null) {
            throw new SystemIntegrityException("First controller not selected.");
        }
        PrintHelper.endSubSection("validation first controller unit");
    }

    private void validateProcessingPaths() throws ProcessingAbilityException, PathDefinitionException, PathDefinitionInfinityLoopExcpetion, SystemIntegrityException {
        PrintHelper.startSubSection("validation processing paths");
        List<TaskDispatcher> taskDispatchers = getTaskDispatchers();
        for (TaskDispatcher taskDispatcher : taskDispatchers) {
            List<Path> leadingToPaths = taskDispatcher.getLeadingToPaths();
            for (Path path : leadingToPaths) {
                validateProcessingAbility(path); // throws ProcessingAbilityException
                Type type = path.getType();
                isKnownType(type.getId(), type.getName(), taskDispatcher);
                validatePathIntegrity(path);
            }
        }
        PrintHelper.endSubSection("validation processing paths");
    }

    /**
     * Check that all task dispatchers can process tasks which should be
     * processed.
     *
     * @param path
     * @throws ProcessingAbilityException
     */
    private void validateProcessingAbility(Path path) throws ProcessingAbilityException {
        if (path.isProcessing()) {
            TaskDispatcher taskDispatcher = path.getPreviousTaskDispatcher();
            List<ProcessingUnit> processingUnits = taskDispatcher.findProcessingUnitsForType(path.getType());
            if (processingUnits.isEmpty()) {
                throw new ProcessingAbilityException("TaskDispatcher <strong>" + taskDispatcher.getName() + "</strong> can not process tasks of type <strong>" + path.getType().getName() + "</strong>");
            }
        }
    }

    /**
     * Check that first task dispatcher has defined paths for all tasks received
     * from task generator.
     *
     * @throws SystemIntegrityException
     */
    private void validateGeneratedTasks() throws SystemIntegrityException {
        PrintHelper.startSubSection("validation generated tasks");
        List<TaskEntityWrapper> taskEntityWrappers = systemStorage.getTaskEntityWrappers();
        for (TaskEntityWrapper taskEntityWrapper : taskEntityWrappers) {
            try {
                TaskType taskType = taskEntityWrapper.getTaskEntity().getTaskType();
                isKnownType(taskType.getId(), taskType.getName(), getFirstTaskDispatcher());
            } catch (PathDefinitionException ex) {
                throw new SystemIntegrityException("Task <strong>" + taskEntityWrapper.getTaskEntity().getName() + "</strong>"
                        + " of type <strong>" + taskEntityWrapper.getTaskEntity().getTaskType().getName() + "</strong>"
                        + " is not supported by first task dispatcher.");
            }
        }
        PrintHelper.endSubSection("validation generated tasks");
    }

    /**
     * Check that given task type is supported by given task dispatcher
     *
     * @param taskType
     * @param taskDispatcher
     * @return
     * @throws PathDefinitionException
     */
    private boolean isKnownType(Long taskTypeId, String taskTypeName, TaskDispatcher taskDispatcher) throws PathDefinitionException {
        List<Type> allKnownTypes = taskDispatcher.getAllKnownTypes();
        for (Type t : allKnownTypes) {
            if (t.getId().equals(taskTypeId)) {
                return true;
            }
        }
        throw new PathDefinitionException("Type of task <strong>" + taskTypeName + "</strong>"
                + " is not supported by "
                + " <strong>" + taskDispatcher.getName() + "</strong>");
    }

    private void validatePathIntegrity(Path path) throws SystemIntegrityException {
        TaskDispatcher previousTaskDispatcher = path.getPreviousTaskDispatcher();
        TaskDispatcher nextTaskDispatcher = path.getNextTaskDispatcher();
        if (!previousTaskDispatcher.getModelId().equals(nextTaskDispatcher.getModelId())) {
            throw new SystemIntegrityException("Path " + path + " is incorrect.");
        }
    }

    private void validateInfinityLoop() {
        // TO DO
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

    @Override
    public List<Type> getAllTypes() {
        return systemStorage.getAllTypes();
    }

}
