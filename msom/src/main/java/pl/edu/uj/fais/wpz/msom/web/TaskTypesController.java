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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskTypeDao;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jaroslaw
 */
@Controller
public class TaskTypesController {

    @Autowired
    private TaskTypeDao taskTypeDao;

    @Autowired
    private TaskTypeService taskTypeService;

    @RequestMapping(value = "/tasktypes", method = RequestMethod.GET)
    public String showTaskTypes(Model model) {
        List<TaskType> taskTypes = taskTypeService.getTypesList();
        model.addAttribute("taskTypesList", taskTypes);

        return "tasktypes/list";
    }

    /**
     * Deletes taskType with specified ID
     *
     * @param id taskType's ID
     * @return redirects to tasktypes if everything was ok
     */
    @RequestMapping(value = "/tasktypes/remove/{id}", method = RequestMethod.POST)
    public String deleteTaskType(@PathVariable("id") long id) {

        TaskType toDelete = taskTypeDao.find(id);
        boolean wasDeleted = taskTypeService.removeTaskType(toDelete);

        if (!wasDeleted) {
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
    public String addTaskType(TaskType taskType) {
        taskTypeDao.add(taskType);

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
        TaskType taskType = taskTypeDao.find(id);
        model.addAttribute("taskType", taskType);

        return "tasktypes/view";
    }

    /**
     * Updates taskType with specified ID
     *
     * @param id taskType's ID
     * @param taskType Type of task to update (bounded from HTML form)
     * @return redirects to taskType
     */
    @RequestMapping(value = "/tasktypes/{id}", method = RequestMethod.POST)
    public String updateTaskType(@PathVariable("id") long id, TaskType taskType) {
        taskType.setId(id);
        taskTypeDao.update(taskType);

        return "redirect:/tasktypes";
    }

}
