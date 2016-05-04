/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ControllerUnitDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;

/**
 *
 * @author jarep
 */
@Repository(value = "controllerUnitDao")
public class ControllerUnitDaoImpl extends AbstractDao<ControllerUnit, Long> implements ControllerUnitDao {
    
}
