/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jaroslaw
 */
@Controller
public class TaskTypesController {

    @Autowired
    private TaskTypeService taskTypeService;

    @RequestMapping(value = "/tasktypes", method = RequestMethod.GET)
    public String showTaskTypes(Model model) {
        List<TaskType> taskTypes = taskTypeService.findAll();
        model.addAttribute("taskTypesList", taskTypes);

        return "tasktypes/list";
    }

    /**
     * Deletes taskType with specified ID
     *
     * @param id taskType's ID
     * @param model
     * @return redirects to tasktypes if everything was ok
     */
    @RequestMapping(value = "/tasktypes/remove/{id}", method = RequestMethod.POST)
    public String deleteTaskType(@PathVariable("id") long id, Model model) {

        TaskType toDelete = taskTypeService.find(id);
        boolean wasDeleted = taskTypeService.remove(toDelete);

        if (!wasDeleted) {
             model.addAttribute("error", "CannotDelete");
            // nie mozna usunac, 
            // należy obsłużyć wyjątek i przekazać odpowiedni komunikat użytkownikowi
        }

        // everything OK, see remaining task types
        return "redirect:/tasktypes";
    }

    /**
     * Creates form for new taskType
     *
     * @param model Model to bind to HTML form
     * @return tasktypes/new
     */
    @RequestMapping(value = "/tasktypes/new", method = RequestMethod.GET)
    public String createTaskType(Model model) {
        model.addAttribute("taskType", new TaskType());
        return "tasktypes/new";
    }

    /**
     * Saves new taskType to the database
     *
     * @param taskType Type to save
     * @return redirects to tasktypes
     */
    @RequestMapping(value = "/tasktypes/new", method = RequestMethod.POST)
    public String addTaskType(@ModelAttribute(value = "taskType") TaskType taskType) {
        taskTypeService.add(taskType);

        return "redirect:/tasktypes";
    }

    /**
     * Returns taskType with specified ID
     *
     * @param id taskType's ID
     * @param model Model to put taskType to
     * @return tasktypes/view
     */
    @RequestMapping(value = "/tasktypes/{id}", method = RequestMethod.GET)
    public String getTaskType(@PathVariable("id") long id, Model model) {
        TaskType taskType = taskTypeService.find(id);
        model.addAttribute("taskType", taskType);

        return "tasktypes/view";
    }

    /**
     * Updates taskType
     *
     * @param taskType Type of task to update (bounded from HTML form)
     * @return redirects to taskType
     */
    @RequestMapping(value = "/tasktypes/update", method = RequestMethod.POST)
    public String updateTaskType(@ModelAttribute(value = "taskType") TaskType taskType) {
        taskTypeService.update(taskType);

        return "redirect:/tasktypes";
    }
}
