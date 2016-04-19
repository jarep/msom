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
public class TaskTypeDaoImpl extends HibernateDao<TaskType, Long> implements TaskTypeDao {

    @Override
    public boolean removeTaskType(TaskType taskType) {
        Query tasksByTaskTypeQuery = getCurrentSession().createQuery(
                "SELECT t FROM Task AS t "
                + "JOIN t.taskType AS type "
                + "WHERE type.id = :id");
        Long taskTypeId = taskType.getId();
        tasksByTaskTypeQuery.setParameter("id", taskTypeId);

        // type mustn't be assigned on no task
        List list = tasksByTaskTypeQuery.list();
        if (!list.isEmpty()) {
            return false;
        }

        // ok, remove as usual
        remove(taskType);
        return true;

    }

}
