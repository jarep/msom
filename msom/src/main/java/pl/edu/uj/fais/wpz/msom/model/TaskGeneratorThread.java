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
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
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

//    private final List<pl.edu.uj.fais.wpz.msom.entities.Task> taskEntities = new ArrayList<>();
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
        while (active.get()) {
            int millis = ThreadLocalRandom.current().nextInt(1000);
//            millis = millis + 1000;
            try {
                PrintHelper.printMsg(getName(), "Nastepne zadanie za: " + millis + " ms.");
                Thread.sleep(millis);
                generateTask();
            } catch (InterruptedException ex) {
                PrintHelper.printAlert(getName(), "Umieram... (interrupted exception)");
//                Logger.getLogger(TaskGeneratorThread.class.getName()).log(Level.SEVERE, null, ex);
            }
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
        availableTypes.addAll(systemStorage.getAllSupportedTypes());
        PrintHelper.printMsg(getName(), "lista typow gotowa.");
    }

    private void initializeTaskEntities() {
        PrintHelper.printMsg(getName(), "inicializuje liste zadan...");
        List<Long> idsByTypes = getIdsByTypes(availableTypes);
        List<Task> tasksByTaskTypeIds = taskService.getTasksByTaskTypeIds(idsByTypes);
        taskEntitiesWrappers.clear();
        for (Task taskEntity : tasksByTaskTypeIds) {
            TaskEntityWrapper entityWithTypeId = new TaskEntityWrapper(taskEntity, taskEntity.getTaskType().getId());
            taskEntitiesWrappers.add(entityWithTypeId);
        }
//        taskEntities.clear();
//        taskEntities.addAll(taskService.getTasksByTaskTypeIds(idsByTypes));
        PrintHelper.printMsg(getName(), "lista zadan gotowa.");
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
