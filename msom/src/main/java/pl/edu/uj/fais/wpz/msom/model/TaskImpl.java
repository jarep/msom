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

    private TypeImpl type;
    private ProcessingUnit lastProcessingUnit;

    private final AtomicBoolean finished = new AtomicBoolean(false);
    private final AtomicBoolean processed = new AtomicBoolean(false);
    private final AtomicInteger currentPercentage = new AtomicInteger();
    private final AtomicInteger executionCounter = new AtomicInteger();

    /**
     * Time threshold (in milliseconds) used to dynamic evaluate waiting time
     */
    private final AtomicLong timeThreshold = new AtomicLong();
    /**
     * Waiting time (in milliseconds) used to dynamic evaluate waiting time.
     */
    private final AtomicLong waitingTime = new AtomicLong();

    /**
     * Waiting time (in milliseconds) used during processing.
     */
    private final AtomicLong staticWaitingTime = new AtomicLong();
    /**
     * Processing time (in milliseconds).
     */
    private final AtomicLong processingTime = new AtomicLong();

    private final Long instanceId;

    public TaskImpl(pl.edu.uj.fais.wpz.msom.entities.Task entityObject, TaskService taskService, Long instanceId) {
        super(entityObject, taskService);
        this.instanceId = instanceId;
    }

    @Override
    protected void reloadData() {
        super.reloadData();
        cleanTimers();
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
                PrintHelper.printError(getName(), "Attempt to process already active task!");
                return false;
            } else if (finished.get()) {
                PrintHelper.printError(getName(), "Attempt to process already finished task!");
                return false;
            } else if (processed.get()) {
                PrintHelper.printError(getName(), "Attempt to process already being processed task!");
                return false;
            } else {
                lastProcessingUnit = processingUnit;
                process();
            }
        } finally {
            executionReadLock.unlock();
        }
        return true;
    }

    private void process() {
        
        currentPercentage.set(0);
        Long currentWaitingTime = (System.currentTimeMillis()) - timeThreshold.get();
        staticWaitingTime.addAndGet(currentWaitingTime);
        type.addWaitingTime(currentWaitingTime.intValue());
        processed.set(true);  // getWaitingTime() przestaje korzystac z thresholda i waiting time, zwraca bezposrednio staticWaitingTime
        type.incrementProcessingCounter();
        try {
            PrintHelper.printMsg(getName(), "Somebody is going to process me. I'm so happy!");
            int millis =(type.getDifficulty()*1000*1000) / getLastProcessingUnit().getEfficiency();
            int interval = millis / 100;
            for (int i = 0; i < 100; i++) {
                Thread.sleep(interval);
                currentPercentage.set(i);
                processingTime.addAndGet(interval);
                type.addProcessingTime(interval);
                if ((i % 10) == 0) {
                    PrintHelper.printMsg(getName(), " ... " + i + "% ...");
                }
            }
            waitingTime.set(staticWaitingTime.get());
            timeThreshold.set(System.currentTimeMillis());
            processed.set(false); // getWaitingTime() zaczyna korzystac z tresholda i waiting time, przestaje zwracac staticWaitingTime
            currentPercentage.set(0);
            PrintHelper.printMsg(getName(), "Processed count: " + executionCounter.incrementAndGet());
        } catch (InterruptedException ex) {
            PrintHelper.printAlert(getName(), "Interrupted while processing. (interrupted exception)");
        }
    }

    @Override
    public boolean finishTask() {
        if (finished.get()) {
            PrintHelper.printAlert(getName(), "Task cannot be finished once more");
            return false;
        }
        executionWriteLock.lock();
        try {
            if (finished.get()) {
                PrintHelper.printAlert(getName(), "Task cannot be finished once more");
                return false;
            } else {
                finish();
                PrintHelper.printMsg(getName(), "Finished");
                return true;
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    private void finish() {
        finished.set(true);
        processed.set(false);
        Long currentTime =  System.currentTimeMillis();
        Long currentWaitingTime = currentTime - timeThreshold.get();
        waitingTime.addAndGet(currentWaitingTime);
        staticWaitingTime.set(waitingTime.get());
        type.addWaitingTime(currentWaitingTime.intValue());
        timeThreshold.set(currentTime);
        type.incrementNumberOfFinishedTasks();
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
        if (finished.get() || processed.get()) {
            Long result = staticWaitingTime.get();
            return result;
        } else { // task is waiting
            return System.currentTimeMillis() - timeThreshold.get() + waitingTime.get();
        }
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
        type.incrementNumberOfGeneratedTasks();
        cleanTimers();
        return true;
    }

    private void cleanTimers() {
        waitingTime.set(0);
        staticWaitingTime.set(0);
        processingTime.set(0);
        timeThreshold.set(System.currentTimeMillis());
    }

    @Override
    protected boolean deactivateObject() {
        active.set(false);
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
