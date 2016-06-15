/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.ProcessingUnit;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Task;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskService;

/**
 *
 * @author jarep
 */
public class TaskImpl extends ActivatableAbstractModelObject<pl.edu.uj.fais.wpz.msom.entities.Task, TaskService> implements Task {

    private Type type;
    private ProcessingUnit lastProcessingUnit;

    private final AtomicBoolean finished = new AtomicBoolean(false);
    private final AtomicInteger currentPercentage = new AtomicInteger();
    private final AtomicInteger executionCounter = new AtomicInteger();

    private final AtomicLong waitingTime = new AtomicLong();
    private final AtomicLong processingTime = new AtomicLong();

    private final Long instanceId;

    public TaskImpl(pl.edu.uj.fais.wpz.msom.entities.Task entityObject, TaskService taskService, Long instanceId) {
        super(entityObject, taskService);
        this.instanceId = instanceId;
    }

    @Override
    protected void reloadData() {
        super.reloadData();
        // clean time and others?
    }

    @Override
    public Type getType() {
        executionReadLock.lock();
        try {
            return type;
        } finally {
            executionReadLock.unlock();
        }
    }

    public boolean setType(final TypeImpl newType) {
        if (newType != null) {
            return executeIfNonActive(new Executable() {

                @Override
                public boolean execute() {
                    type = newType;
                    getEntityObject().setTaskType(newType.getEntityObject());
                    return true;
                }
            }, executionWriteLock);
        } else {
            return false;
        }
    }

    @Override
    public String getName() {
        executionReadLock.lock();
        try {
            return getEntityObject().getName() + ":" + instanceId;
        } finally {
            executionReadLock.unlock();
        }
    }

    @Override
    public Integer getDifficulty() {
        executionReadLock.lock();
        try {
            return getEntityObject().getTaskType().getDifficulty();
        } finally {
            executionReadLock.unlock();
        }
    }

    @Override
    public boolean processTask(ProcessingUnit processingUnit) {
        executionReadLock.lock(); // we only change atomic variables, others can read
        try {
            if (!active.get()) {
                PrintHelper.printError(getName(), "Proba przetwarzania nieaktywnego zadania!");
                return false;
            } else if (finished.get()) {
                PrintHelper.printError(getName(), "Proba wykonania zakonczonego zadania!");
                return false;
            } else {
                process();
            }
        } finally {
            executionReadLock.unlock();
        }
        executionWriteLock.lock();
        try {
            lastProcessingUnit = processingUnit;
            return true;
        } finally {
            executionWriteLock.unlock();
        }
    }

    private void process() {
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
        } catch (InterruptedException ex) {
            PrintHelper.printAlert(getName(), "Przerwano w trakcie przetwarzania. (interrupted exception)");
        }
    }

    @Override
    public boolean finishTask() {
        if (finished.get()) {
            PrintHelper.printAlert(getName(), "Nie mozna ponownie zakonczyc zadania.");
            return false;
        }
        executionWriteLock.lock();
        try {
            if (finished.get()) {
                PrintHelper.printAlert(getName(), "Nie mozna ponownie zakonczyc zadania.");
                return false;
            } else {
                finish();
                PrintHelper.printMsg(getName(), "zakonczono mnie...");
                return true;
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    private void finish() {
        finished.set(true);
        // KONIEC POMIARU CZASU OCZEKIWANIA
        // moze tutaj przekazanie danych - statystyk do obiektu typu?
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
    public ProcessingUnit getLastProcessingUnit() {
        executionReadLock.lock();
        try {
            return lastProcessingUnit;
        } finally {
            executionReadLock.unlock();
        }
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
    protected boolean activateObject() {
        active.set(true);
        // START POMIARU CZASU OCZEKIWANIA
        return true;
    }

    @Override
    protected boolean deactivateObject() {
        active.set(false);
        // CZY KONCZYC POMIAR CZASU? - czy tylko w metodzie finishTask()
        // Deaktywowanie zadania odblokowuje jego edycje, ale nie zakancza zadania,
        // wiec raczej deaktywacja nie powinna wplywac na czas oczekiwania
        return true;
    }

    @Override
    public String toString() {
        executionReadLock.lock();
        try {
            String string = "TaskImpl{" + "name=" + getName() + ", active=" + active.get() + ", type=";
            if (type == null) {
                string = string + "---";
            } else {
                string = string + type.getName();
            }
            string += "}";
            return string;
        } finally {
            executionReadLock.unlock();
        }
    }

    public String getShortName() {
        executionReadLock.lock();
        try {
            String shortName = getName();
            if (type == null) {
            } else {
                shortName += ", " + type.getName();
            }
            return shortName;
        } finally {
            executionReadLock.unlock();
        }
    }

}
