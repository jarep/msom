/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web.api;

/**
 *
 * @author jarep
 */
public class PathReport extends AbstractReport {

    private long nextTaskDispatcherId;
    private long originTaskDispatcherId;
    private long typeId;
    private String typeName;
    private boolean processing;

    public PathReport(long simulationId, long entityId) {
        super(simulationId, entityId);
    }

    public PathReport(long nextTaskDispatcherId, long originTaskDispatcherId, long typeId, String typeName, boolean processing, long simulationId, long entityId) {
        super(simulationId, entityId);
        this.nextTaskDispatcherId = nextTaskDispatcherId;
        this.originTaskDispatcherId = originTaskDispatcherId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.processing = processing;
    }

    public long getNextTaskDispatcherId() {
        return nextTaskDispatcherId;
    }

    public void setNextTaskDispatcherId(long nextTaskDispatcherId) {
        this.nextTaskDispatcherId = nextTaskDispatcherId;
    }

    public long getOriginTaskDispatcherId() {
        return originTaskDispatcherId;
    }

    public void setOriginTaskDispatcherId(long originTaskDispatcherId) {
        this.originTaskDispatcherId = originTaskDispatcherId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isProcessing() {
        return processing;
    }

    public void setProcessing(boolean processing) {
        this.processing = processing;
    }

}
