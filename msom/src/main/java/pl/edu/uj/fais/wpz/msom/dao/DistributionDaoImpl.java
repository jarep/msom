/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.DistributionDao;
import pl.edu.uj.fais.wpz.msom.entities.Distribution;

/**
 *
 * @author paweldylag
 */
@Repository(value = "distributionDao")
public class DistributionDaoImpl extends AbstractDao<Distribution, Long> implements DistributionDao{
    
    /**
     * Nothing here 
     */
    
}
