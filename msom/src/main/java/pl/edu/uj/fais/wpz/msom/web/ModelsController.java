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
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;

/**
 *
 * @author jarep
 */
@Controller
public class ModelsController {

    @Autowired
    ModelService modelService;

    @RequestMapping(value = "/models", method = RequestMethod.GET)
    public String showAllModels(Model model) {
        List<pl.edu.uj.fais.wpz.msom.entities.Model> models = modelService.findAll();
        model.addAttribute("modelsList", models);

        return "models/list";
    }

    @RequestMapping(value = "/models/new", method = RequestMethod.GET)
    public String createModel(Model model) {
        model.addAttribute("model", new pl.edu.uj.fais.wpz.msom.entities.Model());

        return "models/new";
    }

    @RequestMapping(value = "/models/new", method = RequestMethod.POST)
    public String addModel(@ModelAttribute(value = "model") pl.edu.uj.fais.wpz.msom.entities.Model m) {
        modelService.add(m);
        return "redirect:/models";
    }

    @RequestMapping(value = "/models/remove/{id}", method = RequestMethod.POST)
    public String deleteModel(@PathVariable("id") long id, Model model) {
        pl.edu.uj.fais.wpz.msom.entities.Model m = modelService.find(id);
        boolean wasDeleted = modelService.remove(m);

        if (!wasDeleted) {
            model.addAttribute("errorMsg", "Can not remove this Model...");
            return "errorpage";
        }

        return "redirect:/models";
    }

    @RequestMapping(value = "/models/{id}", method = RequestMethod.GET)
    public String getModel(@PathVariable("id") long id, Model model) {
        pl.edu.uj.fais.wpz.msom.entities.Model m = modelService.find(id);
        model.addAttribute("model", m);
        return "models/view";
    }

    @RequestMapping(value = "/models/update", method = RequestMethod.POST)
    public String updateModel(@ModelAttribute(value = "model") pl.edu.uj.fais.wpz.msom.entities.Model m, Model model) {
        if (!modelService.update(m)) {
            model.addAttribute("errorMsg", "Can not change this Model... ");
            return "errorpage";
        }
        return "redirect:/models";
    }

}
