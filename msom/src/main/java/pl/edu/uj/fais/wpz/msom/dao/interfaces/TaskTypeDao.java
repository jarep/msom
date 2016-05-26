/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao.interfaces;

import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jaroslaw
 */
public interface TaskTypeDao extends IDao<TaskType, Long> {

     /**
     * Tries to remove typeTask from the system.
     *
     * @param taskType Type of task to remove
     * @return {@code true} if taskType is not assigned to any task, else
     * {@code false}.
     */
    @Override
    boolean remove(TaskType taskType);
    
    
}
