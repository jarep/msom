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
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModuleDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModuleService;

/** 
 * Service of processing module implementation
 * @author paweldylag
 */
@Service(value = "moduleService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ModuleServiceImpl extends AbstractService<Module> implements ModuleService {

    @Autowired
    private ModuleDao moduleDao;

    @Override
    public IDao getDao() {
        return this.moduleDao;
    }

    @Override
    public List<Module> getModulesByControllerUnit(ControllerUnit controllerUnit) {
        return moduleDao.getModulesByControllerUnit(controllerUnit);
    }

}
