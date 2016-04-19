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
        List<TaskType> taskTypes = taskTypeService.getTypesList();
        model.addAttribute("taskTypesList", taskTypes);

        return "tasktypes/list";
    }

}
