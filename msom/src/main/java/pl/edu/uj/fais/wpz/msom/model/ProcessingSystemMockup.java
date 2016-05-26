/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingSystem;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
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
public class ProcessingSystemMockup implements ProcessingSystem {

    private final ControllerUnitService controllerUnitService;
    private final DistributionService distributionService;
    private final ModelService modelService;
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private Model entityObject;

    private final List<TaskDispatcher> taskDispatchers = new ArrayList<>();

    public ProcessingSystemMockup(ControllerUnitService controllerUnitService, DistributionService distributionService, ModelService modelService, ModuleService moduleService, ProcessingPathService pathService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.distributionService = distributionService;
        this.modelService = modelService;
        this.moduleService = moduleService;
        this.pathService = pathService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
    }

    public Model getEntityObject() {
        return entityObject;
    }

    public void setEntityObject(Model entityObject) {
        this.entityObject = entityObject;
        reloadProcessingSystem();
    }

    public void reloadProcessingSystem() {
        if (entityObject != null) {
            taskDispatchers.clear();

            List<ControllerUnit> controllersByModel = controllerUnitService.getControllersByModel(entityObject);
            for (ControllerUnit controllerUnit : controllersByModel) {
                TaskDispatcherMockup td = new TaskDispatcherMockup(controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
                td.setEntityObject(controllerUnit);
                taskDispatchers.add(td);
            }
        }
    }

    @Override
    public void setName(String name) {
        entityObject.setName(name);
        // update ...
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long getId() {
        return entityObject.getId();
    }

    @Override
    public String getName() {
        return entityObject.getName();
    }

    @Override
    public boolean startSimulation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void stopSimulation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TaskDispatcher createTaskDispatcher(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TaskDispatcher> getTaskDispatchers() {
        return taskDispatchers;
    }

    @Override
    public void setFirstTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TaskDispatcher getFirstTaskDispatcher() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validate() throws SystemIntegrityException, ProcessingAbilityException, PathDefinitionExcpetion, PathDefinitionInfinityLoopExcpetion {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setDistributionType(DistributionType distributionType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DistributionType getDistributionType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
