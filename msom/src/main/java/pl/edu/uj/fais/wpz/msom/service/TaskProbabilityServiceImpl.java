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
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskProbabilityDao;
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

    public TaskProbabilityDao getTaskProbabilityDao() {
        return taskProbabilityDao;
    }

    @Override
    public IDao getDao() {
        return getTaskProbabilityDao();
    }

}
