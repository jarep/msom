/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
public class TaskGeneratorThread implements Runnable {

    private final AtomicBoolean active = new AtomicBoolean(false);
    private final SystemStorage systemStorage;

    private final List<TaskEntityWrapper> taskEntitiesWrappers = new ArrayList<>();
    private final List<Type> availableTypes = new ArrayList<>();

    private final TaskService taskService;

    public TaskGeneratorThread(SystemStorage systemStorage, TaskService taskService) {
        this.systemStorage = systemStorage;
        this.taskService = taskService;
    }

    @Override
    public void run() {
        PrintHelper.printMsg(getName(), "Starting task generation");
        if (taskEntitiesWrappers.size() > 0) {
            while (active.get()) {
                int millis = ThreadLocalRandom.current().nextInt(1000);
                try {
                    PrintHelper.printMsg(getName(), "Next task in: " + millis + " ms.");
                    Thread.sleep(millis);
                    generateTask();
                } catch (InterruptedException ex) {
                    PrintHelper.printAlert(getName(), "Dying... (interrupted exception)");
                }
            }
        } else {
            PrintHelper.printError(getName(), "No more tasks to generate");
        }
        PrintHelper.printMsg(getName(), "Task generation finished");
    }

    private void generateTask() {
        PrintHelper.printMsg(getName(), "Generating task");
        int index = ThreadLocalRandom.current().nextInt(taskEntitiesWrappers.size());
        TaskEntityWrapper taskEntityWrapper = taskEntitiesWrappers.get(index);
        TaskImpl task = new TaskImpl(taskEntityWrapper.getTaskEntity(), taskService, taskEntityWrapper.incrementAndGetInstanceCounter());
        Long typeId = taskEntityWrapper.getTypeId();
        TypeImpl type = systemStorage.getTypeObject(typeId);
        task.setType(type);
        task.activate();
        PrintHelper.printMsg(getName(), "Task generated: " + task.toString());
        systemStorage.addNewTask(task);
    }

    protected void activate() {
        initializeTypes();
        initializeTaskEntities();
        active.set(true);
    }

    protected void deactive() {
        PrintHelper.printMsg(getName(), "Deactivating");
        taskEntitiesWrappers.clear();
        availableTypes.clear();
        active.set(false);
        PrintHelper.printMsg(getName(), "Deactivated.");
    }

    protected boolean isActive() {
        return active.get();
    }

    private void initializeTypes() {
        PrintHelper.printMsg(getName(), "Initiallizing types");
        availableTypes.clear();
        availableTypes.addAll(systemStorage.getSupportedTypes());
        PrintHelper.printMsg(getName(), "Types list ready - saved " + availableTypes.size() + " types");
    }

    private void initializeTaskEntities() {
        PrintHelper.printMsg(getName(), "Initiallizing task list");
        taskEntitiesWrappers.clear();
        for (Task taskEntity : getTaskEntitiesToGenerate()) {
            TaskEntityWrapper entityWrapper = new TaskEntityWrapper(taskEntity, taskEntity.getTaskType().getId());
            taskEntitiesWrappers.add(entityWrapper);
        }
        PrintHelper.printMsg(getName(), "Task list ready - saved " + taskEntitiesWrappers.size() + " tasks");
    }

    private List<Task> getTaskEntitiesToGenerate() {
        List<Long> idsByTypes = getIdsByTypes(availableTypes);
        // powinny byz zadania przypisane do modelu, nie koniecznie wszystkie ktorych typ jest obslugiwany
        List<Task> tasksByTaskTypeIds = taskService.getTasksByTaskTypeIds(idsByTypes);
        return tasksByTaskTypeIds;
    }

    private List<Long> getIdsByTypes(List<Type> types) {
        List<Long> ids = new ArrayList<>();
        for (Type type : types) {
            ids.add(type.getId());
        }
        return ids;
    }

    private String getName() {
        return "GENERATOR";
    }

}
