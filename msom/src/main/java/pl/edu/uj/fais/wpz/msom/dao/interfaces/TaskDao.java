/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao.interfaces;

import pl.edu.uj.fais.wpz.msom.entities.Task;

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

}
