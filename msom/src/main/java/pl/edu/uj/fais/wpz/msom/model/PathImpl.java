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
public class PathImpl extends ActivatableAbstractModelObject<ProcessingPath, ProcessingPathService> implements Path {

    private final Type type;
    private final TaskDispatcher nextTaskDispatcher;
    private final TaskDispatcher previousTaskDispatcher;
    private String name;
    // main service as a "service" in abstract class

    public PathImpl(ProcessingPath entityObject, Type type, TaskDispatcher previosTaskDispatcher, TaskDispatcher nextTaskDispatcher, ProcessingPathService pathService) {
        super(entityObject, pathService);
        this.type = type;
        this.previousTaskDispatcher = previosTaskDispatcher;
        this.nextTaskDispatcher = nextTaskDispatcher;
        initializeName();
    }

    private void initializeName() {
        name = "[" + getPreviousTaskDispatcher().getName()
                + "] -> {" + getType().getName()
                + "} -> [";
        if (getPreviousTaskDispatcher().getId().equals(getNextTaskDispatcher().getId())) {
            name += " -- finish -- " ;
        } else {
            name += getNextTaskDispatcher().getName();
        }
        name += "]";
        if (isProcessing()) {
            name += " {process + dispatch}";
        } else {
            name += " {only dispatch}";
        }
    }

    @Override
    public boolean reload() {
        reloadEntityObcject();
        return true;
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
    public TaskDispatcher getPreviousTaskDispatcher() {
        return previousTaskDispatcher;
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

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    
    
}
