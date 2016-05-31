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
public class TypeReport extends AbstractReport {

    private int difficulty;
    private String name;
    private double avgProcessingTime;
    private double avgWaitingTime;
    private int generatedTasksCounter;
    private int finishedTasksCounter;

    public TypeReport(long simulationId, long entityId) {
        super(simulationId, entityId);
    }

    public TypeReport(int difficulty, String name, double avgProcessingTime, double avgWaitingTime, int generatedTasksCounter, int finishedTasksCounter, long simulationId, long entityId) {
        super(simulationId, entityId);
        this.difficulty = difficulty;
        this.name = name;
        this.avgProcessingTime = avgProcessingTime;
        this.avgWaitingTime = avgWaitingTime;
        this.generatedTasksCounter = generatedTasksCounter;
        this.finishedTasksCounter = finishedTasksCounter;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvgProcessingTime() {
        return avgProcessingTime;
    }

    public void setAvgProcessingTime(double avgProcessingTime) {
        this.avgProcessingTime = avgProcessingTime;
    }

    public double getAvgWaitingTime() {
        return avgWaitingTime;
    }

    public void setAvgWaitingTime(double avgWaitingTime) {
        this.avgWaitingTime = avgWaitingTime;
    }

    public int getGeneratedTasksCounter() {
        return generatedTasksCounter;
    }

    public void setGeneratedTasksCounter(int generatedTasksCounter) {
        this.generatedTasksCounter = generatedTasksCounter;
    }

    public int getFinishedTasksCounter() {
        return finishedTasksCounter;
    }

    public void setFinishedTasksCounter(int finishedTasksCounter) {
        this.finishedTasksCounter = finishedTasksCounter;
    }

}
