/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class TypeImpl extends AbstractModelObject<TaskType> implements Type {

    private final TaskTypeService taskTypeService;

    public TypeImpl(TaskType entityObject, TaskTypeService taskTypeService) {
        this.taskTypeService = taskTypeService;
        setEntityObject(entityObject);
    }

    @Override
    public String getName() {
        return getEntityObject().getName();
    }

    @Override
    public void reload() {
        reloadEntityObcject();
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            taskTypeService.refresh(getEntityObject());
        }
    }

    @Override
    public void save() {
        saveEntityObject();
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            taskTypeService.update(getEntityObject());
        }
    }

    @Override
    public int getDifficulty() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
