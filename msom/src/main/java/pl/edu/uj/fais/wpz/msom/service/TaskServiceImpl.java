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
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
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

    public TaskDao getTaskDao() {
        return taskDao;
    }

    @Override
    public IDao getDao() {
        return getTaskDao();
    }

    @Override
    public List<Task> getTasksByType(TaskType taskType) {
        return taskDao.getTasksByType(taskType);
    }

    @Override
    public List<Task> getTasksByTypes(List<TaskType> taskTypes) {
        List<Task> result = new ArrayList<>();
        for (TaskType taskType : taskTypes) {
            result.addAll(getTasksByType(taskType));
        }
        return result;
    }

    @Override
    public List<Task> getTasksByTaskTypeId(Long taskTypeId) {
        return taskDao.getTasksByTaskTypeId(taskTypeId);
    }

    @Override
    public List<Task> getTasksByTaskTypeIds(List<Long> taskTypeIds) {
        List<Task> result = new ArrayList<>();
        for (Long id : taskTypeIds) {
            result.addAll(getTasksByTaskTypeId(id));
        }
        return result;
    }

    @Override
    public List<Task> getTasksByModelId(Long modelId) {
        return taskDao.getTasksByModelId(modelId);
    }

}
