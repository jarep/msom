/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;
import pl.edu.uj.fais.wpz.msom.service.interfaces.IService;

/**
 *
 * @author jarep
 * @param <T>
 */
public abstract class AbstractService<T extends AbstractEntity> implements IService<T> {

    /**
     *
     * @return Data acces object
     */
    public abstract IDao getDao();

    @Override
    public T find(Long id) {
        return (T) getDao().find(id);
    }

    @Override
    public boolean update(T entity) {
        getDao().update(entity);
        return true;
    }

    @Override
    public List<T> findAll() {
        return (List<T>) getDao().findAll();
    }

    @Override
    public boolean remove(T entity) {
        getDao().remove(entity);
        return true;
    }

    @Override
    public void refresh(T entity) {
        getDao().refresh(entity);
    }

    @Override
    public boolean add(T entity) {
        getDao().add(entity);
        return true;
    }

}
