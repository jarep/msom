/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModuleDao;
import pl.edu.uj.fais.wpz.msom.entities.Module;

/**
 *
 * @author paweldylag
 */
@Repository(value = "moduleDao")
public class ModuleDaoImpl extends AbstractDao<Module, Long> implements ModuleDao {

    // nothing here
}
