/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.mockups;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class ProcessingUnitMockup extends AbstractModelMockupObject<Module> implements ProcessingUnit {

    private final ControllerUnitService controllerUnitService;
    private final ModuleService moduleService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private final Random generator = new Random();
    private int fakeQueueLength;
    private int fakeTaskDifficulty;

    private final List<Type> availableTypes = new ArrayList<>();

    public ProcessingUnitMockup(Module entityObject, ControllerUnitService controllerUnitService, ModuleService moduleService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.moduleService = moduleService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        setEntityObject(entityObject);
    }

    @Override
    public boolean reload() {
        reloadEntityObcject();
        reloadAvailableTypes();
        return true;
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            moduleService.refresh(getEntityObject());
        }
    }

    private void reloadAvailableTypes() {
        availableTypes.clear();
        if (getEntityObject() != null) {
            Set<TaskType> taskTypes = getEntityObject().getTaskTypes();
            for (TaskType t : taskTypes) {
                TypeMockup type = new TypeMockup(t, taskTypeService);
                availableTypes.add(type);
            }
        }
    }

    @Override
    public boolean save() {
        saveEntityObject();
//        saveTaskTypes();
        return true;
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            moduleService.update(getEntityObject());
        }
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return getEntityObject().getName();
    }

    @Override
    public void setEfficiency(Integer efficiency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getEfficiency() {
        return getEntityObject().getEfficiency();
    }

    @Override
    public void setCores(Integer cores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getCores() {
        return getEntityObject().getCores();
    }

    @Override
    public void addType(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeType(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Type> getAvailableTypes() {
        return availableTypes;
    }

    @Override
    public void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTask(Task task) throws ProcessingAbilityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * For now it is only mockup - should be implemented
     */
    @Override
    public int getQueueLength() {
        fakeQueueLength++;
        return fakeQueueLength;
    }

    /**
     * For now it is only mockup - should be implemented
     */
    @Override
    public int getQueueValue() {
        fakeTaskDifficulty = generator.nextInt(50);
        fakeTaskDifficulty++;
        return fakeQueueLength * fakeTaskDifficulty;
    }

    @Override
    public List<Task> getWaitingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Task> getProcessingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfProcessingTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getPercentOfExecutionCurrentTasks() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAvgWaitingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getAvgProcessingTime() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean activate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deactivate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
