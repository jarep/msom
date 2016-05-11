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
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ControllerUnitDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;

/**
 *
 * @author jarep
 */
@Service(value = "controllerUnitService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ControllerUnitServiceImpl extends AbstractService<ControllerUnit> implements ControllerUnitService {

    @Autowired
    private ControllerUnitDao controllerUnitDao;

    public ControllerUnitDao getControllerUnitDao() {
        return controllerUnitDao;
    }

    @Override
    public IDao getDao() {
        return getControllerUnitDao();
    }

    @Override
    public boolean remove(ControllerUnit controllerUnit) {
        return controllerUnitDao.removeControllerUnit(controllerUnit);
    }
    
    @Override
    public boolean removeWithModulesAndPaths(ControllerUnit controllerUnit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ControllerUnit getNextControllerUnit(ControllerUnit controllerUnit, TaskType taskType) {
        return controllerUnitDao.getNextControllerUnit(controllerUnit, taskType);
    }

    @Override
    public List<TaskType> getAvailableTaskTypesToProcessingInControllerUnit(ControllerUnit controllerUnit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validateControllerUnitProcessingPotentiality(ControllerUnit controllerUnit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validateControllerUnitPathDefinitions(ControllerUnit controllerUnit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
