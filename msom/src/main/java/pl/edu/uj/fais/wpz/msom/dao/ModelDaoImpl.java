/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModelDao;
import pl.edu.uj.fais.wpz.msom.entities.Model;

/**
 *
 * @author jarep
 */
@Repository(value = "modelDao")
public class ModelDaoImpl extends AbstractDao<Model, Long> implements ModelDao {

    @Override
    public boolean remove(Model model) {
        if (isReferenced(model)) {
            return false;
        }
        getCurrentSession().delete(model);
        return true;
    }

    private boolean isReferenced(Model model) {
        Query controllersByModel = getCurrentSession().createQuery(
                "SELECT controller "
                + " FROM ControllerUnit AS controller"
                + " JOIN controller.model AS model"
                + " WHERE model.id = :id");
        Long modelId = model.getId();
        controllersByModel.setParameter("id", modelId);
        controllersByModel.setMaxResults(1);

        List list = controllersByModel.list();
        return !list.isEmpty();
    }

    @Override
    public List<Model> findAll() {
        return getCurrentSession().createCriteria(daoType).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

    @Override
    public boolean detachFirstTaskDispatcher(Model model) {
        Query detachQuery = getCurrentSession().createQuery(
                "UPDATE Model AS m"
                + " SET firstcontrollerunit_id=null"
                + " WHERE m.id = :id");
        detachQuery.setParameter("id", model.getId());
        
        int executeUpdate = detachQuery.executeUpdate();
        return executeUpdate>0;
    }

}
