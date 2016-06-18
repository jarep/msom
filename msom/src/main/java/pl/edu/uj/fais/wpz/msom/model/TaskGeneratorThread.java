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
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
public class TaskGeneratorThread implements Runnable {

    private final AtomicBoolean active = new AtomicBoolean(false);
    private final SystemStorage systemStorage;

    /**
     * Task entity wrappers with tasks to generate
     */
    private final List<TaskEntityWrapper> taskEntitiesWrappers = new ArrayList<>();

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
                int millis = getTimeInterval();
                try {
                    PrintHelper.printMsg(getName(), "Next task in: " + millis + " ms.");
                    Thread.sleep(millis);
                    generateTask();
                } catch (InterruptedException ex) {
                    PrintHelper.printAlert(getName(), "Dying... (interrupted exception)");
                }
            }
        } else {
            PrintHelper.printError(getName(), "There are no tasks to generate");
        }
        PrintHelper.printMsg(getName(), "Task generation finished");
    }

    /**
     * Get time after which a new task will be generated.
     *
     * @return number of milliseconds
     */
    private int getTimeInterval() {
        return ThreadLocalRandom.current().nextInt(1000);
    }

    /**
     * Generate task and add to systemStorage as new task.
     */
    private void generateTask() {
        PrintHelper.printMsg(getName(), "Generating task");
        int index = ThreadLocalRandom.current().nextInt(taskEntitiesWrappers.size());
        TaskEntityWrapper taskEntityWrapper = taskEntitiesWrappers.get(index);

        TaskImpl createdTask = createTask(taskEntityWrapper);
        createdTask.activate();
        PrintHelper.printMsg(getName(), "Task generated: " + createdTask.toString());

        systemStorage.addNewTask(createdTask);
    }

    /**
     * Create new task based on given taskEntityWrapper
     *
     * @param taskEntityWrapper
     * @return created new task
     */
    private TaskImpl createTask(TaskEntityWrapper taskEntityWrapper) {
        TaskImpl task = new TaskImpl(taskEntityWrapper.getTaskEntity(), taskService, taskEntityWrapper.incrementAndGetInstanceCounter());
        Long typeId = taskEntityWrapper.getTypeId();
        TypeImpl type = systemStorage.getTypeObject(typeId);
        task.setType(type);
        return task;
    }

    protected void activate() {
        initializeTaskEntities();
        active.set(true);
    }

    protected void deactive() {
        PrintHelper.printMsg(getName(), "Deactivating");
        taskEntitiesWrappers.clear();
        active.set(false);
        PrintHelper.printMsg(getName(), "Deactivated.");
    }

    protected boolean isActive() {
        return active.get();
    }

    /**
     * Creating list of task entity wrappers with tasks to generate in this
     * model.
     */
    private void initializeTaskEntities() {
        PrintHelper.printMsg(getName(), "Initiallizing task list");
        taskEntitiesWrappers.clear();
        for (Task taskEntity : getTaskEntitiesToGenerate()) {
            PrintHelper.printMsg(getName(), "Task: " + taskEntity.getName() + "...");
            TaskEntityWrapper entityWrapper = new TaskEntityWrapper(taskEntity, taskEntity.getTaskType().getId());
            taskEntitiesWrappers.add(entityWrapper);
        }
        PrintHelper.printMsg(getName(), "Task list ready - saved " + taskEntitiesWrappers.size() + " tasks");
    }

    /**
     * Get list of task entities to generate in this model.
     *
     * @return list of tasks to generate
     */
    private List<Task> getTaskEntitiesToGenerate() {
        return taskService.getTasksByModelId(systemStorage.getModelId());
    }

    private String getName() {
        return "GENERATOR";
    }

}
