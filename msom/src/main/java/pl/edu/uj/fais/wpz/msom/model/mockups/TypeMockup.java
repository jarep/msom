/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model.mockups;

import pl.edu.uj.fais.wpz.msom.entities.TaskType;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.TaskTypeService;

/**
 *
 * @author jarep
 */
public class TypeMockup extends AbstractModelMockupObject<TaskType> implements Type {

    private final TaskTypeService taskTypeService;

    public TypeMockup(TaskType entityObject, TaskTypeService taskTypeService) {
        this.taskTypeService = taskTypeService;
        setEntityObject(entityObject);
    }

    @Override
    public String getName() {
        return getEntityObject().getName();
    }

    @Override
    public boolean reload() {
        reloadEntityObcject();
        return true;
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            taskTypeService.refresh(getEntityObject());
        }
    }

    @Override
    public boolean save() {
        saveEntityObject();
        return true;
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

    @Override
    public boolean activate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deactivate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
