/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;

/**
 *
 * @author jarep
 */
public class Core implements Runnable {

    private final BlockingQueue<Task> tasksBlockingQueue;
    private final BlockingQueue<Task> processingTasksBlockingQueue;
    private final AtomicInteger queueValue;
    private final ProcessingUnitImpl processingUnit;
    private final int number;

    public Core(BlockingQueue<Task> tasksBlockingQueue, BlockingQueue<Task> processingTasksBlockingQueue, ProcessingUnitImpl processingUnit, AtomicInteger queueValue, int number) {
        this.number = number;
        this.queueValue = queueValue;
        this.tasksBlockingQueue = tasksBlockingQueue;
        this.processingTasksBlockingQueue = processingTasksBlockingQueue;
        this.processingUnit = processingUnit;
    }

    @Override
    public void run() {
        while (processingUnit.isActive()) {
            try {
                PrintHelper.printMsg(getName(), "CZEKAM na zadanie");
                Task taskToDo = tasksBlockingQueue.take();
                processingTasksBlockingQueue.add(taskToDo);
                queueValue.addAndGet(0 - taskToDo.getDifficulty());
                PrintHelper.printMsg(getName(), "ODEBRAŁEM zadanie, rozpoczynam przetwarzanie...");
                taskToDo.processTask(processingUnit);
                PrintHelper.printMsg(getName(), "zakonczylem przetwarzanie zadania, zwracam do processing unit.");
                processingUnit.returnTask(taskToDo);
            } catch (InterruptedException ex) {
                PrintHelper.printAlert(getName(), "umieram... (interrupted exception)");
//                Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        PrintHelper.printMsg(getName(), "koniec pracy");
    }

    private String getName() {
        return processingUnit.toString() + " *** core_" + number;
    }
}
