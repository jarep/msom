/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import org.springframework.stereotype.Repository;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ProcessingPathDao;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;

/**
 *
 * @author jarep
 */
@Repository(value = "processingPathDao")
public class ProcessingPathDaoImpl extends AbstractDao<ProcessingPath, Long> implements ProcessingPathDao {

}
