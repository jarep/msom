/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao.interfaces;

import java.util.List;

/**
 * Common DAO interface
 *
 * @author jaroslaw
 * @param <E>
 * @param <K>
 */
public interface IDao<E, K> {

    /**
     * Find entity in database by key
     *
     * @param key
     * @return
     */
    public E find(K key);

    /**
     * Save entity in database
     * 
     * @param entity to save
     */
    public void add(E entity);

    /**
     * Save or update entity in database
     *
     * @param entity Entity to update
     */
    public void update(E entity);

    /**
     * Find all entities in database
     *
     * @return List of all entities from database
     */
    public List<E> findAll();

    /**
     * Remove entity from database
     *
     * @param entity Entity to delete
     */
    public void remove(E entity);

    /**
     * Gets the current data from database and refresh local entity
     *
     * @param entity Entity to refresh
     */
    public void refresh(E entity);

}
