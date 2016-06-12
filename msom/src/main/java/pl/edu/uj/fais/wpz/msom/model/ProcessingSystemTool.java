/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingSystem;
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
public class ProcessingSystemTool {

    private final ControllerUnitService controllerUnitService;
    private final DistributionService distributionService;
    private final ModelService modelService;
    private final ModuleService moduleService;
    private final ProcessingPathService pathService;
    private final TaskService taskService;
    private final TaskTypeService taskTypeService;

    private final List<ProcessingSystem> localProcessingSystems = new ArrayList<>();

    /**
     * Constructor
     * @param controllerUnitService
     * @param distributionService
     * @param modelService
     * @param moduleService
     * @param pathService
     * @param taskService
     * @param taskTypeService
     */
    public ProcessingSystemTool(ControllerUnitService controllerUnitService, DistributionService distributionService, ModelService modelService, ModuleService moduleService, ProcessingPathService pathService, TaskService taskService, TaskTypeService taskTypeService) {
        this.controllerUnitService = controllerUnitService;
        this.distributionService = distributionService;
        this.modelService = modelService;
        this.moduleService = moduleService;
        this.pathService = pathService;
        this.taskService = taskService;
        this.taskTypeService = taskTypeService;
        reloadProcessingSystemsFromDatabase();
    }

    /**
     *
     * @return all procesing systems
     */
    public List<ProcessingSystem> getAllProcessingSystems() {
        updateProcessingSystemsList();
        return localProcessingSystems;
    }

    /**
     *
     * @param processingSystemToReload
     * @return reloaded processing system
     */
    public ProcessingSystem reloadProcessingSystem(ProcessingSystem processingSystemToReload) {
        return reloadProcessingSystemFromDatabase(processingSystemToReload.getId());
    }

    /**
     * Get new models from database if exist and remove no longer exist models.
     * Does not change local data for processing systems if still exist in
     * database.
     */
    private void updateProcessingSystemsList() {
        getNewProcessingSystemsFromDatabase();
        removeDoesNotExistsProcessingSystems();;
    }

    private void getNewProcessingSystemsFromDatabase() {
        List<Model> models = modelService.findAll();
        for (Model m : models) {
            Long id = m.getId();
            if (!isProcessingSystemOnLocalList(id)) {
                ProcessingSystemMockup processingSystem = new ProcessingSystemMockup(m, controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
                localProcessingSystems.add(processingSystem);
            }
        }
    }

    private void removeDoesNotExistsProcessingSystems() {
        List<ProcessingSystem> systemsToRemove = new ArrayList<>();
        for (ProcessingSystem p : localProcessingSystems) {
            if (!isProcessingSystemInDatabase(p.getId())) {
                systemsToRemove.add(p);
            }
        }
        localProcessingSystems.removeAll(systemsToRemove);
    }

    private boolean isProcessingSystemOnLocalList(Long id) {
        for (ProcessingSystem p : localProcessingSystems) {
            if (Objects.equals(p.getId(), id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isProcessingSystemInDatabase(Long id) {
        Model find = modelService.find(id);
        if (find != null) {
            return true;
        }
        return false;
    }

    private void reloadProcessingSystemsFromDatabase() {
        localProcessingSystems.clear();
        List<Model> models = modelService.findAll();
        for (Model m : models) {
            ProcessingSystemMockup p = new ProcessingSystemMockup(m, controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
            localProcessingSystems.add(p);
        }
    }

    private ProcessingSystem reloadProcessingSystemFromDatabase(Long id) {
        removeProcessingSystemFromLocalList(id);

        Model model = modelService.find(id);
        if (model != null) {
            ProcessingSystemMockup processingSystem = new ProcessingSystemMockup(model, controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
            localProcessingSystems.add(processingSystem);
            return processingSystem;
        }
        return null;
    }

    private void removeProcessingSystemFromLocalList(Long id) {
        List<ProcessingSystem> processingSystemsToRemove = new ArrayList<>();
        for (ProcessingSystem p : localProcessingSystems) {
            if (p.getId().equals(id)) {
                processingSystemsToRemove.add(p);
            }
        }
        localProcessingSystems.removeAll(processingSystemsToRemove);
    }

    /**
     *
     * @param id processing system
     * @return processing system
     */
    public ProcessingSystem getProcessingSystem(Long id) {
        for (ProcessingSystem p : localProcessingSystems) {
            if (Objects.equals(p.getId(), id)) {
                return p;
            }
        }
        Model model = modelService.find(id);
        if (model != null) {
            ProcessingSystemMockup system = new ProcessingSystemMockup(model, controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
            localProcessingSystems.add(system);
            return system;
        }
        return null;
    }

    /**
     *
     * @param id processing system
     * @return reloaded procesing system
     */
    public ProcessingSystem reloadProcessingSystem(long id) {
        return reloadProcessingSystem(getProcessingSystem(id));
    }

}
