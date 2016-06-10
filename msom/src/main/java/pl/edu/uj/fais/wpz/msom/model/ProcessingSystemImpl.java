/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
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
public class ProcessingSystemImpl extends AbstractModelObject<Model> implements ProcessingSystem {

    private final SystemStorage systemStorage;
    // services
    private final ControllerUnitService controllerUnitService;
    private final DistributionService distributionService;
    private final ModelService modelService;
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private TaskGeneratorImpl taskGenerator;

    private final AtomicBoolean active = new AtomicBoolean(false);

    public ProcessingSystemImpl(Model entityObject, ControllerUnitService controllerUnitService, DistributionService distributionService, ModelService modelService, ModuleService moduleService, ProcessingPathService pathService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.distributionService = distributionService;
        this.modelService = modelService;
        this.moduleService = moduleService;
        this.pathService = pathService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        this.systemStorage = new SystemStorage(controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
        setEntityObject(entityObject);
        initializeSystemStorage();
    }

    private void initializeSystemStorage() {
        reloadTaskDispatchers();
        reloadTypes();
        reloadPaths(); // paths are reloaded after reloaded task dispatchers, because we need all task dispatchers to create connection between them
    }

    @Override
    public boolean reload() {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Obiekt aktywny - nie mozna przeladowac.");
            return false;
        } else {
            reloadEntityObcject();
            reloadTaskDispatchers();
            reloadTypes();
            PrintHelper.printMsg(getName(), "Obiekt przeladowano.");
            return true;
        }
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            modelService.refresh(getEntityObject());
        }
    }

    private void reloadTaskDispatchers() {
        systemStorage.cleanTasksDispatchers();
        if (getEntityObject() != null) {
            List<ControllerUnit> controllersByModel = controllerUnitService.getControllersByModel(getEntityObject());
            for (ControllerUnit controllerUnit : controllersByModel) {
                TaskDispatcherImpl td = new TaskDispatcherImpl(controllerUnit, systemStorage);
                systemStorage.addTaskDispatcher(td);
            }
        }
    }

    private void reloadPaths() {
        List<TaskDispatcher> taskDispatchers = systemStorage.getTaskDispatchers();
        for (TaskDispatcher dispatcher : taskDispatchers) {
            ((TaskDispatcherImpl) dispatcher).reloadComingOutPaths();
        }
    }

    private void reloadTypes() {
        systemStorage.cleanTypes();
        if (getEntityObject() != null) {
            List<TaskType> allTaskTypes = taskTypeService.findAll(); // UWAGA - powinny byc tylko te, ktore sa powiazane z tym modelem!!! 
            //czyli wlasciwie te, ktore obsluguje pierwszy controller
//            getFirstTaskDispatcher().getAllSupportedTypes();
            for (TaskType taskType : allTaskTypes) {
                TypeImpl type = new TypeImpl(taskType, taskTypeService);
                systemStorage.addType(type);
            }
        }
    }

    @Override
    public boolean save() {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Obiekt aktywny - nie mozna zapisac.");
            return false;
        } else {
            saveEntityObject();
            saveTaskDispatchers();
            PrintHelper.printMsg(getName(), "Obiekt zapisano.");
            return true;
        }
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            modelService.update(getEntityObject());
        }
    }

    private void saveTaskDispatchers() {
        for (TaskDispatcher td : systemStorage.getTaskDispatchers()) {
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

    @Override
    public boolean startSimulation() {
        if (active.getAndSet(true)) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna ponownie rozpoczac ...");
            return false; // already started
        } else {
            PrintHelper.printMsg(getName(), "Rozpoczynam symulacje ...");
            activateTaskDispatchers();
            activateGenerator();
            PrintHelper.printMsg(getName(), "Rozpoczeto symulacje");
        }
        return true;
    }

    private void activateTaskDispatchers() {
        PrintHelper.printMsg(getName(), "Aktywuje task dispatchers ...");
        for (TaskDispatcher td : systemStorage.getTaskDispatchers()) {
            td.activate();
        }
        PrintHelper.printMsg(getName(), "Aktywowalem task dispatchers.");
    }

    private void activateGenerator() {
        PrintHelper.printMsg(getName(), "Aktywuje generator ...");
        taskGenerator = new TaskGeneratorImpl(systemStorage, taskService);
        taskGenerator.activate();
        PrintHelper.printMsg(getName(), "Aktywowalem generator.");
    }

    /**
     * For now it is only mockup - should be implemented
     */
    @Override
    public void stopSimulation() {
        if (!active.getAndSet(false)) {
            PrintHelper.printAlert(getName(), "Symualacja zatrzymana - nie mozna ponownie zatrzymac ...");
        } else {
            PrintHelper.printMsg(getName(), "Zatrzymuje symulacje ... ]");
            deactivateGenerator();
            deactivateTaskDispatchers();
            PrintHelper.printMsg(getName(), "Zatrzymano symulacje]");
        }
    }
    
    public boolean cleanSimulationData(){
        if(active.get()){
            PrintHelper.printAlert(getName(), "Nie mozna wyczyscic danych symulacji - symulacja w toku.");
            return false;
        } else {
            systemStorage.cleanTasks();
            systemStorage.cleanTasksDispatchers(); // with paths
            systemStorage.cleanTypes();
        }
        return false;
    }

    private void deactivateTaskDispatchers() {
        for (TaskDispatcher td : systemStorage.getTaskDispatchers()) {
            td.deactivate();
        }
    }

    private void deactivateGenerator() {
        if (taskGenerator != null) {
            taskGenerator.deactivate();
        }
        taskGenerator = null;
    }

    @Override
    public TaskDispatcher createTaskDispatcher(String name) {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
            return null;
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public boolean addTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException {
        if (active.get()) {
            PrintHelper.printAlert(getName(), "Symualacja w toku - nie mozna modyfikowac systemu ...");
            return false;
        } else {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public List<TaskDispatcher> getTaskDispatchers() {
        return systemStorage.getTaskDispatchers();
    }

    @Override
    public boolean setFirstTaskDispatcher(TaskDispatcher taskDispatcher) throws SystemIntegrityException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TaskDispatcher getFirstTaskDispatcher() {
        return systemStorage.getFirstTaskDispatcher();
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
        return active.get();
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
