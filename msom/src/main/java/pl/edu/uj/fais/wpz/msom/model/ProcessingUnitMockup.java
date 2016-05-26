/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.Random;
import pl.edu.uj.fais.wpz.msom.entities.Module;
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
public class ProcessingUnitMockup implements ProcessingUnit {

    private final ControllerUnitService controllerUnitService;
    private final ModuleService moduleService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private Module entityObject;
    
    private final Random generator = new Random();
    private int fakeQueueLength;
    private int fakeTaskDifficulty;

    public ProcessingUnitMockup(ControllerUnitService controllerUnitService, ModuleService moduleService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.moduleService = moduleService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
    }

    public Module getEntityObject() {
        return entityObject;
    }

    public void setEntityObject(Module entityObject) {
        this.entityObject = entityObject;
    }

    @Override
    public void setName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getName() {
        return entityObject.getName();
    }

    @Override
    public void setEfficiency(Integer efficiency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getEfficiency() {
        return entityObject.getEfficiency();
    }

    @Override
    public void setCores(Integer cores) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Integer getCores() {
        return entityObject.getCores();
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
    public void setTaskDispatcher(TaskDispatcher taskDispatcher) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void processTask(Task task) throws ProcessingAbilityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getQueueLength() {
        fakeQueueLength++;
//        fakeQueueLength = generator.nextInt(15);
        return fakeQueueLength;
    }

    @Override
    public int getQueueValue() {
        fakeTaskDifficulty = generator.nextInt(50);
        fakeTaskDifficulty++;
        return fakeQueueLength * fakeTaskDifficulty;
    }

}
