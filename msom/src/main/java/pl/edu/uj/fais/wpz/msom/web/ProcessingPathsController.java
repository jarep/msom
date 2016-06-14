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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;

/**
 * Provides CRUD for processing paths of controller
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

    /**
     *
     * @param controllerId id controller
     * @param model actual model
     * @param redirectAttributes is used for response messages about method action
     * @return path to view with new processing path site
     */
    @RequestMapping(value = "/processingpaths/new/{controllerId}", method = RequestMethod.GET)
    public String createProcessingPath(@PathVariable("controllerId") long controllerId, Model model, RedirectAttributes redirectAttributes) {
        ControllerUnit controllerUnit = controllerUnitService.find(controllerId);
        ProcessingPath path = new ProcessingPath();
        path.setControllerUnit(controllerUnit);
        model.addAttribute("path", path);

        List<TaskType> typesWithUnspecifiedPathFromContoller = controllerUnitService.getTypesWithUnspecifiedPathFromContoller(controllerUnit);
        model.addAttribute("unspecifiedTypes", typesWithUnspecifiedPathFromContoller);

        if (typesWithUnspecifiedPathFromContoller.isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to create a new Processing Path from the Controller Unit - all paths have already been created");
            return "redirect:/controllerunits/" + controllerId;
        }

        List<ControllerUnit> controllers = controllerUnitService.getControllersByModel(controllerUnit.getModel());
        model.addAttribute("controllers", controllers);
        return "processingpaths/new";
        
    }

    /**
     *
     * @param controllerId id controller
     * @param path path from controller
     * @param redirectAttributes is used for response messages about method action
     * @return redirect to controller units site
     */
    @RequestMapping(value = "/processingpaths/new/{controllerId}", method = RequestMethod.POST)
    public String addProcessingPath(@PathVariable("controllerId") long controllerId, @ModelAttribute(value = "path") ProcessingPath path, RedirectAttributes redirectAttributes) {
        if (processingPathService.add(path)) {
            redirectAttributes.addFlashAttribute("msg", "The Processing Path was added");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to add the Processing Path - selected Controller Units might be assigned to a different Models");
        }
        return "redirect:/controllerunits/" + controllerId;

    }

    /**
     *
     * @param controllerId
     * @param pathId id path from controller
     * @param redirectAttributes is used for response messages about method action
     * @return redirect to controller units site
     */
    @RequestMapping(value = "/processingpaths/remove/{controllerId}/{pathId}", method = RequestMethod.POST)
    public String deleteProcessingPath(@PathVariable("controllerId") long controllerId, @PathVariable("pathId") long pathId, RedirectAttributes redirectAttributes) {

        ProcessingPath path = processingPathService.find(pathId);

        if ((path != null) && processingPathService.remove(path)) {
            redirectAttributes.addFlashAttribute("msg", "The Processing Path was removed");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to remove the Processing Path");
        }
        
        return "redirect:/controllerunits/" + controllerId;
    }

}
