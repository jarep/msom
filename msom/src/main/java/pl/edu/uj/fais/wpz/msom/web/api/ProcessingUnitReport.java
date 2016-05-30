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
public class ProcessingUnitReport extends AbstractReport {

    private String name;
    private int efficiency;
    private int cores;

    private int queueLength;
    private int queueValue;
    private double avgWaitingTime;
    private double avgProcessingTime;
    private double percentOfExecutionCurrentTasks;

    private List<TaskReport> waitingTasks;
    private List<TaskReport> processingTasks;

    private List<TypeReport> availableTypes;

    public ProcessingUnitReport(long simulationId, long entityId) {
        super(simulationId, entityId);
    }

    public ProcessingUnitReport(String name, int efficiency, int cores, int queueLength, int queueValue, double avgWaitingTime, double avgProcessingTime, double percentOfExecutionCurrentTasks, List<TaskReport> waitingTasks, List<TaskReport> processingTasks, List<TypeReport> availableTypes, long simulationId, long entityId) {
        super(simulationId, entityId);
        this.name = name;
        this.efficiency = efficiency;
        this.cores = cores;
        this.queueLength = queueLength;
        this.queueValue = queueValue;
        this.avgWaitingTime = avgWaitingTime;
        this.avgProcessingTime = avgProcessingTime;
        this.percentOfExecutionCurrentTasks = percentOfExecutionCurrentTasks;
        this.waitingTasks = waitingTasks;
        this.processingTasks = processingTasks;
        this.availableTypes = availableTypes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getCores() {
        return cores;
    }

    public void setCores(int cores) {
        this.cores = cores;
    }

    public int getQueueLength() {
        return queueLength;
    }

    public void setQueueLength(int queueLength) {
        this.queueLength = queueLength;
    }

    public int getQueueValue() {
        return queueValue;
    }

    public void setQueueValue(int queueValue) {
        this.queueValue = queueValue;
    }

    public double getAvgWaitingTime() {
        return avgWaitingTime;
    }

    public void setAvgWaitingTime(double avgWaitingTime) {
        this.avgWaitingTime = avgWaitingTime;
    }

    public double getAvgProcessingTime() {
        return avgProcessingTime;
    }

    public void setAvgProcessingTime(double avgProcessingTime) {
        this.avgProcessingTime = avgProcessingTime;
    }

    public double getPercentOfExecutionCurrentTasks() {
        return percentOfExecutionCurrentTasks;
    }

    public void setPercentOfExecutionCurrentTasks(double percentOfExecutionCurrentTasks) {
        this.percentOfExecutionCurrentTasks = percentOfExecutionCurrentTasks;
    }

    public List<TaskReport> getWaitingTasks() {
        return waitingTasks;
    }

    public void setWaitingTasks(List<TaskReport> waitingTasks) {
        this.waitingTasks = waitingTasks;
    }

    public List<TaskReport> getProcessingTasks() {
        return processingTasks;
    }

    public void setProcessingTasks(List<TaskReport> processingTasks) {
        this.processingTasks = processingTasks;
    }

    public List<TypeReport> getAvailableTypes() {
        return availableTypes;
    }

    public void setAvailableTypes(List<TypeReport> availableTypes) {
        this.availableTypes = availableTypes;
    }

}
