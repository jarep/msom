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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;

/**
 *
 * @author jarep
 */
@Controller
public class ProcessingPathsController {

    @Autowired
    ControllerUnitService controllerUnitService;

    @Autowired
    ProcessingPathService processingPathService;

    @Autowired
    ModelService modelService;

    @RequestMapping(value = "/processingpaths/new/{controllerId}", method = RequestMethod.GET)
    public String createProcessingPath(@PathVariable("controllerId") long controllerId, Model model) {
        ControllerUnit controllerUnit = controllerUnitService.find(controllerId); // can be null!!!
        if (controllerUnit == null) {
            // redirect to error page 
            model.addAttribute("errorMsg", "Controller Unit with id " + controllerId + " not found...");
            return "errorpage";
        } else {
            ProcessingPath path = new ProcessingPath();
            path.setControllerUnit(controllerUnit);
            model.addAttribute("path", path);

            List<TaskType> typesWithUnspecifiedPathFromContoller = controllerUnitService.getTypesWithUnspecifiedPathFromContoller(controllerUnit);
            model.addAttribute("unspecifiedTypes", typesWithUnspecifiedPathFromContoller);

            if (typesWithUnspecifiedPathFromContoller.isEmpty()) {
                model.addAttribute("errorMsg", "All paths from this Controller Unit are already defined.");
                return "errorpage";
            }

            List<ControllerUnit> controllers = controllerUnitService.getControllersByModel(controllerUnit.getModel());
            model.addAttribute("controllers", controllers);
            return "processingpaths/new";
        }

    }

    @RequestMapping(value = "/processingpaths/new/{controllerId}", method = RequestMethod.POST)
    public String addProcessingPath(@PathVariable("controllerId") long controllerId, @ModelAttribute(value = "path") ProcessingPath path, Model model) {
        if (!processingPathService.add(path)) {
            String errorMsg = "Can not create this processing path... Probably Controller Units are assigned to different Models.";
            model.addAttribute("errorMsg", errorMsg);
            return "errorpage";
        }
        return "redirect:/controllerunits/" + controllerId;

    }

    @RequestMapping(value = "/processingpaths/remove/{controllerId}/{pathId}", method = RequestMethod.POST)
    public String deleteProcessingPath(@PathVariable("controllerId") long controllerId, @PathVariable("pathId") long pathId, Model model) {

        ProcessingPath path = processingPathService.find(pathId);
        boolean wasDeleted = processingPathService.remove(path);

        if (!wasDeleted) {
            model.addAttribute("errorMsg", "Can not remove this processing path...");
            return "errorpage";
        }

        return "redirect:/controllerunits/" + controllerId;
    }

}
