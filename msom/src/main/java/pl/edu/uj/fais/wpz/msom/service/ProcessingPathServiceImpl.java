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
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ProcessingPathDao;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
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

}
