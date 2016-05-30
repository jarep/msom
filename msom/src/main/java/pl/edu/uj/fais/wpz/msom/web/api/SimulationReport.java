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
public class SimulationReport extends AbstractReport {

    private String name;
    private boolean running;
    private List<TaskDispatcherReport> taskDispatchers;
    private List<TypeReport> allTypesReports;
    private List<TaskReport> allTasksReports;

    public SimulationReport(long simulationId, long entityId) {
        super(simulationId, entityId);
    }

    public SimulationReport(String name, boolean running, List<TaskDispatcherReport> taskDispatchers, List<TypeReport> allTypesReports, List<TaskReport> allTasksReports, long simulationId, long entityId) {
        super(simulationId, entityId);
        this.name = name;
        this.running = running;
        this.taskDispatchers = taskDispatchers;
        this.allTypesReports = allTypesReports;
        this.allTasksReports = allTasksReports;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public List<TaskDispatcherReport> getTaskDispatchers() {
        return taskDispatchers;
    }

    public void setTaskDispatchers(List<TaskDispatcherReport> taskDispatchers) {
        this.taskDispatchers = taskDispatchers;
    }

    public List<TypeReport> getAllTypesReports() {
        return allTypesReports;
    }

    public void setAllTypesReports(List<TypeReport> allTypesReports) {
        this.allTypesReports = allTypesReports;
    }

    public List<TaskReport> getAllTasksReports() {
        return allTasksReports;
    }

    public void setAllTasksReports(List<TaskReport> allTasksReports) {
        this.allTasksReports = allTasksReports;
    }

}
