/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
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
public class SystemStorage extends Activatable {

    // services
    private final ControllerUnitService controllerUnitService;
    private final DistributionService distributionService;
    private final ModelService modelService;
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private final List<TaskDispatcher> taskDispatchers = new ArrayList<>();
    private TaskDispatcherImpl firstTaskDispatcher = null;
    /**
     * All types available in this processing system - used by any processing
     * unit or any processing path.
     */
    private final List<TypeImpl> allTypes = new ArrayList<>();

    /**
     * All generated tasks.
     */
    private final BlockingQueue<TaskImpl> tasksBlockingQueue = new LinkedBlockingQueue<>();
    private final DistributionType distributionType = DistributionType.UNKNOWN;

    protected SystemStorage(ControllerUnitService controllerUnitService, DistributionService distributionService, ModelService modelService, ModuleService moduleService, ProcessingPathService pathService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.distributionService = distributionService;
        this.modelService = modelService;
        this.moduleService = moduleService;
        this.pathService = pathService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
    }

    public ControllerUnitService getControllerUnitService() {
        return controllerUnitService;
    }

    public DistributionService getDistributionService() {
        return distributionService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    public ModuleService getModuleService() {
        return moduleService;
    }

    public ProcessingPathService getPathService() {
        return pathService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public TaskTypeService getTaskTypeService() {
        return taskTypeService;
    }

    public boolean clean() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Czyszczenie danych symulacji...");
                cleanTasks();
                cleanTasksDispatchers();
                cleanTypes();
                PrintHelper.printMsg(getName(), "Wyczyszczono dane symulacji.");
                return true;
            }
        }, executionWriteLock);
    }

    protected void cleanTasks() {
        executionWriteLock.lock();
        try {
            tasksBlockingQueue.clear();
        } finally {
            executionWriteLock.unlock();
        }
    }

    protected boolean cleanTypes() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Czyszczenie listy typow...");
                allTypes.clear();
                PrintHelper.printMsg(getName(), "Wyczyszczono liste typow.");
                return true;
            }
        }, executionWriteLock);
    }

    protected boolean addType(final TypeImpl type) {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Dodawanie typu...");
                allTypes.add(type);
                PrintHelper.printMsg(getName(), "Dodano typ: " + type.getName());
                return true;
            }
        }, executionWriteLock);
    }

    /**
     * Get Type object associated with given taskType by typeId (entity object)
     * if this type is available in this processing system.
     *
     * @param typeId
     * @return Type or null if this type is not available
     */
    public TypeImpl getTypeObject(Long typeId) {
        executionReadLock.lock();
        try {
            for (TypeImpl t : allTypes) {
                if (t.getId().equals(typeId)) {
                    return t;
                }
            }
            return null;
        } finally {
            executionReadLock.unlock();
        }
    }

    /**
     * Get Type object associated with given taskType (entity object) if this
     * type is available in this processing system.
     *
     * @param taskType
     * @return Type or null if this type is not available
     */
    public TypeImpl getTypeObject(TaskType taskType) {
        return getTypeObject(taskType.getId());
    }

    /**
     * Get Type object associated with given taskType (entity object). Add task
     * type if is not yet available in this processing system and return.
     *
     * @param taskType
     * @return Type object
     */
    protected TypeImpl addAndGetExtraType(TaskType taskType) {
        executionWriteLock.lock();
        try {
            for (TypeImpl type : allTypes) {
                if (type.getId().equals(taskType.getId())) {
                    return type;
                }
            }
            TypeImpl newType = new TypeImpl(taskType, taskTypeService);
            allTypes.add(newType);
            return newType;
        } finally {
            executionWriteLock.unlock();
        }
    }

    /**
     * Get all types available in this processing system - used by any
     * processing unit or any processing path.
     *
     * @return List of types
     */
    public List<TypeImpl> getAllTypes() {
        executionReadLock.lock();
        try {
            return allTypes;
        } finally {
            executionReadLock.unlock();
        }
    }

    /**
     * Get all supported types. Type is supported if first task dispatcher has
     * defined paths for this type. If First task dispatcher is not defined list
     * of supported tasks will be empty.
     *
     * @return List of supported types
     */
    public List<Type> getSupportedTypes() {
        executionReadLock.lock();
        try {
            if (firstTaskDispatcher != null) {
                return firstTaskDispatcher.getAllKnownTypes();
            } else {
                return new ArrayList<>();
            }
        } finally {
            executionReadLock.unlock();
        }
    }

    protected boolean addTaskDispatcher(final TaskDispatcher taskDispatcher) {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Dodawanie TaskDispatcher ...");
                taskDispatchers.add(taskDispatcher);
                PrintHelper.printMsg(getName(), "Dodano TaskDispatcher: " + taskDispatcher.getName());
                return true;
            }
        }, executionWriteLock);
    }

    protected boolean cleanTasksDispatchers() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Czyszczenie listy task dispatchers ...");
                taskDispatchers.clear();
                firstTaskDispatcher = null;
                PrintHelper.printMsg(getName(), "Wyczyszczono liste task dispatchers.");
                return true;
            }
        }, executionWriteLock);
    }

    public List<TaskDispatcher> getTaskDispatchers() {
        executionReadLock.lock();
        try {
            return taskDispatchers;
        } finally {
            executionReadLock.unlock();
        }
    }

    public TaskDispatcher getTaskDispatcherObject(Long taskDispatcherId) {
        executionReadLock.lock();
        try {
            for (TaskDispatcher td : taskDispatchers) {
                if (td.getId().equals(taskDispatcherId)) {
                    return td;
                }
            }
            return null;
        } finally {
            executionReadLock.unlock();
        }

    }

    public TaskDispatcher getTaskDispatcherObject(ControllerUnit controllerUnit) {
        return getTaskDispatcherObject(controllerUnit.getId());
    }

    public DistributionType getDistributionType() {
        executionReadLock.lock();
        try {
            return distributionType;
        } finally {
            executionReadLock.unlock();
        }
    }

    public List<TaskImpl> getTasks() {
        executionWriteLock.lock();
        try {
            List<TaskImpl> tasks = new ArrayList<>();
            tasks.addAll(tasksBlockingQueue);
            return tasks;
        } finally {
            executionWriteLock.unlock();
        }
    }

    protected boolean setFirstTaskDispatcher(final TaskDispatcherImpl taskDispatcher) {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Ustawianie pierszwgo kontrolera ...");
                firstTaskDispatcher = taskDispatcher;
                PrintHelper.printMsg(getName(), "Ustawiono pierwszy kontroler.");
                return true;
            }
        }, executionWriteLock);
    }

    /**
     * Get first task dispatcher for processing system or null if not defined
     *
     * @return First task dispatcher or null
     */
    public TaskDispatcher getFirstTaskDispatcher() {
        executionReadLock.lock();
        try {
            return firstTaskDispatcher;
        } finally {
            executionReadLock.unlock();
        }
    }

    public void addNewTask(TaskImpl task) {
        PrintHelper.printMsg(getName(), "Odebralem zadanie...");
        try {
            tasksBlockingQueue.add(task);
            getFirstTaskDispatcher().receiveTask(task);
        } catch (PathDefinitionExcpetion | PathDefinitionInfinityLoopExcpetion ex) {
            Logger.getLogger(SystemStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getName() {
        return "SystemStorage";
    }

}
