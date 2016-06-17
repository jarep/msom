/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web.helpers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ControllerUnitDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.DistributionDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModelDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ModuleDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.ProcessingPathDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskProbabilityDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskTypeDao;
import pl.edu.uj.fais.wpz.msom.entities.ControllerUnit;
import pl.edu.uj.fais.wpz.msom.entities.Distribution;
import pl.edu.uj.fais.wpz.msom.entities.DistributionType;
import pl.edu.uj.fais.wpz.msom.entities.Model;
import pl.edu.uj.fais.wpz.msom.entities.Module;
import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskProbability;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;

/**
 * Small util helper for generating entities to simulate real system.
 *
 * @author jaroslaw
 */
@Service
public class EntityGenerator {

    @Autowired
    TaskDao taskDao;

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

    @Autowired
    TaskProbabilityDao taskProbabilityDao;

    @Autowired
    DistributionDao distributionDao;

    public void generateDomain() {

        // Create some Models
        Model m1 = new Model("Model Pierwszy");
        addAll(modelDao, m1);

        // Create some Task Types
        TaskType taskType1 = new TaskType("Typ A", 10);
        TaskType taskType2 = new TaskType("Typ B", 20);
        TaskType taskType3 = new TaskType("Typ C", 30);
        TaskType taskType4 = new TaskType("Typ D", 40);
        TaskType taskType5 = new TaskType("Typ E", 50);
        TaskType taskType6 = new TaskType("Typ Y", 50);

        addAll(taskTypeDao, taskType1, taskType2, taskType3, taskType4, taskType5, taskType6);
        // Create some Tasks
        Task task1 = new Task("Task 1", taskType1);
        Task task2 = new Task("Task 2", taskType1);
        Task task3 = new Task("Task 3", taskType2);
        Task task4 = new Task("Task 4", taskType2);
        Task task5 = new Task("Task 5", taskType5);
        Task task6 = new Task("Task 6", taskType3);
        Task task7 = new Task("Task 7", taskType1);
        Task task8 = new Task("Task 8", taskType5);
        Task task9 = new Task("Task 9", taskType3);

        addAll(taskDao, task1, task2, task3, task4, task5, task6, task7, task8, task9);

        // Create some Controller Units
        ControllerUnit controller1 = new ControllerUnit("Kontroler 1");
        ControllerUnit controller2 = new ControllerUnit("Kontroler 2");
        ControllerUnit controller3 = new ControllerUnit("Kontroler 3");
        ControllerUnit controller4 = new ControllerUnit("Kontroler 4");
        controller1.setModel(m1);
        controller2.setModel(m1);
        controller3.setModel(m1);
        controller4.setModel(m1);
        
        addAll(controllerUnitDao, controller1, controller2, controller3, controller4);
        
        m1.setFirstControllerUnit(controller1);
        modelDao.update(m1);

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
        addAll(moduleDao, module1, module2, module3, module4, module5);

        // Create some processing paths
        // from controller1
        ProcessingPath path1 = new ProcessingPath(controller1, taskType1, Boolean.TRUE, controller3);
        ProcessingPath path2 = new ProcessingPath(controller1, taskType2, Boolean.FALSE, controller3);
        ProcessingPath path3 = new ProcessingPath(controller1, taskType3, Boolean.TRUE, controller2);
        ProcessingPath path4 = new ProcessingPath(controller1, taskType4, Boolean.FALSE, controller2);
        ProcessingPath path5 = new ProcessingPath(controller1, taskType5, Boolean.FALSE, controller3);

        // from controller2
        ProcessingPath path6 = new ProcessingPath(controller2, taskType3, Boolean.TRUE, controller2);
        ProcessingPath path7 = new ProcessingPath(controller2, taskType4, Boolean.FALSE, controller4);
        // from controller3
        ProcessingPath path8 = new ProcessingPath(controller3, taskType1, Boolean.FALSE, controller3);
        ProcessingPath path9 = new ProcessingPath(controller3, taskType2, Boolean.TRUE, controller3);
        ProcessingPath path10 = new ProcessingPath(controller3, taskType5, Boolean.FALSE, controller3);
        // from controller4
        ProcessingPath path11 = new ProcessingPath(controller4, taskType4, Boolean.TRUE, controller4);

        addAll(processingPathDao, path1, path2, path3, path4, path5, path6, path7, path8, path9, path10, path11);

        // Create some distributions
        Distribution distribution1 = new Distribution(DistributionType.POISSON, 10.0, 20.0);
        Distribution distribution2 = new Distribution(DistributionType.POISSON, 3.0, 10.0);
        Distribution distribution3 = new Distribution(DistributionType.POISSON, 6.0, 15.0);

        addAll(distributionDao, distribution1, distribution2, distribution3);

        // Create some task probabilities for model 1
        TaskProbability probability1 = new TaskProbability(distribution1, task1, m1);
        TaskProbability probability2 = new TaskProbability(distribution1, task2, m1);
        TaskProbability probability3 = new TaskProbability(distribution2, task3, m1);
        TaskProbability probability4 = new TaskProbability(distribution3, task4, m1);
        TaskProbability probability5 = new TaskProbability(distribution3, task5, m1);

        addAll(taskProbabilityDao, probability1, probability2, probability3, probability4, probability5);
    }

    public void deleteDomain() {
        removeAll(taskProbabilityDao);
        removeAll(distributionDao);
        removeAll(processingPathDao);
        removeAll(moduleDao);
        removeAll(controllerUnitDao);
        removeAll(taskDao);
        removeAll(taskTypeDao);
        removeAll(modelDao);
    }

    private <T> void addAll(IDao<T, Long> dao, T... entites) {
        for (T o : entites) {
            dao.add(o);
        }
    }

    private <T> void removeAll(IDao<T, Long> dao) {
        List<T> entities = dao.findAll();
        for (T e : entities) {
            dao.remove(e);
        }
    }
}
