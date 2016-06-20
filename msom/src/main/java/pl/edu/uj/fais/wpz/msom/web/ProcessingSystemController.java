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
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.PathDefinitionInfinityLoopExcpetion;
import pl.edu.uj.fais.wpz.msom.model.exceptions.ProcessingAbilityException;
import pl.edu.uj.fais.wpz.msom.model.exceptions.SystemIntegrityException;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingSystem;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.DistributionService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskProbabilityService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
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
    private TaskProbabilityService taskProbabilityService;
    @Autowired
    private TaskTypeService taskTypeService;

    private ProcessingSystemTool processingSystemTool;

    @RequestMapping(value = "/processingsystem", method = RequestMethod.GET)
    public String showAllProcessingSystems(Model model) {
        setProcessingSystemToolIfDoesNotExist();

        List<ProcessingSystem> processingSystems = processingSystemTool.getAllProcessingSystems();
        model.addAttribute("processingSystems", processingSystems);
        return "processingsystem/list";
    }

    /*
     @RequestMapping(value = "/processingsystem/view/{id}", method = RequestMethod.GET)
     public String viewProcessingSystem(@PathVariable("id") long id, Model model) {
     setProcessingSystemToolIfDoesNotExist();

     ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);

     if (processingSystem == null) throw new NullPointerException();

     model.addAttribute("processingSystem", processingSystem);

     return "processingsystem/view";
     }
     */
    @RequestMapping(value = "/processingsystem/simulate/{id}", method = RequestMethod.GET)
    public String simulateProcessingSystem(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);

        if (processingSystem == null) {
            throw new NullPointerException();
        }

        model.addAttribute("message", "Hello!");
        model.addAttribute("processingSystem", processingSystem);
        model.addAttribute("isLocked", processingSystem.isLocked());

        return "processingsystem/simulate";
    }

    @RequestMapping(value = "/processingsystem/refresh/{id}", method = RequestMethod.GET)
    public String refreshProcessingSystem(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);

        if (processingSystem == null) {
            throw new NullPointerException();
        }

        model.addAttribute("message", "The system is running...");
        model.addAttribute("processingSystem", processingSystem);

        return "processingsystem/refresh";
    }

    @RequestMapping(value = "/processingsystem/reset/{id}", method = RequestMethod.GET)
    public String resetProcessingSystem(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);
        if (processingSystem.isLocked()) {
            model.addAttribute("processingSystem", processingSystem);
            model.addAttribute("message", "Unable to reset - the system is locked.");
        } else {
            processingSystem = processingSystemTool.reloadProcessingSystem(id);
            model.addAttribute("processingSystem", processingSystem);
            model.addAttribute("message", "The system was reset.");
        }

        //if (processingSystem == null) throw new NullPointerException();
        model.addAttribute("isLocked", processingSystem.isLocked());

        return "processingsystem/simulate";
    }

    @RequestMapping(value = "processingsystem/start/{id}", method = RequestMethod.GET)
    public String startSimulation(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);
        if (processingSystem == null) {
            throw new NullPointerException();
        }

        String msg = "";
        try {
            if (processingSystem.validate()) {
                processingSystem.startSimulation();
                msg += "The simulation of model with id " + id + " was started.";
            } else {
                msg += "The simulation of model with id " + id + " can not be started.";
            }
        } catch (SystemIntegrityException | ProcessingAbilityException | PathDefinitionException | PathDefinitionInfinityLoopExcpetion ex) {
            msg += getValidationFailedMsg(ex);
        }

        model.addAttribute("processingSystem", processingSystem);
        model.addAttribute("message", msg);
        model.addAttribute("isLocked", processingSystem.isLocked());
        return "processingsystem/simulate";
    }

    @RequestMapping(value = "processingsystem/validate/{id}", method = RequestMethod.GET)
    public String validateSystem(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);
        if (processingSystem == null) {
            throw new NullPointerException();
        }

        String msg = "";
        try {
            if (processingSystem.validate()) {
                msg += "<strong>Validation success.</strong>";
            } else {
                msg += "<strong>Validation failed.</strong>";
            }
        } catch (SystemIntegrityException | ProcessingAbilityException | PathDefinitionException | PathDefinitionInfinityLoopExcpetion ex) {
            msg += getValidationFailedMsg(ex);
        }

        model.addAttribute("processingSystem", processingSystem);
        model.addAttribute("message", msg);
        model.addAttribute("isLocked", processingSystem.isLocked());
        return "processingsystem/simulate";
    }

    private String getValidationFailedMsg(Exception ex) {
        String msg = "<strong>Validation failed: </strong>";
        msg += ex.getMessage();
        msg += " (if already fixed - don't forget click \"reset\" button to reload changes)";
        return msg;
    }

    @RequestMapping(value = "processingsystem/stop/{id}", method = RequestMethod.GET)
    public String stopSimulation(@PathVariable("id") long id, Model model) {
        setProcessingSystemToolIfDoesNotExist();

        ProcessingSystem processingSystem = processingSystemTool.getProcessingSystem(id);
        //if (processingSystem == null) throw new NullPointerException();
        processingSystem.stopSimulation();

        model.addAttribute("processingSystem", processingSystem);
        model.addAttribute("message", "The simulation of model with id " + id + " was stopped");
        model.addAttribute("isLocked", processingSystem.isLocked());
        return "processingsystem/simulate";
    }

    private void setProcessingSystemToolIfDoesNotExist() {
        if (processingSystemTool == null) {
            setProcessingSystemTool();
        }
    }

    private void setProcessingSystemTool() {
        processingSystemTool = new ProcessingSystemTool(controllerUnitService, distributionService, modelService, moduleService, pathService, taskService,taskProbabilityService, taskTypeService);
    }
}
