/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.dao;

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
public class TaskTypeDaoImplTest extends DomainAwareBase {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private TaskTypeDao taskTypeDao;

    public TaskTypeDaoImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @After
    public void tearDown() {
    }

    public void setUp() {
        TaskType type = new TaskType("Typ a", 50);
        taskTypeDao.add(type);
        type = new TaskType("Typ b", 10);
        taskTypeDao.add(type);
    }

    @Override
    public void deleteAllDomainEntities() {
        super.deleteAllDomainEntities();
        setUp();
    }

    @Test
    public void testAdd() {
        int size = taskTypeDao.list().size();

        TaskType taskType = new TaskType("Typ c", 20);
        taskTypeDao.add(taskType);

        assertTrue(size < taskTypeDao.list().size());
    }

    @Test
    public void testUpdate() {
        TaskType taskType = new TaskType("Typ d", 20);
        taskTypeDao.add(taskType);

        // update type
        taskType.setDifficulty(100);
        taskType.setName("Typ d2");
        taskTypeDao.update(taskType);

        TaskType found = taskTypeDao.find(taskType.getId());
        assertTrue(found.getDifficulty() == 100);
        assertTrue(found.getName().equals("Typ d2"));
    }

    @Test
    public void testFind() {
        TaskType taskType = new TaskType("Typ e", 40);
        taskTypeDao.add(taskType);
        TaskType found = taskTypeDao.find(taskType.getId());
        assertEquals(taskType, found);
    }

    @Test
    public void testList() {
        List<TaskType> taskTypes = taskTypeDao.list();

        TaskType taskType1 = new TaskType("Typ f", 45);
        taskTypeDao.add(taskType1);
        TaskType taskType2 = new TaskType("Typ g", 55);
        taskTypeDao.add(taskType2);

        List<TaskType> found = taskTypeDao.list();

        assertEquals(taskTypes.size() + 2, found.size());

        assertTrue(found.contains(taskType1));
        assertTrue(found.contains(taskType2));
    }

    @Test
    public void testRemove() {

        TaskType type = new TaskType("Typ xyz", 48);
        taskTypeDao.add(type);

        Task task = new Task("Task XYZ", type);
        taskDao.add(task);

        // try to remove -> shouldn't work
        boolean result = taskTypeDao.removeTaskType(type);
        assertFalse(result);

        // remove stuff
        taskDao.remove(task);

        // should work -> employee is now free
        assertTrue(taskTypeDao.removeTaskType(type));

    }

}
