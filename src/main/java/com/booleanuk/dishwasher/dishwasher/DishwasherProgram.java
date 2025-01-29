package com.booleanuk.dishwasher.dishwasher;

import java.time.Duration;
import java.time.Instant;

public class DishwasherProgram {
    private String program;
    private double waterConsumption;
    private double electricConsumption;
    private double runTime;
    private Instant startTime;
    private double timeRemaining;

    public DishwasherProgram(String program){
        this.program = program;
        initializeDishwasherProgram();
        timeRemaining = runTime;
    }

    public void initializeDishwasherProgram(){
        switch (program) {
            case "Intensive70":
                    waterConsumption = 13.5;
                    electricConsumption = 1.35;
                    runTime = 150;
                break;
            case "Eco50":
                waterConsumption = 9.0;
                electricConsumption = 0.65;
                runTime = 60;
                break;
            case "HalfLoad":
                waterConsumption = 10.5;
                electricConsumption = 1.10;
                runTime = 40;
                break;
            case "CleanCycle":
                waterConsumption = 14;
                electricConsumption = 1.45;
                runTime = 55;
                break;
        }
    }

    public String getProgram() {
        return program;
    }

    public double getWaterConsumption() {
        return waterConsumption;
    }

    public double getElectricConsumption() {
        return electricConsumption;
    }

    public double getRunTime() {
        return runTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public double getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(double timeRemaining) {
        this.timeRemaining = timeRemaining;
    }
}
