/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ProcessingPathDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;

/**
 *
 * @author jarep
 */
@Service(value = "processingPathService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ProcessingPathServiceImpl extends AbstractService<ProcessingPath> implements ProcessingPathService {

    @Autowired
    private ProcessingPathDao processingPathDao;

    public ProcessingPathDao getProcessingPathDao() {
        return processingPathDao;
    }

    @Override
    public IDao getDao() {
        return getProcessingPathDao();
    }

    @Override
    public List<ProcessingPath> getProcessingPathsByControllerUnit(ControllerUnit controllerUnit) {
        return processingPathDao.getProcessingPathsByControllerUnit(controllerUnit);
    }

    @Override
    public List<ProcessingPath> getProcessingPathsLeadingToTheControllerUnit(ControllerUnit controllerUnit) {
        return processingPathDao.getProcessingPathsLeadingToTheControllerUnit(controllerUnit);
    }

    @Override
    public List<ProcessingPath> getProcessingPathsComingOutFromTheControllerUnit(ControllerUnit controllerUnit) {
        return processingPathDao.getProcessingPathsComingOutFromTheControllerUnit(controllerUnit);
    }

    @Override
    public ProcessingPath getProcessingPathByTaskTypeAndControllerUnit(ControllerUnit controllerUnit, TaskType taskType) {
        return processingPathDao.getProcessingPathByTaskTypeAndControllerUnit(controllerUnit, taskType);
    }

    @Override
    public List<TaskType> getTaskTypesToProcessingByControllerUnit(ControllerUnit controllerUnit) {
        return processingPathDao.getTaskTypesToProcessingByControllerUnit(controllerUnit);
    }

    @Override
    public List<TaskType> getTaskTypesToFinishedByControllerUnit(ControllerUnit controllerUnit) {
        return processingPathDao.getTaskTypesToFinishedByControllerUnit(controllerUnit);
    }

    @Override
    public boolean removeProcessingPathsByControllerUnit(ControllerUnit controllerUnit) {
        List<ProcessingPath> paths = getProcessingPathsComingOutFromTheControllerUnit(controllerUnit);
        for (ProcessingPath p : paths) {
            processingPathDao.remove(p);
        }
        return true;
    }

    @Override
    public boolean removeProcessingPathsByNextControllerUnit(ControllerUnit nextControllerUnit) {
        List<ProcessingPath> paths = getProcessingPathsLeadingToTheControllerUnit(nextControllerUnit);
        for (ProcessingPath p : paths) {
            processingPathDao.remove(p);
        }
        return true;
    }

    @Override
    public boolean add(ProcessingPath processingPath) {
        if (processingPath.getControllerUnit().getModel().equals(processingPath.getNextControllerUnit().getModel())) {
            add(processingPath);
            return true;
        } else {
            return false;
        }
    }

}
