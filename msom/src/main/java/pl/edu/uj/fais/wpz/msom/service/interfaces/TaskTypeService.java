/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service.interfaces;

import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jaroslaw
 */
public interface TaskTypeService extends IService<TaskType> {

    /**
     * Tries to remove typeTask from the system.
     *
     * @param taskType TaskType to remove
     * @return {@code true} if taskType is not assigned to any task, else
     * {@code false}.
     */
    @Override
    public boolean remove(TaskType taskType);

}
