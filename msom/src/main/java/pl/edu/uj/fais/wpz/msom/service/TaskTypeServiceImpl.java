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
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskTypeDao;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jaroslaw
 */
@Service(value = "taskTypeService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class TaskTypeServiceImpl extends AbstractService<TaskType> implements TaskTypeService {


    @Autowired
    private TaskTypeDao taskTypeDao;

    public TaskTypeDao getTaskTypeDao() {
        return taskTypeDao;
    }

    @Override
    public IDao getDao() {
        return getTaskTypeDao();
    }
    
    @Override
    public boolean remove(TaskType taskType){
        return taskTypeDao.remove(taskType);
    }


}
