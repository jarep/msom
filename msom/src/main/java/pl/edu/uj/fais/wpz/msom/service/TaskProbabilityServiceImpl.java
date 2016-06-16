/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskProbabilityDao;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskProbability;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskProbabilityService;

/**
 *
 * @author jarep
 */
@Service(value = "taskProbabilityService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TaskProbabilityServiceImpl extends AbstractService<TaskProbability> implements TaskProbabilityService {

    @Autowired
    private TaskProbabilityDao taskProbabilityDao;
    
    @Autowired
    private TaskDao taskDao;

    public TaskProbabilityDao getTaskProbabilityDao() {
        return taskProbabilityDao;
    }

    @Override
    public IDao getDao() {
        return getTaskProbabilityDao();
    }

    @Override
    public List<TaskProbability> getTaskProbabilitiesByModel(Model model) {
        return taskProbabilityDao.getTaskProbabilitiesByModel(model);
    }

    @Override
    public List<Task> getTasksWithSpecifiedProbabilityForModel(Model model) {
        return taskProbabilityDao.getTasksWithSpecifiedProbabilityForModel(model);
    }

    @Override
    public List<Task> getTasksWithUnspecifiedProbabilityForModel(Model model) {
        List<Task> tasks = taskDao.findAll();
        List<Task> tasksWithSpecifiedProbabilityForModel = getTasksWithSpecifiedProbabilityForModel(model);
        tasks.removeAll(tasksWithSpecifiedProbabilityForModel);
        return tasks;
    }

}
