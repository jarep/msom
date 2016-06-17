/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jaroslaw
 */
public interface TaskDao extends IDao<Task, Long> {

    /**
     * Tries to remove task from the system.
     *
     * @param task Task to remove
     * @return {@code true} if task is not assigned to any other entities, else
     * {@code false}.
     */
    boolean removeTask(Task task);

    /**
     * Get all tasks associated with given type
     *
     * @param taskType Type of task (entity object)
     * @return list of tasks (entity objects)
     */
    public List<Task> getTasksByType(TaskType taskType);

    /**
     * Get all tasks associated with given type
     *
     * @param taskTypeId Type of task id
     * @return list of tasks (entity objects)
     */
    public List<Task> getTasksByTaskTypeId(Long taskTypeId);

}
