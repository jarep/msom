/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ControllerUnitDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModelDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModuleDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ProcessingPathDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskTypeDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.integration.DomainAwareBase;

/**
 *
 * @author jarep
 */
@ContextConfiguration("/testApplicationContext.xml")
//@Transactional
public class ControllerUnitDaoImplTest extends DomainAwareBase {

    @Autowired
    TaskTypeDao taskTypeDao;

    @Autowired
    ControllerUnitDao controllerUnitDao;

    @Autowired
    ModelDao modelDao;

    @Autowired
    ModuleDao moduleDao;

    @Autowired
    ProcessingPathDao processingPathDao;

    private final List<Model> models = new ArrayList<>();
    private final List<TaskType> taskTypes = new ArrayList<>();
    private final List<ControllerUnit> controllerUnits = new ArrayList<>();
    private final List<Module> modules = new ArrayList<>();
    private final List<ProcessingPath> paths = new ArrayList<>();

    public ControllerUnitDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        // Create some Models
        Model m1 = new Model("Model Pierwszy");
        Model m2 = new Model("Model Drugi");
        addAllAndSaveToLocalList(modelDao, models, m1, m2);

        // Create some Task Types
        TaskType taskType1 = new TaskType("Typ A", 10);
        TaskType taskType2 = new TaskType("Typ B", 20);
        TaskType taskType3 = new TaskType("Typ C", 30);
        addAllAndSaveToLocalList(taskTypeDao, taskTypes, taskType1, taskType2, taskType3);

        // Create some Controller Units
        ControllerUnit controller1 = new ControllerUnit("Kontroler 1");
        ControllerUnit controller2 = new ControllerUnit("Kontroler 2");
        ControllerUnit controller3 = new ControllerUnit("Kontroler 3");
        controller1.setModel(m1);
        controller2.setModel(m1);
        controller3.setModel(m1);

        ControllerUnit controllerX = new ControllerUnit("Kontroler Obcy");
        controllerX.setModel(m2);
        addAllAndSaveToLocalList(controllerUnitDao, controllerUnits, controller1, controller2, controller3, controllerX);

        // Create some Modules
        Module module1 = new Module("Mod01", 4, 2400, controller1);
        Module module2 = new Module("Mod02", 16, 3600, controller1);
        Module module3 = new Module("Mod03", 4, 2800, controller2);
        Module module4 = new Module("Mod04", 8, 1200, controller3);
        module1.addTaskType(taskType1);
        module1.addTaskType(taskType2);
        module2.addTaskType(taskType3);
        module3.addTaskType(taskType3);
        module4.addTaskType(taskType1);
        module4.addTaskType(taskType3);
        addAllAndSaveToLocalList(moduleDao, modules, module1, module2, module3, module4);

        // Create some processing paths
        ProcessingPath path1 = new ProcessingPath(controller1, taskType1, Boolean.TRUE, controller3);
        ProcessingPath path2 = new ProcessingPath(controller1, taskType2, Boolean.FALSE, controller3);
        ProcessingPath path3 = new ProcessingPath(controller1, taskType3, Boolean.TRUE, controller2);
        ProcessingPath path4 = new ProcessingPath(controller2, taskType3, Boolean.TRUE, controller2);
        ProcessingPath path5 = new ProcessingPath(controller3, taskType1, Boolean.FALSE, controller3);
        ProcessingPath path6 = new ProcessingPath(controller3, taskType2, Boolean.TRUE, controller3);
        addAllAndSaveToLocalList(processingPathDao, paths, path1, path2, path3, path4, path5, path6);

    }

    private <T> void addAllAndSaveToLocalList(IDao<T, Long> dao, List<T> localList, T... entites) {
        for (T e : entites) {
            localList.add(e);
            dao.add(e);
        }
    }

    private <T> void addAll(IDao<T, Long> dao, T... entites) {
        for (T e : entites) {
            dao.add(e);
        }
    }

    @After
    public void tearDown() {
        // Remove data from database

        removeAllFromDBAndLocalList(processingPathDao, paths);
//        removeAllFromDBAndLocalList(moduleDao, modules);
//        removeAllFromDBAndLocalList(controllerUnitDao, controllerUnits);
//        removeAllFromDBAndLocalList(taskTypeDao, taskTypes);
//        removeAllFromDBAndLocalList(modelDao, models);

    }

    private <T> void removeAllFromDBAndLocalList(IDao<T, Long> dao, List<T> localList) {
        for (T e : localList) {
            dao.remove(e);
        }
        localList.clear();
    }

    private <T> void removeAll(IDao<T, Long> dao, T... entites) {
        for (T e : entites) {
            dao.remove(e);
        }
    }

    /**
     * Test of removeControllerUnit method, of class ControllerUnitDaoImpl.
     */
    @Test
    public void testRemoveControllerUnit() {

        /* Remove controllers related to some modules */
        ControllerUnit c1 = new ControllerUnit("C1");
        controllerUnitDao.add(c1);
        Module m1 = new Module("m1", 4, 1900, c1);
        moduleDao.add(m1);

        // try to remove -> shouldn't work
        assertFalse(controllerUnitDao.removeControllerUnit(c1));

        moduleDao.remove(m1);
        // try to remove after remove references-> should work
        assertTrue(controllerUnitDao.removeControllerUnit(c1));

        /* Remove controllers related to some processing paths */
        ControllerUnit c2 = new ControllerUnit("C2");
        ControllerUnit c3 = new ControllerUnit("C3");
        ControllerUnit c4 = new ControllerUnit("C4");
        addAll(controllerUnitDao, c2, c3, c4);

        ProcessingPath p1 = new ProcessingPath(c2, taskTypes.get(0), Boolean.TRUE, c2);
        ProcessingPath p2 = new ProcessingPath(c3, taskTypes.get(0), Boolean.TRUE, c4);
        addAll(processingPathDao, p1, p2);

        // try to remove -> shouldn't work
        assertFalse(controllerUnitDao.removeControllerUnit(c2));
        assertFalse(controllerUnitDao.removeControllerUnit(c3));
        assertFalse(controllerUnitDao.removeControllerUnit(c4));

        removeAll(processingPathDao, p1, p2);
        // try to remove after remove references-> should work
        assertTrue(controllerUnitDao.removeControllerUnit(c2));
        assertTrue(controllerUnitDao.removeControllerUnit(c3));
        assertTrue(controllerUnitDao.removeControllerUnit(c4));

    }

    /**
     * Test of getNextControllerUnit method, of class ControllerUnitDaoImpl.
     */
//    @Test
//    public void testGetNextControllerUnit() {
//        System.out.println("getNextControllerUnit");
//        ControllerUnit controllerUnit = null;
//        TaskType taskType = null;
//        ControllerUnitDaoImpl instance = new ControllerUnitDaoImpl();
//        ControllerUnit expResult = null;
//        ControllerUnit result = instance.getNextControllerUnit(controllerUnit, taskType);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of getAvailableTaskTypesToProcessingInControllerUnit method, of
     * class ControllerUnitDaoImpl.
     */
//    @Test
//    public void testGetAvailableTaskTypesToProcessingInControllerUnit() {
//        System.out.println("getAvailableTaskTypesToProcessingInControllerUnit");
//        ControllerUnit controllerUnit = null;
//        ControllerUnitDaoImpl instance = new ControllerUnitDaoImpl();
//        List<TaskType> expResult = null;
//        List<TaskType> result = instance.getAvailableTaskTypesToProcessingInControllerUnit(controllerUnit);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
