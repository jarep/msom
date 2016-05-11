/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.ArrayList;
import java.util.Arrays;
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
public class ProcessingPathDaoImplTest extends DomainAwareBase {

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

    // local storage 
    private final List<Module> modules = new ArrayList<>();
    private final List<ProcessingPath> paths = new ArrayList<>();
    private final List<ControllerUnit> controllers = new ArrayList<>();
    private final List<TaskType> taskTypes = new ArrayList<>();
    private final List<Model> models = new ArrayList<>();

    private final List<ProcessingPath> pathsFromController1 = new ArrayList<>();
    private final List<ProcessingPath> pathsFromController2 = new ArrayList<>();
    private final List<ProcessingPath> pathsFromController3 = new ArrayList<>();
    private final List<ProcessingPath> pathsFromController4 = new ArrayList<>();

    private final List<ProcessingPath> pathsToController2 = new ArrayList<>();
    private final List<ProcessingPath> pathsToController3 = new ArrayList<>();
    private final List<ProcessingPath> pathsToController4 = new ArrayList<>();

    private final List<TaskType> taskTypesToProcessing1 = new ArrayList<>();
    private final List<TaskType> taskTypesToProcessing2 = new ArrayList<>();
    private final List<TaskType> taskTypesToProcessing3 = new ArrayList<>();
    private final List<TaskType> taskTypesToProcessing4 = new ArrayList<>();

    private final List<TaskType> taskTypesToFinished1 = new ArrayList<>();
    private final List<TaskType> taskTypesToFinished2 = new ArrayList<>();
    private final List<TaskType> taskTypesToFinished3 = new ArrayList<>();
    private final List<TaskType> taskTypesToFinished4 = new ArrayList<>();

    public ProcessingPathDaoImplTest() {
    }

    private <T> void addAllAndStore(IDao<T, Long> dao, List<T> localList, T... entities) {
        for (T e : entities) {
            dao.add(e);
            localList.add(e);
        }
    }

    private <T> void removeAllAndForget(IDao<T, Long> dao, List<T> entities) {
        for (T e : entities) {
            dao.remove(e);
        }
        entities.clear();
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
        addAllAndStore(modelDao, models, m1, m2);

        // Create some Task Types
        TaskType taskType1 = new TaskType("Typ A", 10);
        TaskType taskType2 = new TaskType("Typ B", 20);
        TaskType taskType3 = new TaskType("Typ C", 30);
        TaskType taskType4 = new TaskType("Typ D", 40);
        TaskType taskType5 = new TaskType("Typ E", 50);
        addAllAndStore(taskTypeDao, taskTypes, taskType1, taskType2, taskType3, taskType4, taskType5);

        // Create some Controller Units
        ControllerUnit controller1 = new ControllerUnit("Kontroler 1");
        ControllerUnit controller2 = new ControllerUnit("Kontroler 2");
        ControllerUnit controller3 = new ControllerUnit("Kontroler 3");
        ControllerUnit controller4 = new ControllerUnit("Kontroler 4");
        controller1.setModel(m1);
        controller2.setModel(m1);
        controller3.setModel(m1);
        controller4.setModel(m1);

        ControllerUnit controllerX = new ControllerUnit("Kontroler Obcy");
        controllerX.setModel(m2);
        addAllAndStore(controllerUnitDao, controllers, controller1, controller2, controller3, controller4, controllerX);

        // Create some Modules
        Module module1 = new Module("Mod01", 4, 2400, controller1);
        Module module2 = new Module("Mod02", 16, 3600, controller1);
        Module module3 = new Module("Mod03", 4, 2800, controller2);
        Module module4 = new Module("Mod04", 8, 1200, controller3);
        Module module5 = new Module("Mod05", 4, 1200, controller4);

        module1.addTaskType(taskType1);
        module1.addTaskType(taskType2);
        module2.addTaskType(taskType3);
        module2.addTaskType(taskType1);
        module3.addTaskType(taskType3);
        module4.addTaskType(taskType1);
        module4.addTaskType(taskType2);
        module5.addTaskType(taskType4);
        addAllAndStore(moduleDao, modules, module1, module2, module3, module4, module5);

        // Create some processing paths
        // from controller1
        ProcessingPath path1 = new ProcessingPath(controller1, taskType1, Boolean.TRUE, controller3);
        ProcessingPath path2 = new ProcessingPath(controller1, taskType2, Boolean.FALSE, controller3);
        ProcessingPath path3 = new ProcessingPath(controller1, taskType3, Boolean.TRUE, controller2);
        ProcessingPath path4 = new ProcessingPath(controller1, taskType4, Boolean.FALSE, controller2);
        ProcessingPath path5 = new ProcessingPath(controller1, taskType5, Boolean.FALSE, controller3);
        pathsFromController1.addAll(Arrays.asList(path1, path2, path3, path4, path5));
        taskTypesToProcessing1.addAll(Arrays.asList(taskType1, taskType3));

        // from controller2
        ProcessingPath path6 = new ProcessingPath(controller2, taskType3, Boolean.TRUE, controller2);
        ProcessingPath path7 = new ProcessingPath(controller2, taskType4, Boolean.FALSE, controller4);
        pathsFromController2.addAll(Arrays.asList(path6, path7));
        taskTypesToProcessing2.addAll(Arrays.asList(taskType3));
        taskTypesToFinished2.addAll(Arrays.asList(taskType3));

        // from controller3
        ProcessingPath path8 = new ProcessingPath(controller3, taskType1, Boolean.FALSE, controller3);
        ProcessingPath path9 = new ProcessingPath(controller3, taskType2, Boolean.TRUE, controller3);
        ProcessingPath path10 = new ProcessingPath(controller3, taskType5, Boolean.FALSE, controller3);
        pathsFromController3.addAll(Arrays.asList(path8, path9, path10));
        taskTypesToProcessing3.addAll(Arrays.asList(taskType2));
        taskTypesToFinished3.addAll(Arrays.asList(taskType1, taskType2, taskType5));

        // from controller4
        ProcessingPath path11 = new ProcessingPath(controller4, taskType4, Boolean.TRUE, controller4);
        pathsFromController4.add(path11);
        taskTypesToProcessing4.addAll(Arrays.asList(taskType4));
        taskTypesToFinished4.addAll(Arrays.asList(taskType4));

        pathsToController2.addAll(Arrays.asList(path3, path4, path6));
        pathsToController3.addAll(Arrays.asList(path1, path2, path5, path8, path9, path10));
        pathsToController4.addAll(Arrays.asList(path7, path11));

        addAllAndStore(processingPathDao, paths, path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11);
    }

    @After
    public void tearDown() {
        removeAllAndForget(processingPathDao, paths);
        removeAllAndForget(moduleDao, modules);
        removeAllAndForget(controllerUnitDao, controllers);
        removeAllAndForget(taskTypeDao, taskTypes);
        removeAllAndForget(modelDao, models);
    }

    /**
     * Test of getProcessingPathsByControllerUnit method, of class
     * ProcessingPathDaoImpl.
     */
    @Test
    public void testGetProcessingPathsByControllerUnit() {
        List<ProcessingPath> paths1 = processingPathDao.getProcessingPathsByControllerUnit(controllers.get(0));
        assertTrue(paths1.size() == pathsFromController1.size());
        assertTrue(paths1.containsAll(pathsFromController1));

        List<ProcessingPath> paths2 = processingPathDao.getProcessingPathsByControllerUnit(controllers.get(1));
        List<ProcessingPath> allPaths2 = getDistinctPathList(pathsFromController2, pathsToController2);
        assertTrue(paths2.size() == allPaths2.size());
        assertTrue(paths2.containsAll(allPaths2));

        List<ProcessingPath> paths3 = processingPathDao.getProcessingPathsByControllerUnit(controllers.get(2));
        List<ProcessingPath> allPaths3 = getDistinctPathList(pathsFromController3, pathsToController3);
        assertTrue(paths3.size() == allPaths3.size());
        assertTrue(paths3.containsAll(allPaths3));

        List<ProcessingPath> paths4 = processingPathDao.getProcessingPathsByControllerUnit(controllers.get(3));
        List<ProcessingPath> allPaths4 = getDistinctPathList(pathsFromController4, pathsToController4);
        assertTrue(paths4.size() == allPaths4.size());
        assertTrue(paths4.containsAll(allPaths4));
    }

    private List<ProcessingPath> getDistinctPathList(List<ProcessingPath> list1, List<ProcessingPath> list2) {
        List<ProcessingPath> mergedList = new ArrayList<>();

        mergedList.addAll(list1);
        for (ProcessingPath p : list2) {
            if (!mergedList.contains(p)) {
                mergedList.add(p);
            }
        }
        return mergedList;
    }

    /**
     * Test of getProcessingPathsLeadingToTheControllerUnit method, of class
     * ProcessingPathDaoImpl.
     */
    @Test
    public void testGetProcessingPathsLeadingToTheControllerUnit() {
        List<ProcessingPath> paths1 = processingPathDao.getProcessingPathsLeadingToTheControllerUnit(controllers.get(0));
        assertTrue(paths1.isEmpty());

        List<ProcessingPath> paths2 = processingPathDao.getProcessingPathsLeadingToTheControllerUnit(controllers.get(1));
        assertTrue(paths2.size() == pathsToController2.size());
        assertTrue(paths2.containsAll(pathsToController2));

        List<ProcessingPath> paths3 = processingPathDao.getProcessingPathsLeadingToTheControllerUnit(controllers.get(2));
        assertTrue(paths3.size() == pathsToController3.size());
        assertTrue(paths3.containsAll(pathsToController3));

        List<ProcessingPath> paths4 = processingPathDao.getProcessingPathsLeadingToTheControllerUnit(controllers.get(3));
        assertTrue(paths4.size() == pathsToController4.size());
        assertTrue(paths4.containsAll(pathsToController4));
    }

    /**
     * Test of getProcessingPathsComingOutFromTheControllerUnit method, of class
     * ProcessingPathDaoImpl.
     */
    @Test
    public void testGetProcessingPathsComingOutFromTheControllerUnit() {
        List<ProcessingPath> paths1 = processingPathDao.getProcessingPathsComingOutFromTheControllerUnit(controllers.get(0));
        assertTrue(paths1.equals(pathsFromController1));

        List<ProcessingPath> paths2 = processingPathDao.getProcessingPathsComingOutFromTheControllerUnit(controllers.get(1));
        assertTrue(paths2.equals(pathsFromController2));

        List<ProcessingPath> paths3 = processingPathDao.getProcessingPathsComingOutFromTheControllerUnit(controllers.get(2));
        assertTrue(paths3.equals(pathsFromController3));

        List<ProcessingPath> paths4 = processingPathDao.getProcessingPathsComingOutFromTheControllerUnit(controllers.get(3));
        assertTrue(paths4.equals(pathsFromController4));
    }

    /**
     * Test of getProcessingPathByTaskTypeAndControllerUnit method, of class
     * ProcessingPathDaoImpl.
     */
    @Test
    public void testGetProcessingPathByTaskTypeAndControllerUnit() {
        ProcessingPath path1 = processingPathDao.getProcessingPathByTaskTypeAndControllerUnit(controllers.get(0), taskTypes.get(3));
        assertTrue(path1.equals(paths.get(3)));

        ProcessingPath path2 = processingPathDao.getProcessingPathByTaskTypeAndControllerUnit(controllers.get(0), taskTypes.get(0));
        assertTrue(path2.equals(paths.get(0)));

        ProcessingPath path3 = processingPathDao.getProcessingPathByTaskTypeAndControllerUnit(controllers.get(3), taskTypes.get(3));
        assertTrue(path3.equals(paths.get(10)));
    }

    /**
     * Test of getTaskTypesToProcessingByControllerUnit method, of class
     * ProcessingPathDaoImpl.
     */
    @Test
    public void testGetTaskTypesToProcessingByControllerUnit() {
        List<TaskType> taskTypes1 = processingPathDao.getTaskTypesToProcessingByControllerUnit(controllers.get(0));
        assertTrue(taskTypes1.size() == taskTypesToProcessing1.size());
        assertTrue(taskTypes1.containsAll(taskTypesToProcessing1));

        List<TaskType> taskTypes2 = processingPathDao.getTaskTypesToProcessingByControllerUnit(controllers.get(1));
        assertTrue(taskTypes2.size() == taskTypesToProcessing2.size());
        assertTrue(taskTypes2.containsAll(taskTypesToProcessing2));

        List<TaskType> taskTypes3 = processingPathDao.getTaskTypesToProcessingByControllerUnit(controllers.get(2));
        assertTrue(taskTypes3.size() == taskTypesToProcessing3.size());
        assertTrue(taskTypes3.containsAll(taskTypesToProcessing3));

        List<TaskType> taskTypes4 = processingPathDao.getTaskTypesToProcessingByControllerUnit(controllers.get(3));
        assertTrue(taskTypes4.size() == taskTypesToProcessing4.size());
        assertTrue(taskTypes4.containsAll(taskTypesToProcessing4));

    }

    /**
     * Test of getTaskTypesToFinishedByControllerUnit method, of class
     * ProcessingPathDaoImpl.
     */
    @Test
    public void testGetTaskTypesToFinishedByControllerUnit() {
        List<TaskType> taskTypes1 = processingPathDao.getTaskTypesToFinishedByControllerUnit(controllers.get(0));
        assertTrue(taskTypes1.size() == taskTypesToFinished1.size());
        assertTrue(taskTypes1.containsAll(taskTypesToFinished1));

        List<TaskType> taskTypes2 = processingPathDao.getTaskTypesToFinishedByControllerUnit(controllers.get(1));
        assertTrue(taskTypes2.size() == taskTypesToFinished2.size());
        assertTrue(taskTypes2.containsAll(taskTypesToFinished2));

        List<TaskType> taskTypes3 = processingPathDao.getTaskTypesToFinishedByControllerUnit(controllers.get(2));
        assertTrue(taskTypes3.size() == taskTypesToFinished3.size());
        assertTrue(taskTypes3.containsAll(taskTypesToFinished3));

        List<TaskType> taskTypes4 = processingPathDao.getTaskTypesToFinishedByControllerUnit(controllers.get(3));
        assertTrue(taskTypes4.size() == taskTypesToFinished4.size());
        assertTrue(taskTypes4.containsAll(taskTypesToFinished4));
    }

}
