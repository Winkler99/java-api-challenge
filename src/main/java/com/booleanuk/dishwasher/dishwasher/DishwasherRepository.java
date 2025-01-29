package com.booleanuk.dishwasher.dishwasher;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class DishwasherRepository {

    private ArrayList<DishwasherProgram> availablePrograms;
    private ArrayList<DishwasherProgram> history;
    private ArrayList<DishwasherProgram> running;
    private int tablets;
    private double totalWaterConsumed = 0;

    public ArrayList<DishwasherProgram> getAvailablePrograms() {
        return availablePrograms;
    }

    public ArrayList<DishwasherProgram> getHistory() {
        return history;
    }

    public ArrayList<DishwasherProgram> getRunning() {
        return running;
    }

    public DishwasherRepository(){
        this.availablePrograms = new ArrayList<>();
        this.running = new ArrayList<>();
        this.history = new ArrayList<>();
        tablets  = 60;

        this.availablePrograms.add(new DishwasherProgram("Intensive70"));
        this.availablePrograms.add(new DishwasherProgram("Eco50"));
        this.availablePrograms.add(new DishwasherProgram("HalfLoad"));
        this.availablePrograms.add(new DishwasherProgram("CleanCycle"));

        for(int i = 0; i < 3; i++){
            this.history.add(new DishwasherProgram("CleanCycle"));
            this.history.add(new DishwasherProgram("CleanCycle"));
            this.history.add(new DishwasherProgram("Intensive70"));
            this.history.add(new DishwasherProgram("HalfLoad"));
            this.history.add(new DishwasherProgram("Eco50"));
        }

    }

    public ArrayList<DishwasherProgram> getAllAvailablePrograms(){
        return this.availablePrograms;
    }

    public String getCurrentRunningInfo(){
        String message = "";
        if(running.isEmpty()){
            message = "No program is running";
            return message;
        }

        DishwasherProgram program = running.getFirst();
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(program.getStartTime(), end);
        program.setTimeRemaining(program.getRunTime() - timeElapsed.toMinutesPart());

        if(program.getTimeRemaining() < 0){
            program.setTimeRemaining(0);
            history.add(program);
            running.removeFirst();
            return null;
        }

        for(DishwasherProgram oldProgram : this.history){
            totalWaterConsumed += oldProgram.getWaterConsumption();
        }
        String rinseMessage = "Rinse aid is available";
        String tabletMessage = "Tablets are available";

        if(totalWaterConsumed > 30){
            rinseMessage = "Rinse warning: Rinse Aid is running low. Please refill";
            if(totalWaterConsumed > 40){
                rinseMessage = "Rinse warning: Rinse Aid is empty. Please refill";
            }
        }
        if(tablets < 58){
            tabletMessage = "Tablet warning: Tablets are running low. Please refill";
            if(tablets < 63){
                tabletMessage = "Tablet warning: Out of tablets. Please refill";
            }
        }
        message = "Running program: " + program.getProgram() + "\n" + "Time remaining: "
                + program.getTimeRemaining() + " minutes" + "\n" + tabletMessage + "\n" + rinseMessage;
        return message;
    }

    public DishwasherProgram startProgram(String program){

        if(program == null){
            return null;
        }
        if (!running.isEmpty()) {
            DishwasherProgram runningProgram = running.getFirst();
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(runningProgram.getStartTime(), end);
            runningProgram.setTimeRemaining(runningProgram.getRunTime() - timeElapsed.toMinutesPart());
            if (runningProgram.getTimeRemaining() < 0) {
                history.add(runningProgram);
                running.removeFirst();
            }
            else
                return null;
        }

        DishwasherProgram newProgram = new DishwasherProgram(program);
        if(newProgram.getProgram() == null){
            return null;
        }
        newProgram.setStartTime(Instant.now());
        running.add(newProgram);
        return newProgram;
    }

    public ArrayList<DishwasherProgram> getLast150(){
        ArrayList<DishwasherProgram> last150 = new ArrayList<>();
        if(!history.isEmpty()){
            for(int i = 0; i < 150; i++){
                if(i == history.size()){
                    return last150;

                }
                last150.add(history.get(i));

            }
            return last150;
        }
        return null;
    }

    public String statistics(){
        String s = "";
        totalWaterConsumed = 0;
        double totalElectricityConsumed = 0;
        double averageElectricityConsumed = 0;
        double averageWaterConsumed = 0;

        for(DishwasherProgram program : history){
            totalWaterConsumed += program.getWaterConsumption();
            totalElectricityConsumed += program.getElectricConsumption();
        }
        averageElectricityConsumed = totalElectricityConsumed / history.size();
        averageWaterConsumed = totalWaterConsumed / history.size();

        return "Total water consumed: " + totalWaterConsumed + " litres" +"\n"
                + "Total electricity consumed: " + totalElectricityConsumed + "kWh per cycle" + "\n"
                + "Average water consumed: " + averageWaterConsumed + " litres"  + "\n"
                + "Average electricity consumed: " + averageElectricityConsumed + "kWh per cycle";
    }

    public DishwasherProgram cancelRunningProgram(){
        if(running.isEmpty()){
            return null;
        }

        return running.removeFirst();
    }



}
