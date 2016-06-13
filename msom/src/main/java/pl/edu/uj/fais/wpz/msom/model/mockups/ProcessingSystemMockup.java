/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.mockups;

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
public class ProcessingSystemMockup extends AbstractModelMockupObject<Model> implements ProcessingSystem {

    private final ControllerUnitService controllerUnitService;
    private final DistributionService distributionService;
    private final ModelService modelService;
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private final List<TaskDispatcher> taskDispatchers = new ArrayList<>();
    private boolean locked = false;

    public ProcessingSystemMockup(Model entityObject, ControllerUnitService controllerUnitService, DistributionService distributionService, ModelService modelService, ModuleService moduleService, ProcessingPathService pathService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.distributionService = distributionService;
        this.modelService = modelService;
        this.moduleService = moduleService;
        this.pathService = pathService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        setEntityObject(entityObject);
    }

    @Override
    public boolean reload() {
        reloadEntityObcject();
        reloadTaskDispatchers();
        return true;
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            modelService.refresh(getEntityObject());
        }
    }

    private void reloadTaskDispatchers() {
        taskDispatchers.clear();
        if (getEntityObject() != null) {
            List<ControllerUnit> controllersByModel = controllerUnitService.getControllersByModel(getEntityObject());
            for (ControllerUnit controllerUnit : controllersByModel) {
                TaskDispatcherMockup td = new TaskDispatcherMockup(controllerUnit, controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
                taskDispatchers.add(td);
            }
        }
    }

    @Override
    public boolean save() {
        saveEntityObject();
        saveTaskDispatchers();
        return true;
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            modelService.update(getEntityObject());
        }
    }

    private void saveTaskDispatchers() {
        for (TaskDispatcher td : taskDispatchers) {
            td.save();
        }
    }

    @Override
    public void setName(String name) {
        getEntityObject().setName(name);
    }

    @Override
    public String getName() {
        return getEntityObject().getName();
    }

    /**
     * For now it is only mockup - should be implemented
     */
    @Override
    public boolean startSimulation() {
        locked = true;
        return locked;
    }

    /**
     * For now it is only mockup - should be implemented
     * @return 
     */
    @Override
    public boolean stopSimulation() {
        locked = false;
        return true;
    }

    @Override
    public TaskDispatcher createTaskDispatcher(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean addTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TaskDispatcher> getTaskDispatchers() {
        return taskDispatchers;
    }

    @Override
    public boolean setFirstTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException {
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

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public double getAvgProcessingTimeByType(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double getAvgWaitingTimeByType(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfGeneratedTasksByType(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getNumberOfFinishedTasksByType(Type type) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
