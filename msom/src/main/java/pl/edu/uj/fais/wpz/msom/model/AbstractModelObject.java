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

    protected Entity getEntityObject() {
        return entityObject;
    }

    protected void setEntityObject(Entity entityObject) {
        if (entityObject instanceof AbstractEntity) {
            this.entityObject = entityObject;
            reload();
        }
    }

    public Long getId() {
        return ((AbstractEntity) entityObject).getId();
    }
    
    public abstract void reload();
}
