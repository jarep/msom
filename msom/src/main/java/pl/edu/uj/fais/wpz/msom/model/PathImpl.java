/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.model;

import pl.edu.uj.fais.wpz.msom.entities.ProcessingPath;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Path;
import pl.edu.uj.fais.wpz.msom.model.interfaces.TaskDispatcher;
import pl.edu.uj.fais.wpz.msom.model.interfaces.Type;
import pl.edu.uj.fais.wpz.msom.service.interfaces.ProcessingPathService;

/**
 *
 * @author jarep
 */
public class PathImpl extends AbstractModelObject<ProcessingPath> implements Path {

    private final Type type;
    private final TaskDispatcher nextTaskDispatcher;
    private final ProcessingPathService pathService;

    public PathImpl(ProcessingPath entityObject, Type type, TaskDispatcher forwardTo, ProcessingPathService pathService) {
        this.type = type;
        this.nextTaskDispatcher = forwardTo;
        this.pathService = pathService;
        setEntityObject(entityObject);
    }

    @Override
    public boolean reload() {
        reloadEntityObcject();
        return true;
    }

    private void reloadEntityObcject() {
        if (getEntityObject() != null) {
            pathService.refresh(getEntityObject());
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public boolean isProcessing() {
        return getEntityObject().getProcessing();
    }

    @Override
    public TaskDispatcher getNextTaskDispatcher() {
        return nextTaskDispatcher;
    }

    @Override
    public boolean save() {
        saveEntityObject();
        return true;
    }

    private void saveEntityObject() {
        if (getEntityObject() != null) {
            pathService.update(getEntityObject());
        }
    }
}
