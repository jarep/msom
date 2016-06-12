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
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ControllerUnitService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
@Service(value = "controllerUnitService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ControllerUnitServiceImpl extends AbstractService<ControllerUnit> implements ControllerUnitService {

    @Autowired
    private ControllerUnitDao controllerUnitDao;

    @Autowired
    private ProcessingPathService processingPathService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private TaskTypeService taskTypeService;

    /**
     *
     * @return controller unit dao
     */
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
    public boolean update(ControllerUnit controllerUnit) {
        List<ProcessingPath> paths = processingPathService.getProcessingPathsByControllerUnit(controllerUnit);
        if (controllerUnit.getModel().equals(controllerUnitDao.find(controllerUnit.getId()).getModel()) || paths.isEmpty()) {
            controllerUnitDao.update(controllerUnit);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeWithModulesAndPaths(ControllerUnit controllerUnit) {
        List<ProcessingPath> allPaths = processingPathService.getProcessingPathsByControllerUnit(controllerUnit);
        for (ProcessingPath p : allPaths) {
            processingPathService.remove(p);
        }

        List<Module> modules = moduleService.getModulesByControllerUnit(controllerUnit);
        for (Module m : modules) {
            moduleService.remove(m);
        }

        return processingPathService.getProcessingPathsByControllerUnit(controllerUnit).isEmpty()
                && moduleService.getModulesByControllerUnit(controllerUnit).isEmpty();
    }

    @Override
    public ControllerUnit getNextControllerUnit(ControllerUnit controllerUnit, TaskType taskType) {
        return controllerUnitDao.getNextControllerUnit(controllerUnit, taskType);
    }

    @Override
    public List<TaskType> getAvailableTaskTypesToProcessingInControllerUnit(ControllerUnit controllerUnit) {
        return controllerUnitDao.getAvailableTaskTypesToProcessingInControllerUnit(controllerUnit);
    }

    @Override
    public boolean validateControllerUnitProcessingPotentiality(ControllerUnit controllerUnit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean validateControllerUnitPathDefinitions(ControllerUnit controllerUnit) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TaskType> getTypesWithUnspecifiedPathFromContoller(ControllerUnit controllerUnit) {
        List<ProcessingPath> processingPathsComingOutFromTheControllerUnit = processingPathService.getProcessingPathsComingOutFromTheControllerUnit(controllerUnit);
        List<TaskType> taskTypes = taskTypeService.findAll();

        for (ProcessingPath p : processingPathsComingOutFromTheControllerUnit) {
            taskTypes.remove(p.getTaskType());
        }
        return taskTypes;
    }

    @Override
    public List<ControllerUnit> getControllersByModel(Model model) {
        return controllerUnitDao.getControllersByModel(model);
    }

}
