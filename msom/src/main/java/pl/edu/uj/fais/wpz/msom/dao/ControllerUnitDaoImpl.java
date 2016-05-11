/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ControllerUnitDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jarep
 */
@Repository(value = "controllerUnitDao")
public class ControllerUnitDaoImpl extends AbstractDao<ControllerUnit, Long> implements ControllerUnitDao {

    @Override
    public boolean removeControllerUnit(ControllerUnit controllerUnit) {
        if (isReferenced(controllerUnit)) {
            return false;
        }
        remove(controllerUnit);
        return true;
    }

    private boolean isReferenced(ControllerUnit controllerUnit) {
        if (isReferencedToAnyModule(controllerUnit)) {
            return true;
        } else if (isReferencedToAnyProcessingPath(controllerUnit)) {
            return true;
        }
        return false;
    }

    private boolean isReferencedToAnyModule(ControllerUnit controllerUnit) {
        Query modulesByControllerUnitQuery = getCurrentSession().createQuery(
                "SELECT m FROM Module AS m "
                + "JOIN m.controllerUnit AS controller "
                + "WHERE controller.id = :id");
        Long controllerUnitId = controllerUnit.getId();
        modulesByControllerUnitQuery.setParameter("id", controllerUnitId);
//        modulesByControllerUnitQuery.setMaxResults(1);

        List list = modulesByControllerUnitQuery.list();
        return !list.isEmpty();
    }

    private boolean isReferencedToAnyProcessingPath(ControllerUnit controllerUnit) {
        Long controllerUnitId = controllerUnit.getId();

        Query pathsByControllerUnitQuery = getCurrentSession().createQuery(
                "SELECT p FROM ProcessingPath AS p "
                + "JOIN p.controllerUnit AS controller "
                + "WHERE controller.id = :id");
        pathsByControllerUnitQuery.setParameter("id", controllerUnitId);
//        pathsByControllerUnitQuery.setMaxResults(1);
//        Integer firstResult = pathsByControllerUnitQuery.getFirstResult();
        List pathsByController = pathsByControllerUnitQuery.list();

        Query pathsByNextControllerUnitQuery = getCurrentSession().createQuery(
                "SELECT p FROM ProcessingPath AS p "
                + "JOIN p.nextControllerUnit AS controller "
                + "WHERE controller.id = :id");
        pathsByNextControllerUnitQuery.setParameter("id", controllerUnitId);
//        pathsByNextControllerUnitQuery.setMaxResults(1);
//        Integer firstResult = pathsByNextControllerUnitQuery.getFirstResult();
        List pathsByNextController = pathsByNextControllerUnitQuery.list();

        return !pathsByController.isEmpty() || !pathsByNextController.isEmpty();
    }

    @Override
    public ControllerUnit getNextControllerUnit(ControllerUnit controllerUnit, TaskType taskType) {
        Query nextControllerUnits = getCurrentSession().createQuery(
                "SELECT controller"
                + " FROM ProcessingPath AS path"
                + " JOIN path.taskType AS type"
                + " JOIN path.nextControllerUnit AS nextController"
                + " JOIN path.controllerUnit AS controller"
                + " WHERE type.id = :typeId AND controller.id = :controllerId");
        nextControllerUnits.setParameter("typeId", taskType.getId());
        nextControllerUnits.setParameter("controllerId", controllerUnit.getId());

        try {
            ControllerUnit result = (ControllerUnit) nextControllerUnits.uniqueResult();
            return result;
        } catch (Exception e) {
            // should log exception
            return null;
        }
    }

    @Override
    public List<TaskType> getAvailableTaskTypesToProcessingInControllerUnit(ControllerUnit controllerUnit) {
        Query distinctTaskTypesAvailableToProcecessingQuery = getCurrentSession().createQuery(
                "SELECT DISTINCT type"
                + " FROM Module AS m"
                + " JOIN ControllerUnit AS controller"
                + " JOIN TaskType AS type"
                + " WHERE controller.id = :id");
        distinctTaskTypesAvailableToProcecessingQuery.setParameter("id", controllerUnit.getId());
        return distinctTaskTypesAvailableToProcecessingQuery.list();
    }

}
