/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskTypeDao;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jaroslaw
 */
@Repository(value = "taskTypeDao")
public class TaskTypeDaoImpl extends AbstractDao<TaskType, Long> implements TaskTypeDao {

    @Override
    public boolean remove(TaskType taskType) {
        if (isUsed(taskType)) {
            return false;
        }

        // ok, remove as usual
        getCurrentSession().delete(taskType);
        return true;
    }

    private boolean isUsed(TaskType taskType) {
        return (isUsedByAnyTask(taskType) || isUsedByAnyModule(taskType) || isUsedByAnyProcessingPath(taskType));
    }

    private boolean isUsedByAnyTask(TaskType taskType) {
        Query tasksByTaskTypeQuery = getCurrentSession().createQuery(
                "SELECT t FROM Task AS t "
                + "JOIN t.taskType AS type "
                + "WHERE type.id = :id");
        Long taskTypeId = taskType.getId();
        tasksByTaskTypeQuery.setParameter("id", taskTypeId);
        tasksByTaskTypeQuery.setMaxResults(1);

        List list = tasksByTaskTypeQuery.list();
        return !list.isEmpty();
    }

    private boolean isUsedByAnyModule(TaskType taskType) {
        Query modulesByTaskTypeQuery = getCurrentSession().createQuery(
                "SELECT m"
                + " FROM Module AS m"
                + " JOIN m.taskTypes AS taskType"
                + " WHERE taskType.id = :id");
        Long taskTypeId = taskType.getId();
        modulesByTaskTypeQuery.setParameter("id", taskTypeId);
        modulesByTaskTypeQuery.setMaxResults(1);

        List list = modulesByTaskTypeQuery.list();
        return !list.isEmpty();
    }

    private boolean isUsedByAnyProcessingPath(TaskType taskType) {
        Query pathsByTaskTypeQuery = getCurrentSession().createQuery(
                "SELECT type"
                + " FROM ProcessingPath AS p"
                + " JOIN p.taskType AS type"
                + " WHERE type.id = :id");
        pathsByTaskTypeQuery.setParameter("id", taskType.getId());
        pathsByTaskTypeQuery.setMaxResults(1);

        List list = pathsByTaskTypeQuery.list();
        return !list.isEmpty();
    }

}
