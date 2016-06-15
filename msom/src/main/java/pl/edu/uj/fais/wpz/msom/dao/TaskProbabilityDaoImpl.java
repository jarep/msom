/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskProbabilityDao;
import pl.edu.uj.fais.wpz.msom.entities.TaskProbability;

/**
 *
 * @author jarep
 */
@Repository(value = "taskProbabilityDao")
public class TaskProbabilityDaoImpl extends AbstractDao<TaskProbability, Long> implements TaskProbabilityDao {
    
}
