/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.uj.fais.wpz.msom.model.ProcessingSystemTool;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingSystem;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.DistributionService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 * Provides CRUD for processing system controller
 * @author jarep
 */
@Controller
public class ProcessingSystemController {

    @Autowired
    private ControllerUnitService controllerUnitService;
    @Autowired
    private DistributionService distributionService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private ModuleService moduleService;
    @Autowired
    private ProcessingPathService pathService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskTypeService taskTypeService;

    private ProcessingSystemTool processingSystemTool;

    /**
     *
     * @param model actual model
     * @return path to view with processing system list
     */
    @RequestMapping(value = "/processingsystem", method = RequestMethod.GET)
    public String showAllProcessingSystems(Model model) {
        setProcessingSystemToolIfDoesNotExist();

        List<ProcessingSystem> processingSystems = processingSystemTool.getAllProcessingSystems();
        model.addAttribute("processingSystems", processingSystems);
        return "processingsystem/list";
    }

    /**
     *
     * @param id Processing System
     * @param model actual model
     * @return path to view with processing system site
     */
    @RequestMapping(value = "/processingsystem/view/{id}", method = RequestMethod.GET)
    public String viewProcessingSystem(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);

        if (processingSystem == null) {
            model.addAttribute("errorMsg", "404 Bad Request...");
            return "errorpage";
        }

        model.addAttribute("processingSystem", processingSystem);

        return "processingsystem/view";
    }

    /**
     *
     * @param id Processing System
     * @param model actual model
     * @return  path to view with processing system list
     */
    @RequestMapping(value = "/processingsystem/simulate/{id}", method = RequestMethod.GET)
    public String simulateProcessingSystem(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);

        if (processingSystem == null) {
            model.addAttribute("errorMsg", "404 Bad Request...");
            return "errorpage";
        }

        model.addAttribute("processingSystem", processingSystem);

        return "processingsystem/simulate";
    }

    /**
     *
     * @param id Processing System
     * @param model actual model
     * @return path to view with processing system simulate
     */
    @RequestMapping(value = "/processingsystem/reset/{id}", method = RequestMethod.GET)
    public String resetProcessingSystem(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.reloadProcessingSystem(id);

        if (processingSystem == null) {
            model.addAttribute("errorMsg", "404 Bad Request...");
            return "errorpage";
        }

        model.addAttribute("processingSystem", processingSystem);

        return "processingsystem/simulate";
    }

    private void setProcessingSystemToolIfDoesNotExist() {
        if (processingSystemTool == null) {
            setProcessingSystemTool();
        }
    }
    private void setProcessingSystemTool() {
            processingSystemTool = new ProcessingSystemTool(controllerUnitService, distributionService, modelService, moduleService, pathService, taskService, taskTypeService);
    }
}
