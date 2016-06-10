/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
public class TaskImpl extends AbstractModelObject<pl.edu.uj.fais.wpz.msom.entities.Task> implements Task {

    private final TaskService taskService;
    private Type type;

    private final AtomicBoolean active = new AtomicBoolean(false);
    private final AtomicBoolean finished = new AtomicBoolean(false);
    private final AtomicInteger currentPercentage = new AtomicInteger();
    private final AtomicInteger executionCounter = new AtomicInteger();

    private final ReentrantReadWriteLock executionLock = new ReentrantReadWriteLock();
    private final Lock executionReadLock = executionLock.readLock();
    private final Lock executionWriteLock = executionLock.writeLock();

    private final AtomicLong waitingTime = new AtomicLong();
    private final AtomicLong processingTime = new AtomicLong();

    private final Long instanceId;

    public TaskImpl(pl.edu.uj.fais.wpz.msom.entities.Task entityObject, TaskService TaskService, Long instanceId) {
        this.taskService = TaskService;
        this.instanceId = instanceId;
        setEntityObject(entityObject);
    }

    @Override
    public boolean reload() {
        if (active.get()) {
            return false;
        } else {
            executionWriteLock.lock();
            try {
                reloadEntityObcject();
            } finally {
                executionWriteLock.unlock();
            }
            return true;
        }
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            taskService.refresh(getEntityObject());
        }
    }

    @Override
    public boolean save() {
        executionWriteLock.lock();
        try {
            if (active.get()) {
                PrintHelper.printAlert(getName(), "Nie mozna zapisac zadania gdy jest aktywne.");
                return false;
            } else {
                saveEntityObject();
                PrintHelper.printMsg(getName(), "Zapisano zadanie.");
                return true;
            }
        } finally {
            executionWriteLock.unlock();
        }

    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            taskService.update(getEntityObject());
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    public boolean setType(TypeImpl type) {
        executionWriteLock.lock();
        try {
            if (active.get()) {
                return false;
            } else if (type == null || getEntityObject() == null) {
                return false;
            } else {
                this.type = type;
                getEntityObject().setTaskType(type.getEntityObject());
                return true;
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    @Override
    public String getName() {
        return getEntityObject().getName() + ":" + instanceId;
    }

    @Override
    public Integer getDifficulty() {
        return getEntityObject().getTaskType().getDifficulty();
    }

    @Override
    public boolean processTask() {
        executionWriteLock.lock();
        try {
            if (!active.get()) {
                PrintHelper.printError(getName(), "Proba przetwarzania nieaktywnego zadania!");
                return false;
            } else if (finished.get()) {
                PrintHelper.printError(getName(), "Proba wykonania zakonczonego zadania!");
                return false;
            } else {
                currentPercentage.set(0);
                try {
                    PrintHelper.printMsg(getName(), "ktos mnie bedzie przetwarzal...");
                    int millis = 1000 * type.getDifficulty();
                    int interval = millis / 100;
                    // AKTUALIZACJA LACZNEGO CZASU OCZEKIWANIA
                    // ZATRZYMANIE POMIARU CZASU OCZEKIWANIA
                    // moze tutaj przekazanie danych - statystyk do obiektu typu?
                    // START POMIARU CZASU PRZETWARZANIA
                    for (int i = 0; i < 100; i++) {
                        Thread.sleep(interval);
                        currentPercentage.set(i);
                        if ((i % 10) == 0) {
                            PrintHelper.printMsg(getName(), " ... " + i + "% ...");
                        }
                    }
                    // AKTUALIZACJA LACZNEGO CZASU PRZETWARZANIA (zadanie moze byc wielokrotnie przetwarzane)
                    // moze tutaj przekazanie danych - statystyk do obiektu typu?
                    PrintHelper.printMsg(getName(), "wykonano mnie po raz: " + executionCounter.incrementAndGet());
                    currentPercentage.set(0);
                    // WZNOWIENIE POMIARU CZASU OCZEKIWANIA
                    return true;
                } catch (InterruptedException ex) {
                    PrintHelper.printAlert(getName(), "Przerwano w trakcie przetwarzania. (interrupted exception)");
                }
                return false;
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    @Override
    public void finishTask() {
        executionWriteLock.lock();
        try {
            finished.set(true);
            // KONIEC POMIARU CZASU OCZEKIWANIA
            // moze tutaj przekazanie danych - statystyk do obiektu typu?
            PrintHelper.printMsg(getName(), "zakonczono mnie...");
        } finally {
            executionWriteLock.unlock();
        }
    }

    @Override
    public boolean isFinished() {
        return finished.get();
    }

    @Override
    public Long getProcessingTime() {
        return processingTime.get();
    }

    @Override
    public Long getWaitingTime() {
        return waitingTime.get();
    }

    @Override
    public ProcessingUnitImpl getLastProcessingUnit() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getExecutionCounter() {
        return executionCounter.get();
    }

    @Override
    public double getPercentageOfCurrentExecution() {
        return currentPercentage.get();
    }

    @Override
    public boolean isActive() {
        return active.get();
    }

    @Override
    public boolean activate() {
        executionWriteLock.lock();
        try {
            if (active.get()) {
                PrintHelper.printMsg(getName(), "Nie mozna ponownie aktywowac zadania.");
                return false;
            } else {
                active.set(true);
                // START POMIARU CZASU OCZEKIWANIA
                PrintHelper.printMsg(getName(), "Zadanie aktywowane - gotowe do przetwarzania.");
                return true;
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    @Override
    public boolean deactivate() {
        executionWriteLock.lock();
        try {
            if (active.get()) {
                PrintHelper.printMsg(getName(), "Nie mozna ponownie deaktywowac zadania.");
                return false;
            } else {
                active.set(false);
                // CZY KONCZYC POMIAR CZASU? - czy tylko w metodzie finishTask()
                // Deaktywowanie zadania odblokowuje jego edycje, ale nie zakancza zadania,
                // wiec raczej deaktywacja nie powinna wplywac na czas oczekiwania
                PrintHelper.printMsg(getName(), "Zadanie deaktywowane.");
                return true;
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    @Override
    public String toString() {
        return "TaskImpl{" + "name=" + getName() + ", active=" + active + ", type=" + type.getName() + '}';
    }

}
