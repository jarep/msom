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
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author zajac
 */
@Controller
public class TaskManagmentController {
    
    
    
    @Autowired
    TaskService taskService;
    
    @RequestMapping(value = "/taskmanagment", method = RequestMethod.GET)
    public String showTaskTypes(Model model) {
        List<Task> tasks = taskService.getTasksList();
        model.addAttribute("tasksList", tasks);

        return "taskmanagment/list";
    }
        
    @RequestMapping(value = "/taskmanagment/remove/{id}", method = RequestMethod.POST)
    public String deleteTask(@PathVariable("id") long id) {

        Task toDelete = taskService.find(id);
        boolean wasDeleted = taskService.removeTask(toDelete);

        if (!wasDeleted) {
            // nie mozna usunac, 
            // należy obsłużyć wyjątek i przekazać odpowiedni komunikat użytkownikowi
        }

        // everything OK, see remaining task types
        return "redirect:/taskmanagment";
    }
}
