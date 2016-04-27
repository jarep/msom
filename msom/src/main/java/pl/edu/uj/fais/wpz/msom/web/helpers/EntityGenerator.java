/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web.helpers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.IDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskDao;
import pl.edu.uj.fais.wpz.msom.dao.interfaces.TaskTypeDao;
import pl.edu.uj.fais.wpz.msom.entities.Task;
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

    public void generateDomain() {

        TaskType typeA = new TaskType("Type A", 10);
        TaskType typeB = new TaskType("Type B", 15);
        TaskType typeC = new TaskType("Type C", 50);
        TaskType typeD = new TaskType("Type D", 45);
        TaskType typeE = new TaskType("Type E", 100);

        addAll(taskTypeDao, typeA, typeB, typeC, typeD, typeE);

        Task task1 = new Task("Task 1", typeA);
        Task task2 = new Task("Task 2", typeA);
        Task task3 = new Task("Task 3", typeB);
        Task task4 = new Task("Task 4", typeA);
        Task task5 = new Task("Task 5", typeC);
        Task task6 = new Task("Task 6", typeC);
        Task task7 = new Task("Task 7", typeC);
        Task task8 = new Task("Task 8", typeE);
        Task task9 = new Task("Task 9", typeA);

        addAll(taskDao, task1, task2, task3, task4, task5, task6, task7, task8, task9);
    }

    public void deleteDomain() {
        List<Task> tasks = taskDao.findAll();
        for (Task t : tasks) {
            taskDao.remove(t);
        }

        List<TaskType> taskTypes = taskTypeDao.findAll();
        for (TaskType type : taskTypes) {
            taskTypeDao.remove(type);
        }
    }

    private <T> void addAll(IDao<T, Long> dao, T... entites) {
        for (T o : entites) {
            dao.add(o);
        }
    }
}
