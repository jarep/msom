/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jarep
 */
public interface Type extends IModelObject<TaskType>, IActivatable {

    /**
     * Get name of type
     *
     * @return Name of type
     */
    public String getName();

    /**
     * Get difficulty value of type.
     *
     * @return Integer value represents difficulty
     */
    public int getDifficulty();

}
