/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;
import pl.edu.uj.fais.wpz.msom.helpers.PrintHelper;
import pl.edu.uj.fais.wpz.msom.model.interfaces.IActivatable;
import pl.edu.uj.fais.wpz.msom.service.interfaces.IService;

/**
 *
 * @author jaroslaw
 * @param <Entity>
 * @param <Service>
 */
public abstract class ActivatableAbstractModelObject<Entity extends AbstractEntity, Service extends IService<Entity>> extends Activatable implements IActivatable {

    /**
     * Entity object.
     */
    private final Entity entityObject;
    /**
     * Service for entity object.
     */
    protected final Service service;

    public ActivatableAbstractModelObject(Entity entityObject, Service service) {
        this.entityObject = entityObject;
        this.service = service;
    }

    /**
     * Thread-safe method to get entity object.
     *
     * @return entity object
     */
    protected Entity getEntityObject() {
        executionReadLock.lock();
        try {
            return entityObject;
        } finally {
            executionReadLock.unlock();
        }
    }

    /**
     * Thread-safe method to save entity object from the database.
     */
    protected void saveEntityObject() {
        executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Saving object to database");
                service.update(entityObject);
                PrintHelper.printMsg(getName(), "Object saved to database");
                return true;
            }
        }, executionWriteLock);
    }

    /**
     * Thread-safe method to get id of entity object.
     *
     * @return id of entity object
     */
    public Long getId() {
        executionReadLock.lock();
        try {
            return entityObject.getId();
        } finally {
            executionReadLock.unlock();
        }
    }

    protected Service getService() {
        return service;
    }

    /**
     * Thread-safe method to reload entity object from the database.
     */
    protected void reloadEntityObcject() {
        executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Loading object from database");
                service.refresh(entityObject);
                PrintHelper.printMsg(getName(), "Object loaded from database");
                return true;
            }
        }, executionWriteLock);
    }

    /**
     * Thread-safe method to reload data from the database.
     *
     * @return
     */
    public boolean reload() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Reloading object");
                reloadData();
                PrintHelper.printMsg(getName(), "Object reloaded");
                return true;
            }
        }, executionWriteLock);
    }

    /**
     * Reload data from the database (should be called from thread-safe method).
     *
     * @see #reload()
     */
    protected void reloadData() {
        reloadEntityObcject();
    }

    /**
     * Thread-safe method to save data to the database.
     *
     * @return
     */
    public boolean save() {
        return executeIfNonActive(new Executable() {

            @Override
            public boolean execute() {
                PrintHelper.printMsg(getName(), "Saving object");
                boolean result = saveData();
                PrintHelper.printMsg(getName(), "Object saved");
                return result;
            }
        }, executionWriteLock);
    }

    /**
     * Save data to the database (should be called from thread-safe method).
     *
     * @see #save()
     * @return
     */
    protected boolean saveData() {
        saveEntityObject();
        return true;
    }

    /**
     * Get identifying string of this object.
     *
     * @return
     */
    public abstract String getName();

}
