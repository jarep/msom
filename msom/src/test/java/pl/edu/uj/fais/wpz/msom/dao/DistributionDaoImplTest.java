/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.List;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.DistributionDao;
import pl.edu.uj.fais.wpz.msom.entities.Distribution;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.integration.DomainAwareBase;

/**
 *
 * @author paweldylag
 */
@ContextConfiguration("/testApplicationContext.xml")
public class DistributionDaoImplTest extends DomainAwareBase{
    
    @Autowired
    private DistributionDao distributionDao;
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    
    @Override
    public void deleteAllDomainEntities() {
        super.deleteAllDomainEntities();
    }
    
    @Test
    public void testAdd() {
        int size = distributionDao.findAll().size();
        Distribution distribution = new Distribution(DistributionType.GAUSSIAN);
        distributionDao.add(distribution);
        assertTrue(size < distributionDao.findAll().size());
    }

    @Test
    public void testUpdate() {
        DistributionType dt = DistributionType.LINEAR;
        
        // add distribution
        Distribution distribution = new Distribution(DistributionType.GAUSSIAN);
        distributionDao.add(distribution);

        // update distribution
        distribution.setType(dt);
        distributionDao.update(distribution);

        Distribution found = distributionDao.find(distribution.getId());
        assertTrue(found.getType().equals(dt));
    }

    @Test
    public void testFind() {
        Distribution distribution = new Distribution(DistributionType.LINEAR);
        distributionDao.add(distribution);

        distributionDao.add(distribution);

        Distribution found = distributionDao.find(distribution.getId());
        assertEquals(distribution, found);
    }

    @Test
    public void testList() {
       List<Distribution> distributions = distributionDao.findAll();
       Distribution d1 = new Distribution(DistributionType.LINEAR);
       Distribution d2 = new Distribution(DistributionType.GAUSSIAN);
       distributionDao.add(d1);
       distributionDao.add(d2);
       
       List<Distribution> freshDistributions = distributionDao.findAll();
       assertTrue(distributions.size() + 2 == freshDistributions.size());
       assertTrue(freshDistributions.contains(d1));
       assertTrue(freshDistributions.contains(d2));
    }

    @Test
    public void testRemove() {
       Distribution distribution = new Distribution(DistributionType.LINEAR);
       distributionDao.add(distribution);
       // check if added
       assertTrue(distributionDao.find(distribution.getId()) != null);
       distributionDao.remove(distribution);
       // check if removed
       assertTrue(distributionDao.find(distribution.getId()) == null);
    }
    
}
