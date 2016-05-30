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
public class TaskReport extends AbstractReport {

    private String name;
    private int difficulty;
    private String typeName;
    private int typeId;
    private boolean finished;
    private int processingTime;
    private int waitingTime;
    private int executionCounter;
    private double percentageOfCurrentExecution;

    public TaskReport(long simulationId, long entityId) {
        super(simulationId, entityId);
    }

    public TaskReport(String name, int difficulty, String typeName, int typeId, boolean finished, int processingTime, int waitingTime, int executionCounter, double percentageOfCurrentExecution, long simulationId, long entityId) {
        super(simulationId, entityId);
        this.name = name;
        this.difficulty = difficulty;
        this.typeName = typeName;
        this.typeId = typeId;
        this.finished = finished;
        this.processingTime = processingTime;
        this.waitingTime = waitingTime;
        this.executionCounter = executionCounter;
        this.percentageOfCurrentExecution = percentageOfCurrentExecution;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(int processingTime) {
        this.processingTime = processingTime;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getExecutionCounter() {
        return executionCounter;
    }

    public void setExecutionCounter(int executionCounter) {
        this.executionCounter = executionCounter;
    }

    public double getPercentageOfCurrentExecution() {
        return percentageOfCurrentExecution;
    }

    public void setPercentageOfCurrentExecution(double percentageOfCurrentExecution) {
        this.percentageOfCurrentExecution = percentageOfCurrentExecution;
    }

}
