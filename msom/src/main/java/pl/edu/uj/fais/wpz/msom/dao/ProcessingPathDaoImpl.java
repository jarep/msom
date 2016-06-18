/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.List;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ProcessingPathDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 *
 * @author jarep
 */
@Repository(value = "processingPathDao")
public class ProcessingPathDaoImpl extends AbstractDao<ProcessingPath, Long> implements ProcessingPathDao {

    @Override
    public List<ProcessingPath> getProcessingPathsByControllerUnit(ControllerUnit controllerUnit) {
        Query pathsByControllerQuery = getCurrentSession().createQuery(
                "SELECT path"
                + " FROM ProcessingPath AS path"
                + " JOIN path.nextControllerUnit AS nextController"
                + " JOIN path.controllerUnit AS controller"
                + " WHERE nextController.id = :id OR controller.id = :id");
        pathsByControllerQuery.setParameter("id", controllerUnit.getId());

        List paths = pathsByControllerQuery.list();
        return paths;
    }

    @Override
    public List<ProcessingPath> getProcessingPathsLeadingToTheControllerUnit(ControllerUnit nextControllerUnit) {
        return getProcessingPathsLeadingToTheControllerUnit(nextControllerUnit.getId());
    }

    @Override
    public List<ProcessingPath> getProcessingPathsLeadingToTheControllerUnit(Long controllerUnitId) {
        Query pathsByNextControllerQuery = getCurrentSession().createQuery(
                "SELECT path"
                + " FROM ProcessingPath AS path"
                + " JOIN path.nextControllerUnit AS nextController"
                + " WHERE nextController.id = :id");
        pathsByNextControllerQuery.setParameter("id", controllerUnitId);

        List paths = pathsByNextControllerQuery.list();
        return paths;
    }

    @Override
    public List<ProcessingPath> getProcessingPathsComingOutFromTheControllerUnit(ControllerUnit controllerUnit) {
        Query pathsByControllerQuery = getCurrentSession().createQuery(
                "SELECT path"
                + " FROM ProcessingPath AS path"
                + " JOIN path.controllerUnit AS controller"
                + " WHERE controller.id = :id");
        pathsByControllerQuery.setParameter("id", controllerUnit.getId());

        List paths = pathsByControllerQuery.list();
        return paths;
    }

    @Override
    public ProcessingPath getProcessingPathByTaskTypeAndControllerUnit(ControllerUnit controllerUnit, TaskType taskType) {
        Query pathsByControllerQuery = getCurrentSession().createQuery(
                "SELECT path"
                + " FROM ProcessingPath AS path"
                + " JOIN path.controllerUnit AS controller"
                + " JOIN path.taskType AS taskType"
                + " WHERE controller.id = :controllerId AND taskType.id = :typeId");
        pathsByControllerQuery.setParameter("controllerId", controllerUnit.getId());
        pathsByControllerQuery.setParameter("typeId", taskType.getId());

        try {
            ProcessingPath result = (ProcessingPath) pathsByControllerQuery.uniqueResult();
            return result;
        } catch (Exception e) {
            // should log exception
            return null;
        }
    }

    @Override
    public List<TaskType> getTaskTypesToProcessingByControllerUnit(ControllerUnit controllerUnit) {
        Query taskTypesToProcessingQuery = getCurrentSession().createQuery(
                "SELECT type"
                + " FROM ProcessingPath AS path"
                + " JOIN path.taskType AS type"
                + " JOIN path.controllerUnit AS controller"
                + " WHERE path.processing IS TRUE AND controller.id = :id");
        taskTypesToProcessingQuery.setParameter("id", controllerUnit.getId());

        List types = taskTypesToProcessingQuery.list();
        return types;
    }

    @Override
    public List<TaskType> getTaskTypesToFinishedByControllerUnit(ControllerUnit controllerUnit) {
        Query taskTypesToFinished = getCurrentSession().createQuery(
                "SELECT type"
                + " FROM ProcessingPath AS path"
                + " JOIN path.taskType AS type"
                + " JOIN path.nextControllerUnit AS nextController"
                + " JOIN path.controllerUnit AS controller"
                + " WHERE nextController.id = :id AND controller.id = :id");
        taskTypesToFinished.setParameter("id", controllerUnit.getId());

        List types = taskTypesToFinished.list();
        return types;
    }

    @Override
    public List<TaskType> getKnownTaskTypesByControllerUnit(ControllerUnit controllerUnit) {
        Query knownTaskTypesQuery = getCurrentSession().createQuery(
                "SELECT type"
                + " FROM ProcessingPath AS path"
                + " JOIN path.taskType AS type"
                + " JOIN path.controllerUnit AS controller"
                + " WHERE controller.id = :id");
        knownTaskTypesQuery.setParameter("id", controllerUnit.getId());

        List types = knownTaskTypesQuery.list();
        return types;
    }

}
