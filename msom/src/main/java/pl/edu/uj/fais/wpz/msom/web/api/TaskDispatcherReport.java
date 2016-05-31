/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.uj.fais.wpz.msom.web.api;

import java.util.List;

/**
 *
 * @author jarep
 */
public class TaskDispatcherReport extends AbstractReport {

    private String name;
    private List<ProcessingUnitReport> processingUnits;
    private List<PathReport> comingOutPaths;
    private List<PathReport> incomingPaths;

    public TaskDispatcherReport(long simulationId, long entityId) {
        super(simulationId, entityId);
    }

    public TaskDispatcherReport(String name, List<ProcessingUnitReport> processingUnits, List<PathReport> comingOutPaths, List<PathReport> incomingPaths, long simulationId, long entityId) {
        super(simulationId, entityId);
        this.name = name;
        this.processingUnits = processingUnits;
        this.comingOutPaths = comingOutPaths;
        this.incomingPaths = incomingPaths;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProcessingUnitReport> getProcessingUnits() {
        return processingUnits;
    }

    public void setProcessingUnits(List<ProcessingUnitReport> processingUnits) {
        this.processingUnits = processingUnits;
    }

    public List<PathReport> getComingOutPaths() {
        return comingOutPaths;
    }

    public void setComingOutPaths(List<PathReport> comingOutPaths) {
        this.comingOutPaths = comingOutPaths;
    }

    public List<PathReport> getIncomingPaths() {
        return incomingPaths;
    }

    public void setIncomingPaths(List<PathReport> incomingPaths) {
        this.incomingPaths = incomingPaths;
    }

}
