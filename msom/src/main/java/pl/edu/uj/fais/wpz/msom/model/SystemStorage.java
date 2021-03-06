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
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
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
public class SystemStorage extends Activatable {

    // services
    private final ControllerUnitService controllerUnitService;
    private final DistributionService distributionService;
    private final ModelService modelService;
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;
    private final TaskProbabilityService taskProbabilityService;

    private final List<TaskDispatcher> taskDispatchers = new ArrayList<>();
    /**
     * Task entity wrappers with tasks to generate
     */
    private final List<TaskEntityWrapper> taskEntitiesWrappers = new ArrayList<>();

    private TaskDispatcherImpl firstTaskDispatcher = null;
    /**
     * All types available in this processing system - used by any processing
     * unit or any processing path.
     */
    private final List<Type> allTypes = new ArrayList<>();

    private final AtomicLong modelId = new AtomicLong();

    /**
     * All generated tasks.
     */
    private final BlockingQueue<TaskImpl> tasksBlockingQueue = new LinkedBlockingQueue<>();

    protected SystemStorage(Long modelId, ControllerUnitService controllerUnitService, DistributionService distributionService, ModelService modelService, ModuleService moduleService, ProcessingPathService pathService, TaskService taskService,TaskProbabilityService taskProbabilityService,TaskTypeService taskTypeService) {
        this.modelId.set(modelId);
        this.controllerUnitService = controllerUnitService;
        this.distributionService = distributionService;
        this.modelService = modelService;
        this.moduleService = moduleService;
        this.pathService = pathService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        this.taskProbabilityService = taskProbabilityService;        
    }

    public ControllerUnitService getControllerUnitService() {
        return controllerUnitService;
    }

    public TaskProbabilityService getTaskProbabilityService() {
        return taskProbabilityService;
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

    public Long getModelId() {
        return modelId.get();
    }

    public boolean clean() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Clearing simulation data");
                cleanTasks();
                cleanTasksDispatchers();
                cleanTypes();
                cleanTaskEntityWrappers();
                PrintHelper.printMsg(getName(), "Simulation data cleared.");
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
                PrintHelper.printMsg(getName(), "Clearing types list");
                allTypes.clear();
                PrintHelper.printMsg(getName(), "Types list cleared");
                return true;
            }
        }, executionWriteLock);
    }

    protected boolean cleanTaskEntityWrappers() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Clearing task entities list");
                taskEntitiesWrappers.clear();
                PrintHelper.printMsg(getName(), "Task entities list cleared");
                return true;
            }
        }, executionWriteLock);
    }

    protected boolean addTaskEntityWrapper(final TaskEntityWrapper taskEntityWrapper) {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Adding task entity wrapper");
                taskEntitiesWrappers.add(taskEntityWrapper);
                PrintHelper.printMsg(getName(), "Task entity wrapper was added: " + taskEntityWrapper.getTaskEntity().getName());
                return true;
            }
        }, executionWriteLock);
    }

    /**
     * Get list of task entity wrappers with tasks to generate in this model.
     *
     * @return list of tasks to generate
     */
    protected List<TaskEntityWrapper> getTaskEntityWrappers() {
        executionReadLock.lock();
        try {
            return taskEntitiesWrappers;
        } finally {
            executionReadLock.unlock();
        }
    }

    protected boolean addType(final TypeImpl type) {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Adding type");
                allTypes.add(type);
                PrintHelper.printMsg(getName(), "Type was added: " + type.getName());
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
            for (Type t : allTypes) {
                if (t.getId().equals(typeId)) {
                    return (TypeImpl) t;
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
            for (Type type : allTypes) {
                if (type.getId().equals(taskType.getId())) {
                    return (TypeImpl) type;
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
    public List<Type> getAllTypes() {
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
                PrintHelper.printMsg(getName(), "Adding task dispatcher");
                taskDispatchers.add(taskDispatcher);
                PrintHelper.printMsg(getName(), "Task dispatcher added: " + taskDispatcher.getName());
                return true;
            }
        }, executionWriteLock);
    }

    protected boolean cleanTasksDispatchers() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Clearing task dispatcher list");
                taskDispatchers.clear();
                firstTaskDispatcher = null;
                PrintHelper.printMsg(getName(), "Task dispatcher list cleared");
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
                PrintHelper.printMsg(getName(), "Setting up first controller");
                firstTaskDispatcher = taskDispatcher;
                PrintHelper.printMsg(getName(), "First controller set up");
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
        PrintHelper.printMsg(getName(), "Received task");
        try {
            tasksBlockingQueue.add(task);
            getFirstTaskDispatcher().receiveTask(task);
        } catch (PathDefinitionException | PathDefinitionInfinityLoopExcpetion ex) {
            Logger.getLogger(SystemStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getName() {
        return "SystemStorage";
    }

}
