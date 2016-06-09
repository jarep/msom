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
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;

/**
 *
 * @author jarep
 */
@Controller
public class ControllerUnitsController {

    @Autowired
    ControllerUnitService controllerUnitService;

    @Autowired
    ProcessingPathService processingPathService;

    @Autowired
    ModelService modelService;

    @RequestMapping(value = "/controllerunits", method = RequestMethod.GET)
    public String showAllControllerUnits(Model model) {
        List<ControllerUnit> controllers = controllerUnitService.findAll();
        model.addAttribute("controllersList", controllers);

        return "controllerunits/list";
    }

    @RequestMapping(value = "/controllerunits/{id}", method = RequestMethod.GET)
    public String getControllerUnit(@PathVariable("id") long id, Model model) {
        ControllerUnit controller = controllerUnitService.find(id);
        List<ProcessingPath> pathsFromController = processingPathService.getProcessingPathsComingOutFromTheControllerUnit(controller);
        List<ProcessingPath> pathsToController = processingPathService.getProcessingPathsLeadingToTheControllerUnit(controller);

        model.addAttribute("controller", controller);

        List<pl.edu.uj.fais.wpz.msom.entities.Model> models = modelService.findAll();
        model.addAttribute("modelsList", models);

        model.addAttribute("pathsFromControllerList", pathsFromController);
        model.addAttribute("pathsToControllerList", pathsToController);

        return "controllerunits/view";
    }

    @RequestMapping(value = "/controllerunits/new", method = RequestMethod.GET)
    public String createControllerUnit(Model model) {
        model.addAttribute("controller", new ControllerUnit());

        List<pl.edu.uj.fais.wpz.msom.entities.Model> models = modelService.findAll();
        model.addAttribute("modelsList", models);

        return "controllerunits/new";
    }

    @RequestMapping(value = "/controllerunits/new", method = RequestMethod.POST)
    public String addControllerUnit(@ModelAttribute(value = "controller") ControllerUnit controller, RedirectAttributes redirectAttributes) {
        if (controllerUnitService.add(controller)) {
            redirectAttributes.addFlashAttribute("msg", "The Controller Unit was added");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to add the Controller Unit");
        }
        return "redirect:/controllerunits";
    }

    @RequestMapping(value = "/controllerunits/remove/{id}", method = RequestMethod.POST)
    public String deleteControllerUnit(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

        ControllerUnit controller = controllerUnitService.find(id);

        if ((controller != null) && controllerUnitService.remove(controller)) {
            redirectAttributes.addFlashAttribute("msg", "The Controller Unit was removed");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to remove the Controller Unit");
        }
        
        return "redirect:/controllerunits";
    }

    @RequestMapping(value = "/controllerunits/update", method = RequestMethod.POST)
    public String updateControllerUnit(@ModelAttribute(value = "controller") ControllerUnit controller, RedirectAttributes redirectAttributes) {
        if (controllerUnitService.update(controller)) {
            redirectAttributes.addFlashAttribute("msg", "The Controller Unit was udpated");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to update the Controller Unit");
        }
        return "redirect:/controllerunits";
    }

}