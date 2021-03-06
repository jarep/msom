/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.DistributionDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.entities.Distribution;
import pl.edu.uj.fais.wpz.msom.service.interfaces.DistributionService;

/**
 *
 * @author paweldylag
 */
@Service(value = "distributionService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class DistributionServiceImpl extends AbstractService<Distribution> implements DistributionService {
    
    @Autowired
    private DistributionDao distributionDao;

    @Override
    public IDao getDao() {
        return this.distributionDao;
    }
    
    
}
