/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service.interfaces;

import pl.edu.uj.fais.wpz.msom.entities.Model;

/**
 *
 * @author jarep
 */
public interface ModelService extends IService<Model> {

    /**
     * Tries to remove Model from the system.
     *
     * @param model Model to remove
     * @return {@code true} if Model is not assigned to any Controller Unit,
     * else {@code false}.
     */
    @Override
    public boolean remove(Model model);
    
    /**
     * Set firstTaskDispatcher as null for given model.
     * @param model
     * @return 
     */
    public boolean detachFirstTaskDispatcher(Model model);
}
