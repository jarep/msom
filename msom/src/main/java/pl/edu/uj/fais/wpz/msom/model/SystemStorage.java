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
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;

/**
 *
 * @author jarep
 */
public class SystemStorage {

    private final List<TaskDispatcher> taskDispatchers = new ArrayList<>();
    private final List<TypeImpl> types = new ArrayList<>();
    private final List<TaskImpl> tasks = new ArrayList<>();
    private final DistributionType distributionType = DistributionType.UNKNOWN;
    private final AtomicBoolean active = new AtomicBoolean(false);

    // nalozyc blokady, kolejki bezpieczne watkowo ...
    
    public SystemStorage() {
    }

    public void cleanTasks() {
        tasks.clear();
    }

    protected boolean cleanTypes() {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
            return false;
        } else {
            types.clear();
            PrintHelper.printMsg(getName(), "Wyczyszczono liste typow.");
            return true;
        }
    }

    protected boolean addType(TypeImpl type) {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
            return false;
        } else {
            types.add(type);
            PrintHelper.printMsg(getName(), "Dodano typ: " + type.getName());
            return true;
        }
    }

    public TypeImpl getTypeObject(Long typeId) {
        for (TypeImpl t : types) {
            if (t.getId().equals(typeId)) {
                return t;
            }
        }
        return null;
    }
    
    public List<TypeImpl> getAllSupportedTypes(){
        return types;
    }

    protected boolean addTaskDispatcher(TaskDispatcher taskDispatcher) {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
            return false;
        } else {
            taskDispatchers.add(taskDispatcher);
            PrintHelper.printMsg(getName(), "Dodano TaskDispatcher: " + taskDispatcher.getName());
            return true;
        }
    }

    protected boolean cleanTasksDispatchers() {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
            return false;
        } else {
            taskDispatchers.clear();
            PrintHelper.printMsg(getName(), "Wyczyszczono liste task dispatchers.");
            return true;
        }
    }

    public List<TaskDispatcher> getTaskDispatchers() {
        return taskDispatchers;
    }

    public TaskDispatcher getTaskDispacherObject(Long taskDispatcherId) {
        for (TaskDispatcher td : taskDispatchers) {
            if (td.getId().equals(taskDispatcherId)) {
                return td;
            }
        }
        return null;
    }

    public DistributionType getDistributionType() {
        return distributionType;
    }

    public List<TaskImpl> getTasks() {
        return tasks;
    }

    public TaskDispatcher getFirstTaskDispatcher() {
        return taskDispatchers.get(0); // UWAGA - TYLKO TYMCZASOWO - w bazie nie przechowujemy informacji, ktory controler jest pierwszy - trzeba to dobudowac!!!!
    }

    public void addNewTask(TaskImpl task) {
        PrintHelper.printMsg(getName(), "Odebralem zadanie...");
        try {
            tasks.add(task);
            getFirstTaskDispatcher().receiveTask(task);
        } catch (PathDefinitionExcpetion ex) {
            Logger.getLogger(SystemStorage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PathDefinitionInfinityLoopExcpetion ex) {
            Logger.getLogger(SystemStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String getName() {
        return "SystemStorage";
    }

}
