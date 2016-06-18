/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskDao;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

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

    @Override
    public List<Task> getTasksByType(TaskType taskType) {
        Query tasksByTypeQuery = getCurrentSession().createQuery(
                "SELECT t"
                + " FROM Task AS t"
                + " JOIN t.taskType AS type"
                + " WHERE type.id = :id");
        tasksByTypeQuery.setParameter("id", taskType.getId());
        return tasksByTypeQuery.list();
    }

    @Override
    public List<Task> getTasksByTaskTypeId(Long taskTypeId) {
        Query tasksByTypeQuery = getCurrentSession().createQuery(
                "SELECT t"
                + " FROM Task AS t"
                + " JOIN t.taskType AS type"
                + " WHERE type.id = :id");
        tasksByTypeQuery.setParameter("id", taskTypeId);
        return tasksByTypeQuery.list();
    }

    @Override
    public List<Task> getTasksByModelId(Long modelId) {
        Query tasksByModelQuery = getCurrentSession().createQuery(
                "SELECT task"
                        + " FROM TaskProbability AS p"
                        + " JOIN p.task AS task"
                        + " JOIN p.model AS model"
                        + " WHERE model.id = :id");
        tasksByModelQuery.setParameter("id", modelId);
        return tasksByModelQuery.list();
    }

}
