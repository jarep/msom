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
public class AbstractReport {

    private long simulationId;
    private long entityId;

    public AbstractReport(long simulationId, long entityId) {
        this.simulationId = simulationId;
        this.entityId = entityId;
    }

    public long getSimulationId() {
        return simulationId;
    }

    public void setSimulationId(long simulationId) {
        this.simulationId = simulationId;
    }

    public long getEntityId() {
        return entityId;
    }

    public void setEntityId(long entityId) {
        this.entityId = entityId;
    }

}