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
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModelDao;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ModelService;

/**
 *
 * @author jarep
 */
@Service(value = "modelService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public class ModelServiceImpl extends AbstractService<Model> implements ModelService {

    @Autowired
    private ModelDao modelDao;

    public ModelDao getModelDao() {
        return modelDao;
    }

    @Override
    public IDao getDao() {
        return getModelDao();
    }

    @Override
    public boolean remove(Model model) {
        return modelDao.remove(model);
    }

    @Override
    public boolean detachFirstTaskDispatcher(Model model) {
        return modelDao.detachFirstTaskDispatcher(model);
    }

}
