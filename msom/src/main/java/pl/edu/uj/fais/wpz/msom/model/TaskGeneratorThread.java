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
        PrintHelper.printMsg(getName(), "Poczatek generowania zadan");
        if (taskEntitiesWrappers.size() > 0) {
            while (active.get()) {
                int millis = ThreadLocalRandom.current().nextInt(1000);
                try {
                    PrintHelper.printMsg(getName(), "Nastepne zadanie za: " + millis + " ms.");
                    Thread.sleep(millis);
                    generateTask();
                } catch (InterruptedException ex) {
                    PrintHelper.printAlert(getName(), "Umieram... (interrupted exception)");
                }
            }
        } else {
            PrintHelper.printError(getName(), "Nie ma zadnych zadan do wygenerowania!");
        }
        PrintHelper.printMsg(getName(), "Koniec generowania zadan");
    }

    private void generateTask() {
        PrintHelper.printMsg(getName(), "Generuje zadanie...");
        int index = ThreadLocalRandom.current().nextInt(taskEntitiesWrappers.size());
        TaskEntityWrapper taskEntityWrapper = taskEntitiesWrappers.get(index);
        TaskImpl task = new TaskImpl(taskEntityWrapper.getTaskEntity(), taskService, taskEntityWrapper.incrementAndGetInstanceCounter());
        Long typeId = taskEntityWrapper.getTypeId();
        TypeImpl type = systemStorage.getTypeObject(typeId);
        task.setType(type);
        task.activate();
        PrintHelper.printMsg(getName(), "Wygenerowalem zadanie: " + task.toString());
        systemStorage.addNewTask(task);
    }

    protected void activate() {
        initializeTypes();
        initializeTaskEntities();
        active.set(true);
    }

    protected void deactive() {
        PrintHelper.printMsg(getName(), "deaktywacja...");
        taskEntitiesWrappers.clear();
        availableTypes.clear();
        active.set(false);
        PrintHelper.printMsg(getName(), "deaktywowano.");
    }

    protected boolean isActive() {
        return active.get();
    }

    private void initializeTypes() {
        PrintHelper.printMsg(getName(), "inicializuje typy...");
        availableTypes.clear();
        availableTypes.addAll(systemStorage.getSupportedTypes());
        PrintHelper.printMsg(getName(), "lista typow gotowa - zapisano " + availableTypes.size() + " typy/ow.");
    }

    private void initializeTaskEntities() {
        PrintHelper.printMsg(getName(), "inicializuje liste zadan...");
        taskEntitiesWrappers.clear();
        for (Task taskEntity : getTaskEntitiesToGenerate()) {
            TaskEntityWrapper entityWrapper = new TaskEntityWrapper(taskEntity, taskEntity.getTaskType().getId());
            taskEntitiesWrappers.add(entityWrapper);
        }
        PrintHelper.printMsg(getName(), "lista zadan gotowa - zapisano " + taskEntitiesWrappers.size() + " zadan.");
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
