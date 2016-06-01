/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

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
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author paweldylag
 */
@ContextConfiguration("/testApplicationContext.xml")
public class TaskImplTest extends DomainAwareBase {
    
    private final String TEST_TASK_TYPE_NAME = "testTaskType";
    private final String TEST_TASK_NAME = "testTask";
    private final int TEST_TASK_DIFFICULTY = 1000;
    
    @Autowired
    private TaskTypeService taskTypeService;
    
    @Autowired
    private TaskService taskService;
    
    private Task task;
    private TaskType taskType;
    
    public TaskImplTest() {
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
        TaskImpl taskObj = new TaskImpl(task, taskService, taskTypeService);
        taskObj.queueTask();
        taskObj.processTask();
        taskObj.finishTask();
        assertTrue(taskObj.isFinished());
        taskObj.reload();
        assertTrue(!taskObj.isFinished());
        assertTrue(taskObj.getProcessingTime() == 0);
        assertTrue(taskObj.getWaitingTime() == 0);
    }

    /**
     * Test of save method, of class TaskMockup.
     */
    @Test
    public void testSave() {
        TaskImpl taskObj = new TaskImpl(task, taskService, taskTypeService);
        // TODO: implement this
        taskObj.reload();
    }

    /**
     * Test of getType method, of class TaskMockup.
     */
    @Test
    public void testGetType() {
       TaskImpl taskMockupObject = new TaskImpl(task, taskService, taskTypeService);
       assertTrue(taskMockupObject.getType().getId().equals(this.taskType.getId()));
    }

    /**
     * Test of getName method, of class TaskMockup.
     */
    @Test
    public void testGetName() {
        TaskImpl taskMockupObject = new TaskImpl(task, taskService, taskTypeService);
        assertTrue(taskMockupObject.getName().equals(TEST_TASK_NAME));
    }

    /**
     * Test of getDifficulty method, of class TaskMockup.
     */
    @Test
    public void testGetDifficulty() {
        TaskImpl taskMockupObject = new TaskImpl(task, taskService, taskTypeService);
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
       TaskImpl taskObj = new TaskImpl(task, taskService, taskTypeService); 
       taskObj.reload();
       assertTrue(taskObj.getWaitingTime() == 0);
       assertTrue(taskObj.getProcessingTime() == 0);
       assertTrue(!taskObj.isFinished());
       taskObj.queueTask();
       try {
           Thread.sleep(100);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
       assertTrue(taskObj.getWaitingTime() == 0);
       assertTrue(taskObj.getProcessingTime() == 0);
       assertTrue(!taskObj.isFinished());
       taskObj.processTask();
         try {
           Thread.sleep(100);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
       assertTrue(taskObj.getWaitingTime() != 0);
       assertTrue(taskObj.getProcessingTime() == 0);
       taskObj.finishTask();
         try {
           Thread.sleep(100);
       } catch (InterruptedException e){
           e.printStackTrace();
       }
       assertTrue(taskObj.getWaitingTime() != 0);
       assertTrue(taskObj.getProcessingTime() != 0);
    }

  
    
}
