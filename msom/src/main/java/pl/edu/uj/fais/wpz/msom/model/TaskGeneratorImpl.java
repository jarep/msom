/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.ArrayList;
import java.util.List;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskGenerator;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
public class TaskGeneratorImpl implements TaskGenerator {

    private List<TaskGeneratorThread> generators = new ArrayList<>();
    private List<Thread> generatorThreads= new ArrayList<>();
    final private List<TaskEntityWrapper> taskEntityWrappers;

    public TaskGeneratorImpl(SystemStorage systemStorage) {
        taskEntityWrappers = systemStorage.getTaskEntityWrappers();
        for (TaskEntityWrapper taskEntityWrapper : taskEntityWrappers) {
            if(taskEntityWrapper.getDistributionEntity().getParameterA()>=1&&taskEntityWrapper.getDistributionEntity().getParameterB()>=1)  //Tymczasowa ochrona przed zlymi danymi
                generators.add(new TaskGeneratorThread(systemStorage, taskEntityWrapper ));
        }
    }

    @Override
    public boolean isActive() {
        return isActivate();
    }

    @Override
    public boolean activate() {
        if (isActivate()) {
            System.out.println("TASK GENERATOR - reactivating - cannot execute");
            return false;
        } else {
            System.out.println("TASK GENERATOR - activating");
            launchGenerator();
            System.out.println("TASK GENERATOR - activated");
            return true;
        }
    }

    @Override
    public boolean deactivate() {
        if (!isActivate()) {
            System.out.println("TASK GENERATOR - deactivating - cannot execute.");
            return false;
        } else {
            for (TaskGeneratorThread generator : generators) {
                System.out.println("TASK GENERATOR - deactivating gen...");
                generator.deactive();

        }
            for (Thread generatorThread  : generatorThreads) {
                generatorThread.interrupt();
                System.out.println("TASK GENERATOR - deactivated.");
            }
            generatorThreads.clear();
            return true;
        }
    }

    public boolean isActivate() {
        for (TaskGeneratorThread generator : generators) {
            if(!generator.isActive())
                return false;
        }
        return true;
    }

    private void launchGenerator() {
        for (TaskGeneratorThread generator : generators) {
            generator.activate();
            Thread t = new Thread(generator);
            t.setDaemon(true);
            generatorThreads.add(t);
            t.start();
        }

    }

}
