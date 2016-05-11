/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao.interfaces;

import pl.edu.uj.fais.wpz.msom.entities.Model;

/**
 *
 * @author jarep
 */
public interface ModelDao extends IDao<Model, Long> {

    /**
     * Tries to remove model from the data base.
     *
     * @param model Model to remove
     * @return {@code true} if model is not assigned to any controller unit,
     * else {@code false}.
     */
    public boolean removeModel(Model model);

}
