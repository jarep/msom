/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.interfaces;

/**
 *
 * @author jarep
 * @param <Entity>
 */
public interface IModelObject<Entity> {

    /**
     * Return identity number for associated entity object
     *
     * @return Entity object ID
     */
    public Long getId();

    /**
     * Cancel changes and reload data from database if object is not active.
     *
     * @return {@code true} if reload, else {@code false}.
     */
    public boolean reload();

    /**
     * Save changes to database if object is not active.
     *
     * @return {@code true} if saved, else {@code false}.
     */
    public boolean save();

}
