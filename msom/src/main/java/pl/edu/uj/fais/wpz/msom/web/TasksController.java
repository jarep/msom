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
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jaroslaw
 */
@Controller
public class TasksController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskTypeService taskTypeService;

    @RequestMapping(value = "/tasks", method = RequestMethod.GET)
    public String showTasks(Model model) {
        List<Task> tasks = taskService.findAll();
        model.addAttribute("tasksList", tasks);

        return "tasks/list";
    }

    @RequestMapping(value = "/tasks/remove/{id}", method = RequestMethod.POST)
    public String deleteTask(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {

        Task task = taskService.find(id);
        if ((task != null) && taskService.remove(task)) {
            redirectAttributes.addFlashAttribute("msg", "The Task was removed");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to remove the Task");
        }
        
        return "redirect:/tasks";
    }

    /**
     * Creates form for new task
     *
     * @param model Model to bind to HTML form
     * @return tasks/new
     */
    @RequestMapping(value = "/tasks/new", method = RequestMethod.GET)
    public String createTask(Model model) {
        model.addAttribute("task", new Task());

        List<TaskType> taskTypes = taskTypeService.findAll();
        model.addAttribute("taskTypesList", taskTypes);

        return "tasks/new";
    }

    /**
     * Saves new task to the database
     *
     * @param task
     * @param redirectAttributes
     * @param taskTypeId
     * @return redirects to tasks
     */
    @RequestMapping(value = "/tasks/new", method = RequestMethod.POST)
    public String addTask(@ModelAttribute(value = "task") Task task, RedirectAttributes redirectAttributes) {
        if (taskService.add(task)) {
            redirectAttributes.addFlashAttribute("msg", "The Task was added");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to add the Task");
        }
        return "redirect:/tasks";
    }

    /**
     * Returns task with specified ID
     *
     * @param id task's ID
     * @param model Model to put taskType to
     * @return tasks/view
     */
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.GET)
    public String getTask(@PathVariable("id") long id, Model model) {
        Task task = taskService.find(id);

        List<TaskType> taskTypes = taskTypeService.findAll();
        model.addAttribute("taskTypesList", taskTypes);
        model.addAttribute("task", task);

        return "tasks/view";
    }

    /**
     * Updates task
     *
     * @param task Task to update (bounded from HTML form)
     * @param redirectAttributes
     * @return redirects to tasks
     */
    @RequestMapping(value = "/tasks/update", method = RequestMethod.POST)
    public String updateTask(@ModelAttribute(value = "task") Task task, RedirectAttributes redirectAttributes) {
        if (taskService.update(task)) {
            redirectAttributes.addFlashAttribute("msg", "The Task was updated");
        } else {
            redirectAttributes.addFlashAttribute("msg", "Error: Unable to update the Task");
        }
        return "redirect:/tasks";
    }

}
