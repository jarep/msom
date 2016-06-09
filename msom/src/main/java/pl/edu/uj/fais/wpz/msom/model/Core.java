/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;

/**
 *
 * @author jarep
 */
public class Core implements Runnable {

    private final BlockingQueue<Task> tasksBlockingQueue;
    private final ProcessingUnit processingUnit;

    public Core(BlockingQueue<Task> tasksBlockingQueue, ProcessingUnit processingUnit) {
        this.tasksBlockingQueue = tasksBlockingQueue;
        this.processingUnit = processingUnit;
    }

    @Override
    public void run() {
        while (processingUnit.isActive()) {
            try {
                PrintHelper.printMsg(getName(), "CZEKAM na zadanie");
                Task taskToDo = tasksBlockingQueue.take();
                PrintHelper.printMsg(getName(), "ODEBRAŁEM zadanie");
            } catch (InterruptedException ex) {
                PrintHelper.printAlert(getName(), "umieram... (interrupted exception)");
//                Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        PrintHelper.printMsg(getName(), "koniec pracy");
    }

    private String getName(){
        return processingUnit.getName() +  " *** core";
    }
}
