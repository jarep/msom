/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jaroslaw
 */
public interface TaskService extends IService<Task> {

    /**
     * Get all tasks associated with given type
     *
     * @param taskType Type of task (entity object)
     * @return list of tasks (entity objects)
     */
    public List<Task> getTasksByType(TaskType taskType);

    /**
     * Get all tasks associated with given types
     *
     * @param taskTypes List of task types (entity objects)
     * @return list of tasks (entity objects)
     */
    public List<Task> getTasksByTypes(List<TaskType> taskTypes);

    /**
     * Get all tasks associated with given type
     *
     * @param taskTypeId Type of task id
     * @return list of tasks (entity objects)
     */
    public List<Task> getTasksByTaskTypeId(Long taskTypeId);

    /**
     * Get all tasks associated with given types
     *
     * @param taskTypeIds List of task type ids
     * @return list of tasks (entity objects)
     */
    public List<Task> getTasksByTaskTypeIds(List<Long> taskTypeIds);
}
