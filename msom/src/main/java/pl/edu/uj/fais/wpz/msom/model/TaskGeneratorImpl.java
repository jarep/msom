/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskGenerator;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
public class TaskGeneratorImpl implements TaskGenerator {

    private final TaskGeneratorThread generator;
    private Thread generatorThread;

    public TaskGeneratorImpl(SystemStorage systemStorage, TaskService taskService) {
        this.generator = new TaskGeneratorThread(systemStorage, taskService);
    }

    @Override
    public boolean isActive() {
        return generator.isActive();
    }

    @Override
    public boolean activate() {
        if (generator.isActive()) {
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
        if (!generator.isActive()) {
            System.out.println("TASK GENERATOR - deactivating - cannot execute.");
            return false;
        } else {
            System.out.println("TASK GENERATOR - deactivating...");
            generator.deactive();
            generatorThread.interrupt();
            System.out.println("TASK GENERATOR - deactivated.");
            return true;
        }
    }

    public boolean isActivate() {
        return generator.isActive();
    }

    private void launchGenerator() {
        generator.activate();
        generatorThread = new Thread(generator);
        generatorThread.setDaemon(true);
        generatorThread.start();
    }

}
