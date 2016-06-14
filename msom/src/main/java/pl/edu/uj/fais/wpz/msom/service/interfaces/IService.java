/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 * Interface for service required by hibernate
 * @author jarep
 * @param <T>
 */
public interface IService<T extends AbstractEntity> {

    /**
     * Find entity in database by id
     *
     * @param id Id of entity in database
     * @return
     */
    public T find(Long id);

    /**
     * Add entity to database
     *
     * @param entity Entity to add
     * @return {@code true} if success, else {@code false}.
     */
    public boolean add(T entity);

    /**
     * Save or update entity in database
     *
     * @param entity Entity to update
     * @return {@code true} if success, else {@code false}.
     */
    public boolean update(T entity);

    /**
     * Find all entities
     *
     * @return List of all entities from database
     */
    public List<T> findAll();

    /**
     * Delete entity from database
     *
     * @param entity Entity to delete
     * @return {@code true} if success, else {@code false}.
     */
    public boolean remove(T entity);

    /**
     * Gets the current data from database and refresh local entity
     *
     * @param entity Entity to refresh
     */
    public void refresh(T entity);

}
