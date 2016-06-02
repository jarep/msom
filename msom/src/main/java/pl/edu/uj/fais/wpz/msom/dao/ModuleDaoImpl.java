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
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModuleDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Module;

/**
 *
 * @author paweldylag
 */
@Repository(value = "moduleDao")
public class ModuleDaoImpl extends AbstractDao<Module, Long> implements ModuleDao {

    @Override
    public List<Module> getModulesByControllerUnit(ControllerUnit controllerUnit) {
        Query modulesByControllerUnitQuery = getCurrentSession().createQuery(
                "SELECT m"
                + " FROM Module AS m"
                + " JOIN m.controllerUnit AS controller"
                + " WHERE controller.id = :id");
        modulesByControllerUnitQuery.setParameter("id", controllerUnit.getId());
        return modulesByControllerUnitQuery.list();
    }
    
    @Override
    public List<Module> findAll() {
        return getCurrentSession().createCriteria(daoType).
                setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY).list();
    }

}
