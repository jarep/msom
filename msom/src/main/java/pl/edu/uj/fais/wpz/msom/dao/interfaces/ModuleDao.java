/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao.interfaces;

import java.util.List;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Module;

/**
 * Data access object for processing module
 * @author paweldylag
 */
public interface ModuleDao extends IDao<Module, Long> {

    /**
     * Get all modules assigned to given controller unit.
     *
     * @param controllerUnit
     * @return founded modules
     */
    List<Module> getModulesByControllerUnit(ControllerUnit controllerUnit);

}
