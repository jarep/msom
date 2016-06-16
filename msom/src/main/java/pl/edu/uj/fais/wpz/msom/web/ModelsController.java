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
import pl.edu.uj.fais.wpz.msom.entities.Distribution;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskProbability;
import pl.edu.uj.fais.wpz.msom.service.interfaces.DistributionService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskProbabilityService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
@Controller
public class ModelsController {

    @Autowired
    ModelService modelService;

    @Autowired
    TaskProbabilityService probabilityService;

    @Autowired
    DistributionService distributionService;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskProbabilityService taskProbabilityService;

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
    public String addModel(@ModelAttribute(value = "model") pl.edu.uj.fais.wpz.msom.entities.Model m, RedirectAttributes redirectAttributes) {
        if (modelService.add(m)) {
            redirectAttributes.addFlashAttribute("msg", "The Model was added");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to add the Model");
        }
        return "redirect:/models";
    }

    @RequestMapping(value = "/models/remove/{id}", method = RequestMethod.POST)
    public String deleteModel(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        pl.edu.uj.fais.wpz.msom.entities.Model m = modelService.find(id);
        if ((m != null) && modelService.remove(m)) {
            redirectAttributes.addFlashAttribute("msg", "The Model was removed");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to remove the Model");
        }

        return "redirect:/models";
    }

    @RequestMapping(value = "/models/remove-first-task-dispatcher/{id}", method = RequestMethod.POST)
    public String removeFirstTaskDispatcher(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        pl.edu.uj.fais.wpz.msom.entities.Model m = modelService.find(id);
        if ((m != null) && modelService.detachFirstTaskDispatcher(m)) {
            redirectAttributes.addFlashAttribute("msg", "First task dispatcher was detached");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to detach first task dispatcher");
        }

        return "redirect:/models";
    }

    @RequestMapping(value = "/models/{id}", method = RequestMethod.GET)
    public String getModel(@PathVariable("id") long id, Model model) {
        pl.edu.uj.fais.wpz.msom.entities.Model m = modelService.find(id);
        if (m == null) {
            throw new NullPointerException();
        }
        model.addAttribute("model", m);

        List<TaskProbability> taskProbabilities = probabilityService.getTaskProbabilitiesByModel(m);
        model.addAttribute("taskProbabilitiesList", taskProbabilities);
        return "models/view";
    }

    @RequestMapping(value = "/models/update", method = RequestMethod.POST)
    public String updateModel(@ModelAttribute(value = "model") pl.edu.uj.fais.wpz.msom.entities.Model m, Model model, RedirectAttributes redirectAttributes) {
        if (modelService.update(m)) {
            redirectAttributes.addFlashAttribute("msg", "The Model was updated");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to update the Model");
        }
        return "redirect:/models";
    }

    @RequestMapping(value = "/models/addprobability/{modelId}", method = RequestMethod.GET)
    public String addProbability(@PathVariable("modelId") long modelId, Model model, RedirectAttributes redirectAttributes) {
        pl.edu.uj.fais.wpz.msom.entities.Model m = modelService.find(modelId);
        if (m == null) {
            throw new NullPointerException();
        }
        model.addAttribute("model", m);

        TaskProbability taskProbability = new TaskProbability();
        taskProbability.setModel(m);
        model.addAttribute("taskProbability", taskProbability);

        List<Distribution> distributions = distributionService.findAll();
        model.addAttribute("distributions", distributions);

        List<Task> tasksWitchUnspecifiedDistributionForModel = taskProbabilityService.getTasksWithUnspecifiedProbabilityForModel(m);
        model.addAttribute("tasks", tasksWitchUnspecifiedDistributionForModel);

        if (tasksWitchUnspecifiedDistributionForModel.isEmpty()) {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to create a new Task Probability - all probabilities have already been created");
            return "redirect:/models/" + modelId;
        }

        return "models/addprobability";
    }

    @RequestMapping(value = "/models/addprobability/{modelId}", method = RequestMethod.POST)
    public String addProbability(@PathVariable("modelId") long modelId, @ModelAttribute(value = "taskProbability") TaskProbability taskProbability, RedirectAttributes redirectAttributes) {
        if (taskProbabilityService.add(taskProbability)) {
            redirectAttributes.addFlashAttribute("msg", "The Task Probability was added");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to add the Task Probability.");
        }
        return "redirect:/models/" + modelId;
    }

    @RequestMapping(value = "/models/editprobability/{probabilityId}", method = RequestMethod.GET)
    public String editProbability(@PathVariable("probabilityId") long probabilityId, Model model, RedirectAttributes redirectAttributes) {
        TaskProbability taskProbability = taskProbabilityService.find(probabilityId);
        if (taskProbability == null) {
            throw new NullPointerException();
        }
        model.addAttribute("taskProbability", taskProbability);
        model.addAttribute("model", taskProbability.getModel());
        model.addAttribute("task", taskProbability.getTask());
        
        List<Distribution> distributions = distributionService.findAll();
        model.addAttribute("distributions", distributions);

        return "models/editprobability";
    }
    
    @RequestMapping(value = "/models/editprobability/update", method = RequestMethod.POST)
    public String updateProbability(@ModelAttribute(value = "probability") TaskProbability probability, Model model, RedirectAttributes redirectAttributes) {
        if(taskProbabilityService.update(probability)) {
            redirectAttributes.addFlashAttribute("msg", "The Task Probability was updated");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to update the Task Probability");
        }
        return "redirect:/models/" + probability.getModel().getId();
    }

    @RequestMapping(value = "/models/removeprobability/{modelId}/{probabilityId}", method = RequestMethod.POST)
    public String deleteTaskProbability(@PathVariable("modelId") long modelId, @PathVariable("probabilityId") long probabilityId, RedirectAttributes redirectAttributes) {
        TaskProbability taskProbability = taskProbabilityService.find(probabilityId);
        if ((taskProbability != null) && taskProbabilityService.remove(taskProbability)) {
            redirectAttributes.addFlashAttribute("msg", "The Task Probability was removed");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to remove the Task Probability");
        }
        return "redirect:/models/" + modelId;
    }

}
