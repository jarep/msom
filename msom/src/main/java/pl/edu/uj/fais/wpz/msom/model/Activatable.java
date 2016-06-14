/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.IActivatable;

/**
 *
 * @author jaroslaw
 */
public abstract class Activatable implements IActivatable {

    protected final ReentrantReadWriteLock executionLock = new ReentrantReadWriteLock();
    protected final Lock executionReadLock = executionLock.readLock();
    protected final Lock executionWriteLock = executionLock.writeLock();

    protected final AtomicBoolean active = new AtomicBoolean(false);

    /**
     * Check if this object is active. Active object can not be changed -
     * prevents changes in structure during simulation.
     *
     * @return
     */
    @Override
    public boolean isActive() {
        return active.get();
    }

    /**
     * Thread-safe method to activate object. Active object can not be changed -
     * prevents changes in structure during simulation.
     *
     * @return {@code true} if successfully activated, {@code false} if it was
     * already activated or can not be activate.
     */
    @Override
    public boolean activate() {
        if (active.get()) {
            PrintHelper.printMsg(toString(), "Nie mozna ponownie aktywowac.");
            return false;
        }
        executionWriteLock.lock();
        try {
            if (active.get()) {
                PrintHelper.printMsg(toString(), "Nie mozna ponownie aktywowac.");
                return false;
            } else if (activateObject()) {
                PrintHelper.printMsg(toString(), "Aktywowano.");
                return true;
            } else {
                PrintHelper.printAlert(toString(), "Nie udalo sie aktywowac.");
                return false;
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    /**
     * Activate object (should be called from thread-safe method). Active object
     * can not be changed.
     *
     * @see #activate() 
     * @return {@code true} if successfully activated, {@code false} if it was
     * already activated or can not be activate.
     */
    protected boolean activateObject() {
        active.set(true);
        return true;
    }

    /**
     * Thread-safe method to deactivate object. Inactive object can be changed -
     * stops execution and allows changes.
     *
     * @return {@code true} if successfully deactivated, {@code false} if it was
     * already deactivated or can not be deactivate.
     */
    @Override
    public boolean deactivate() {
        if (!active.get()) {
            PrintHelper.printMsg(toString(), "Nie mozna ponownie deaktywowac.");
            return false;
        }
        executionWriteLock.lock();
        try {
            if (!active.get()) {
                PrintHelper.printMsg(toString(), "Nie mozna ponownie deaktywowac.");
                return false;
            } else if (deactivateObject()) {
                PrintHelper.printMsg(toString(), "Deaktywowano.");
                return true;
            } else {
                PrintHelper.printAlert(toString(), "Nie udalo sie deaktywowac...");
                return false;
            }
        } finally {
            executionWriteLock.unlock();
        }
    }

    /**
     * Dectivate object (should be called from thread-safe method). Inactive
     * object can be changed.
     *
     * @see #deactivate() 
     * @return {@code true} if successfully deactivated, {@code false} if it was
     * already deactivated or can not be deactivate.
     */
    protected boolean deactivateObject() {
        active.set(false);
        return true;
    }

    protected boolean executeIfNonActive(Executable e, Lock lock) {
        if (active.get()) {
            PrintHelper.printAlert(toString(), "Nie mozna wykonac - obiekt aktywny ...");
            return false;
        }
        lock.lock();
        try {
            if (active.get()) {
                PrintHelper.printAlert(toString(), "Nie mozna wykonac - obiekt aktywny ...");
                return false;
            } else {
                PrintHelper.printMsg(toString(), "Wykonuje ...");
                e.execute();
                PrintHelper.printMsg(toString(), "Wykonano.");
            }
            return true;
        } finally {
            lock.unlock();
        }
    }
}
