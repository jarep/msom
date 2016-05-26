/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author paweldylag
 */
@Controller
public class ModulesController {

    @Autowired
    private ModuleService moduleService;
   
    @RequestMapping(value = "/modules", method = RequestMethod.GET)
    public String showAllModules(Model model) {
        List<Module> modules = moduleService.findAll(); //lovely 
        HashSet<Module> modules2 = new HashSet<>(modules);
        model.addAttribute("moduleList", modules2);

        return "modules/list";
    }

    /**
     * Creates new form for module
     *
     * @param model
     * @return module/new
     */
    @RequestMapping(value = "/modules/new", method = RequestMethod.GET)
    public String createModule(Model model) {
        model.addAttribute("module", new Module());
        return "modules/new";
    }

    /**
     * Saves new module to database
     *
     * @param module
     * @return redirects to list
     */
    @RequestMapping(value = "/modules/new", method = RequestMethod.POST)
    public String addModule(@ModelAttribute(value = "module") Module module) {
        moduleService.add(module);
        return "redirect:/modules";
    }

    /**
     * Returns module with specified ID
     *
     * @param id module ID
     * @param model Model to put module to
     * @return module/view
     */
    @RequestMapping(value = "/modules/{id}", method = RequestMethod.GET)
    public String getModule(@PathVariable("id") long id, Model model) {
        Module module = moduleService.find(id);
        model.addAttribute("module", module);
        
        return "modules/view";
    }

    /**
     * Deletes module with specified ID
     *
     * @param id module's ID
     * @return redirects to modules if everything
     */
    @RequestMapping(value = "/modules/remove/{id}", method = RequestMethod.POST)
    public String deleteModule(@PathVariable("id") long id) {

        Module module = moduleService.find(id);
        boolean wasDeleted = moduleService.remove(module);

        if (!wasDeleted) {
            // exception to handle
        }

        // everything OK, see remaining modules
        return "redirect:/modules";
    }

    /**
     * Updates module
     *
     * @param module module to update (bounded from HTML form)
     * @return redirects to module
     */
    @RequestMapping(value = "/modules/update", method = RequestMethod.POST)
    public String updateModule(@ModelAttribute(value = "module") Module module) {
        moduleService.update(module);

        return "redirect:/modules";
    }

}
