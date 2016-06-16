/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskProbability;

/**
 *
 * @author jarep
 */
public interface TaskProbabilityService extends IService<TaskProbability> {

    /**
     * Get all task probabilities assigned to given model
     *
     * @param model
     * @return
     */
    public List<TaskProbability> getTaskProbabilitiesByModel(Model model);

    /**
     * Get Tasks which are have not specified Probability for given Model
     *
     * @param model
     * @return List of founded tasks
     */
    public List<Task> getTasksWithUnspecifiedProbabilityForModel(Model model);

    /**
     * Get Tasks which are have specified Probability for given Model
     *
     * @param model
     * @return List of founded tasks
     */
    public List<Task> getTasksWithSpecifiedProbabilityForModel(Model model);
}
