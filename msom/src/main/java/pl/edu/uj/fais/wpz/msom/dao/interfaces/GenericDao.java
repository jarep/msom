/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao.interfaces;

import java.util.List;

/**
 *
 * @author jaroslaw
 * @param <E>
 * @param <K>
 */
public interface GenericDao<E, K> {
    
    void add(E entity);
    
    void update(E entity);
    
    void remove(E entity);
    
    E find(K key);
    
    List<E> list();
    
}
