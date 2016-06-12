/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.entities.abstracts.AbstractEntity;

/**
 *
 * @author jarep
 * @param <Entity>
 */
public abstract class AbstractModelObject<Entity> {

    private Entity entityObject;

    /**
     *
     * @return entity object
     */
    protected Entity getEntityObject() {
        return entityObject;
    }

    /**
     *
     * @param entityObject to set entity object
     */
    protected void setEntityObject(Entity entityObject) {
        if (entityObject instanceof AbstractEntity) {
            this.entityObject = entityObject;
            reload();
        }
    }

    /**
     *
     * @return id of entity
     */
    public Long getId() {
        return ((AbstractEntity) entityObject).getId();
    }
    
    /**
     * refresh and reload state
     */
    public abstract void reload();
}
