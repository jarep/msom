/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskProbabilityDao;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskProbability;

/**
 *
 * @author jarep
 */
@Repository(value = "taskProbabilityDao")
public class TaskProbabilityDaoImpl extends AbstractDao<TaskProbability, Long> implements TaskProbabilityDao {

    @Override
    public List<TaskProbability> findAll() {
        return getCurrentSession().createCriteria(daoType).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public List<TaskProbability> getTaskProbabilitiesByModel(Model model) {
        Query taskProbabilitiesByModelQuery = getCurrentSession().createQuery(
                "SELECT p"
                + " FROM TaskProbability AS p"
                + " JOIN p.model AS model"
                + " WHERE model.id = :id");
        taskProbabilitiesByModelQuery.setParameter("id", model.getId());

        return taskProbabilitiesByModelQuery.list();
    }

    @Override
    public List<Task> getTasksWithSpecifiedProbabilityForModel(Model model) {
        Query tasksWithUnspecifiedProbabilityForModelQuery = getCurrentSession().createQuery(
                "SELECT task"
                + " FROM TaskProbability AS p"
                + " JOIN p.task AS task"
                + " JOIN p.model AS model"
                + " WHERE model.id = :id");
        tasksWithUnspecifiedProbabilityForModelQuery.setParameter("id", model.getId());
        
        return tasksWithUnspecifiedProbabilityForModelQuery.list();
    }
}
