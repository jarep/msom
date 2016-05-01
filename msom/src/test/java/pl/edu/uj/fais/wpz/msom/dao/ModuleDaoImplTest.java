/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;


import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModuleDao;

import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskTypeDao;
import pl.edu.uj.fais.wpz.msom.entities.Module;

import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.integration.DomainAwareBase;

/**
 *
 * @author paweldylag
 */
@ContextConfiguration("/testApplicationContext.xml")
public class ModuleDaoImplTest extends DomainAwareBase {
    
    @Autowired
    private ModuleDao moduleDao;
     
    @Autowired
    private TaskTypeDao taskTypeDao;

    public ModuleDaoImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }
    
    public void setUp() {
        Module module1 = new Module(2, 2000);
        Module module2 = new Module(4, 4000);
        Module module3 = new Module(8, 8000);
        Module module4 = new Module(16, 16000);
        
        TaskType type1 = new TaskType("Typ a", 50);
        taskTypeDao.add(type1);
        TaskType type2 = new TaskType("Typ b", 10);
        taskTypeDao.add(type2);
        TaskType type3 = new TaskType("Typ c", 70);
        taskTypeDao.add(type3);
        
        module1.getTaskTypes().add(type1);
        module2.getTaskTypes().add(type2);
        module3.getTaskTypes().add(type3);
        module4.getTaskTypes().add(type1);
        module4.getTaskTypes().add(type2);
        module4.getTaskTypes().add(type3);
    }

    @Override
    public void deleteAllDomainEntities() {
        super.deleteAllDomainEntities();
        setUp();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAdd() {
        int size = moduleDao.findAll().size();
        Module module = new Module(2, 4000);
        moduleDao.add(module);
        assertTrue(size < moduleDao.findAll().size());
    }

    @Test
    public void testUpdate() {
        int cores = 4;
        int eff = 8000;
        // add task types 
        TaskType type = new TaskType("Typ ALFA", 30);
        TaskType newType = new TaskType("Typ BETA", 50);
        taskTypeDao.add(type);
        taskTypeDao.add(newType);
        // add module
        Module module = new Module(2, 4000);
        module.getTaskTypes().add(type);
        moduleDao.add(module);

        // update task
        module.setCores(cores);
        module.setEfficiency(eff);
        moduleDao.update(module);

        Module found = moduleDao.find(module.getId());
        assertTrue(found.getCores().equals(cores));
        assertTrue(found.getEfficiency().equals(eff));
    }
    
    @Test
    public void testAddingNewTaskTypes() {
        // add task types 
        TaskType type = new TaskType("Typ ALFA", 30);
        TaskType newType = new TaskType("Typ BETA", 50);
        taskTypeDao.add(type);
        taskTypeDao.add(newType);
        // add module
        Module module = new Module(2, 4000);
        module.addTaskType(type);
        moduleDao.add(module);
        // check if set contains only one task
        assertTrue(module.getTaskTypes().size() == 1);
        // add new task
        module.addTaskType(newType);
        moduleDao.update(module);
        // check if new task type has been added to module
        Module found = moduleDao.find(module.getId());
        assertTrue(found.getTaskTypes().size() == 2);
        assertTrue(found.containsTaskType(newType));
    }
    
    @Test
    public void testRemovingTaskTypes() {
        // add task types 
        TaskType type = new TaskType("Typ ALFA", 30);
        TaskType newType = new TaskType("Typ BETA", 50);
        taskTypeDao.add(type);
        taskTypeDao.add(newType);
        // add module
        Module module = new Module(2, 4000);
        module.addTaskType(type);
        moduleDao.add(module);
        // check if set contains only one task
        assertTrue(module.getTaskTypes().size() == 1);
        // add new task
        module.addTaskType(newType);
        moduleDao.update(module);
        // check if new task type has been added to module
        Module found = moduleDao.find(module.getId());
        assertTrue(found.getTaskTypes().size() == 2);
        assertTrue(found.containsTaskType(newType));
        // remove one task
        found.removeTaskType(newType);
        assertTrue(!found.containsTaskType(newType));
        assertTrue(found.getTaskTypes().size() == 1);
    }

    @Test
    public void testFind() {
        Module module = new Module(2, 4000);
        TaskType type = new TaskType("Typ ALFA", 30);
        module.getTaskTypes().add(type);
        moduleDao.add(module);

        Module found = moduleDao.find(module.getId());
        assertEquals(module, found);
    }

    @Test
    public void testList() {
       List<Module> modules = moduleDao.findAll();
       Module module1 = new Module(2, 4000);
       Module module2 = new Module(4, 8000);
       moduleDao.add(module1);
       moduleDao.add(module2);
       
       List<Module> freshModules = moduleDao.findAll();
       assertTrue(modules.size() + 2 == freshModules.size());
       assertTrue(freshModules.contains(module1));
       assertTrue(freshModules.contains(module2));
    }

    @Test
    public void testRemove() {
       Module module = new Module(2, 4000);
       moduleDao.add(module);
       // check if added
       assertTrue(moduleDao.find(module.getId()) != null);
       moduleDao.remove(module);
       // check if removed
       assertTrue(moduleDao.find(module.getId()) == null);
    }
    
    
}
