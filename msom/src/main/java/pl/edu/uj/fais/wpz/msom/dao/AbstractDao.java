/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;

/**
 * Basic DAO operations dependent with Hibernate's specific classes
 *
 * @param <E>
 * @param <K>
 * @see SessionFactory
 * @author jaroslaw
 */
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public abstract class AbstractDao<E, K extends Serializable> implements IDao<E, K> {

    private SessionFactory sessionFactory;

    /**
     * Data access object type, used by hybernate
     */
    protected Class<? extends E> daoType;

    /**
     * sets data access object type, used by hybernate
     */
    public AbstractDao() {
        daoType = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

 

    /**
     * In this specific case we will use setter injection,
     * because we’re designing this class for extension. 
     * If we’d pick constructor injection, all extending classes would have to have
     constructor matching one from the superclass. 
     * @param sessionFactory used by hybernate
     */

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * @return current data access session
     */
    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(E entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(E entity) {
        getCurrentSession().merge(entity);
    }

    @Override
    public boolean remove(E entity) {
        getCurrentSession().delete(entity);
        return true;
    }

    @Override
    public E find(K key) {
        return (E) getCurrentSession().get(daoType, key);
    }

    @Override
    public List<E> findAll() {
        return getCurrentSession().createCriteria(daoType).list();
    }

    @Override
    public void refresh(E entity) {
        getCurrentSession().refresh(entity);
    }

}
