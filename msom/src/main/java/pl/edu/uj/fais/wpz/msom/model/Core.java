/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.BlockingQueue;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;

/**
 *
 * @author jarep
 */
public class Core implements Runnable {

    private final BlockingQueue<Task> tasksBlockingQueue;
    private final ProcessingUnitImpl processingUnit;
    private final int number;

    public Core(BlockingQueue<Task> tasksBlockingQueue, ProcessingUnitImpl processingUnit, int number) {
        this.number = number;
        this.tasksBlockingQueue = tasksBlockingQueue;
        this.processingUnit = processingUnit;
    }

    @Override
    public void run() {
        while (processingUnit.isActive()) {
            try {
                PrintHelper.printMsg(getName(), "CZEKAM na zadanie");
                Task taskToDo = tasksBlockingQueue.take();
                PrintHelper.printMsg(getName(), "ODEBRAŁEM zadanie, rozpoczynam przetwarzanie...");
                taskToDo.processTask();
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
        return processingUnit.getFullName() + " *** core_" + number;
    }
}
