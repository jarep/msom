/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import pl.edu.uj.fais.wpz.msom.web.helpers.TaskTypesWrapper;

/**
 *
 * @author paweldylag
 */
@Controller
public class ModulesController {

    @Autowired
    private ModuleService moduleService;
    
    @Autowired
    private TaskTypeService taskTypeService;
    
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
        
        List<TaskType> taskTypes = taskTypeService.findAll();
        Map<Long, String> taskTypesMap = new HashMap<>();
        for (TaskType t : taskTypes) {
            taskTypesMap.put(t.getId(), t.getName());
        }
        Set<TaskType> modulesTaskTypes = module.getTaskTypes();
        List<Long> checkedTaskTypes = new ArrayList<>();
        for (TaskType t : modulesTaskTypes) {
            checkedTaskTypes.add(t.getId());
        }
        TaskTypesWrapper wCheckedTaskTypes = new TaskTypesWrapper(checkedTaskTypes);
        
        model.addAttribute("taskTypes", taskTypesMap);
        model.addAttribute("checkedTaskTypes", wCheckedTaskTypes);
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
        module.setTaskTypes(moduleService.find(module.getId()).getTaskTypes());
        moduleService.update(module);
        return "redirect:/modules";
    }
    
    @RequestMapping(value = "/modules/{id}/updatetasktypes", method = RequestMethod.POST)
    public String updateTaskTypesInModule(@PathVariable("id") long id, @ModelAttribute(value = "checkedTaskTypes") TaskTypesWrapper checkedTaskTypes) {

        Module module = moduleService.find(id);
        Set<TaskType> taskTypes = new HashSet<>();
        List<Long> ids = checkedTaskTypes.getIds();
        for (Long i : ids) {
            TaskType t = taskTypeService.find(i);
            taskTypes.add(t);
        }
        module.setTaskTypes(taskTypes);
        moduleService.update(module);
        return "redirect:/modules";
    }

}
