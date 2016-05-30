/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import pl.edu.uj.fais.wpz.msom.entities.Task;
import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.integration.DomainAwareBase;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author paweldylag
 */
@ContextConfiguration("/testApplicationContext.xml")
public class TaskMockupTest extends DomainAwareBase {
    
    private final String TEST_TASK_TYPE_NAME = "testTaskType";
    private final String TEST_TASK_NAME = "testTask";
    private final int TEST_TASK_DIFFICULTY = 1000;
    
    @Autowired
    private TaskTypeService taskTypeService;
    
    @Autowired
    private TaskService taskService;
    
    private Task task;
    private TaskType taskType;
    
    public TaskMockupTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.taskType = new TaskType(TEST_TASK_TYPE_NAME, TEST_TASK_DIFFICULTY);
        if (taskTypeService.add(taskType)) {
            this.task = new Task(TEST_TASK_NAME, taskType);
            if (!taskService.add(task)) {
                fail("Cannot add task");
            } 
        } else fail("Cannot add task type");
    }
    
    @After
    public void tearDown() {
    }
    
    

    /**
     * Test of reload method, of class TaskMockup.
     */
    @Test
    public void testReload() {
        TaskMockup taskMockupObject = new TaskMockup(task, taskService, taskTypeService);
        taskMockupObject.queueTask();
        taskMockupObject.processTask();
        taskMockupObject.finishTask();
        assertTrue(taskMockupObject.isFinished());
        taskMockupObject.reload();
        assertTrue(!taskMockupObject.isFinished());
        assertTrue(taskMockupObject.getProcessingTime() == 0);
        assertTrue(taskMockupObject.getWaitingTime() == 0);
    }

    /**
     * Test of save method, of class TaskMockup.
     */
    @Test
    public void testSave() {
        TaskMockup taskMockupObject = new TaskMockup(task, taskService, taskTypeService);
        // TODO: implement this
        taskMockupObject.reload();
    }

    /**
     * Test of getType method, of class TaskMockup.
     */
    @Test
    public void testGetType() {
       TaskMockup taskMockupObject = new TaskMockup(task, taskService, taskTypeService);
       assertTrue(taskMockupObject.getType().getId().equals(this.taskType.getId()));
    }

    /**
     * Test of getName method, of class TaskMockup.
     */
    @Test
    public void testGetName() {
        TaskMockup taskMockupObject = new TaskMockup(task, taskService, taskTypeService);
        assertTrue(taskMockupObject.getName().equals(TEST_TASK_NAME));
    }

    /**
     * Test of getDifficulty method, of class TaskMockup.
     */
    @Test
    public void testGetDifficulty() {
        TaskMockup taskMockupObject = new TaskMockup(task, taskService, taskTypeService);
        assertTrue(taskMockupObject.getDifficulty() == TEST_TASK_DIFFICULTY);
    }

    /**
     * Test of queueTask method, of class TaskMockup.
     */
    @Test
    public void testQueueTask() {
      // todo
    }

    /**
     * Test of processTask method, of class TaskMockup.
     */
    @Test
    public void testProcessingTask() {
       TaskMockup taskMockupObject = new TaskMockup(task, taskService, taskTypeService); 
       taskMockupObject.reload();
       assertTrue(taskMockupObject.getWaitingTime() == 0);
       assertTrue(taskMockupObject.getProcessingTime() == 0);
       assertTrue(!taskMockupObject.isFinished());
       taskMockupObject.queueTask();
       try {
           Thread.sleep(100);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
       assertTrue(taskMockupObject.getWaitingTime() == 0);
       assertTrue(taskMockupObject.getProcessingTime() == 0);
       assertTrue(!taskMockupObject.isFinished());
       taskMockupObject.processTask();
         try {
           Thread.sleep(100);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
       assertTrue(taskMockupObject.getWaitingTime() != 0);
       assertTrue(taskMockupObject.getProcessingTime() == 0);
       taskMockupObject.finishTask();
         try {
           Thread.sleep(100);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
       assertTrue(taskMockupObject.getWaitingTime() != 0);
       assertTrue(taskMockupObject.getProcessingTime() != 0);
    }

  
    
}
