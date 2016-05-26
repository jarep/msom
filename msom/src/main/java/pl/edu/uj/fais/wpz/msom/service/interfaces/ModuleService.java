/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Module;

/**
 *
 * @author paweldylag
 */
public interface ModuleService extends IService<Module> {
    
    /**
     * Get all modules assigned to given controller unit.
     * 
     * @param controllerUnit
     * @return founded modules
     */
    List<Module> getModulesByControllerUnit(ControllerUnit controllerUnit);
}
