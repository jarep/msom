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

    public List<ProcessingSystem> getAllProcessingSystems() {
        return localProcessingSystems;
    }

    public ProcessingSystem reloadProcessingSystem(ProcessingSystem processingSystemToReload) {
        return reloadProcessingSystemFromDatabase(processingSystemToReload.getId());
    }

    private void reloadProcessingSystemsFromDatabase() {
        localProcessingSystems.clear();
        List<Model> models = modelService.findAll();
        for (Model m : models) {
            ProcessingSystemMockup p = new ProcessingSystemMockup(controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
            p.setEntityObject(m);
            localProcessingSystems.add(p);
        }
    }

    private ProcessingSystem reloadProcessingSystemFromDatabase(Long id) {
        removeProcessingSystemFromLocalList(id);

        Model model = modelService.find(id);
        if (model != null) {
            ProcessingSystemMockup processingSystem = new ProcessingSystemMockup(controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
            processingSystem.setEntityObject(model);
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

    public ProcessingSystem getProcessingSystem(Long id) {
        for (ProcessingSystem p : localProcessingSystems) {
            if (Objects.equals(p.getId(), id)) {
                return p;
            }
        }
        Model model = modelService.find(id);
        if (model != null) {
            ProcessingSystemMockup system = new ProcessingSystemMockup(controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
            system.setEntityObject(model);
            localProcessingSystems.add(system);
            return system;
        }
        return null;
    }

    public ProcessingSystem reloadProcessingSystem(long id) {
        return reloadProcessingSystem(getProcessingSystem(id));
    }

}
