/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskDao;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jaroslaw
 */
@Service(value = "taskService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TaskServiceImpl extends AbstractService<Task> implements TaskService {

    @Autowired
    private TaskDao taskDao;

    /**
     *
     * @return task data acces object
     */
    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Override
    public IDao getDao() {
        return getTaskDao();
    }

}
