/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

import java.util.List;
import java.util.Objects;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskTypeDao;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.integration.DomainAwareBase;

/**
 *
 * @author jaroslaw
 */
@ContextConfiguration("/testApplicationContext.xml")
public class TaskDaoImplTest extends DomainAwareBase {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskTypeDao taskTypeDao;

    public TaskDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    public void setUp() {
        TaskType type1 = new TaskType("Typ a", 50);
        taskTypeDao.add(type1);
        TaskType type2 = new TaskType("Typ b", 10);
        taskTypeDao.add(type2);
        TaskType type3 = new TaskType("Typ c", 70);
        taskTypeDao.add(type3);

        Task task = new Task("zadanie 1", type1);
        taskDao.add(task);
        task = new Task("zadanie 2", type1);
        taskDao.add(task);
        task = new Task("zadanie 3", type2);
        taskDao.add(task);
        task = new Task("zadanie 4", type1);
        taskDao.add(task);
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
        int size = taskDao.list().size();

        TaskType type = new TaskType("Typ x", 40);
        taskTypeDao.add(type);

        Task task = new Task("Task 1", type);
        taskDao.add(task);

        assertTrue(size < taskDao.list().size());
    }

    @Test
    public void testUpdate() {
        List<TaskType> types = taskTypeDao.list();

        Task task = new Task("Task 2", types.get(0));
        taskDao.add(task);

        // update task
        task.setName("Task 2b");
        task.setTaskType(types.get(1));
        taskDao.update(task);

        Task found = taskDao.find(task.getId());
        assertTrue(found.getName().equals(task.getName()));
        assertTrue(Objects.equals(found.getTaskType().getId(), task.getTaskType().getId()));
    }

    @Test
    public void testFind() {
        TaskType type = new TaskType("Typ ALFA", 30);
        taskTypeDao.add(type);

        Task task = new Task("Task 3", type);
        taskDao.add(task);

        Task found = taskDao.find(task.getId());
        assertEquals(task, found);
    }

    @Test
    public void testList() {
        List<Task> tasks = taskDao.list();

        TaskType taskType1 = new TaskType("Typ f", 45);
        taskTypeDao.add(taskType1);

        Task task1 = new Task("Task ALFA", taskType1);
        taskDao.add(task1);
        Task task2 = new Task("Task BETA", taskType1);
        taskDao.add(task2);

        List<Task> found = taskDao.list();
        
        assertEquals(tasks.size() + 2, found.size());
        assertTrue(found.contains(task1));
        assertTrue(found.contains(task2));
    }

    @Test
    public void testRemove() {
        TaskType type = new TaskType("Typ xyz", 48);
        taskTypeDao.add(type);

        Task task = new Task("Task XYZ", type);
        taskDao.add(task);

        assertTrue(taskDao.removeTask(task));

    }

}
