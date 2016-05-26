/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskDao;
import pl.edu.uj.fais.wpz.msom.entities.Task;

/**
 *
 * @author jaroslaw
 */
@Repository(value = "taskDao")
public class TaskDaoImpl extends AbstractDao<Task, Long> implements TaskDao {

    @Override
    public boolean removeTask(Task task) {
        remove(task);
        return true;
    }

}
