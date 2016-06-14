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
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;

/**
 * Provides CRUD for models of processing system controller
 * @author jarep
 */
@Controller
public class ModelsController {

    @Autowired
    ModelService modelService;

    /**
     * 
     * @param model to use in method
     * @return  path to view with all models
     */
    @RequestMapping(value = "/models", method = RequestMethod.GET)
    public String showAllModels(Model model) {
        List<pl.edu.uj.fais.wpz.msom.entities.Model> models = modelService.findAll();
        model.addAttribute("modelsList", models);

        return "models/list";
    }

    /**
     * Create new model
     * @param model to use in method
     * @return  path to view with new model
     */
    @RequestMapping(value = "/models/new", method = RequestMethod.GET)
    public String createModel(Model model) {
        model.addAttribute("model", new pl.edu.uj.fais.wpz.msom.entities.Model());

        return "models/new";
    }

    /**
     *
     * @param m
     * @param redirectAttributes is used for response messages about method action
     * @return redirect to view with  to view models
     */
    @RequestMapping(value = "/models/new", method = RequestMethod.POST)
    public String addModel(@ModelAttribute(value = "model") pl.edu.uj.fais.wpz.msom.entities.Model m, RedirectAttributes redirectAttributes) {
        if (modelService.add(m)) {
            redirectAttributes.addFlashAttribute("msg", "The Model was added");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to add the Model");
        }
        return "redirect:/models";
    }

    /**
     *
     * @param id
     * @param redirectAttributes is used for response messages about method action
     * @return path to view models
     */
    @RequestMapping(value = "/models/remove/{id}", method = RequestMethod.POST)
    public String deleteModel(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        pl.edu.uj.fais.wpz.msom.entities.Model m = modelService.find(id);
        if ((m != null) && modelService.remove(m))  {
            redirectAttributes.addFlashAttribute("msg", "The Model was removed");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to remove the Model");
        }

        return "redirect:/models";
    }

    /**
     *
     * @param id
     * @param model to use in method
     * @return  path to view models view
     */
    @RequestMapping(value = "/models/{id}", method = RequestMethod.GET)
    public String getModel(@PathVariable("id") long id, Model model) {
        pl.edu.uj.fais.wpz.msom.entities.Model m = modelService.find(id);
        if (m == null) throw new NullPointerException();
        model.addAttribute("model", m);
        return "models/view";
    }

    /**
     *
     * @param m
     * @param model to use in method
     * @param redirectAttributes is used for response messages about method action
     * @return  path to view models
     */
    @RequestMapping(value = "/models/update", method = RequestMethod.POST)
    public String updateModel(@ModelAttribute(value = "model") pl.edu.uj.fais.wpz.msom.entities.Model m, Model model, RedirectAttributes redirectAttributes) {
        if (modelService.update(m)) {
            redirectAttributes.addFlashAttribute("msg", "The Model was updated");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to update the Model");
        }
        return "redirect:/models";
    }

}
