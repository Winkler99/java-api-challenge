package com.booleanuk.dishwasher.dishwasher;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("dishwasherPrograms")
public class DishwasherController {

    private DishwasherRepository thePrograms;

    public DishwasherController() {
        this.thePrograms = new DishwasherRepository();

    }

    @GetMapping
    public List<DishwasherProgram> getAllAvailablePrograms() {

        if (this.thePrograms.getAvailablePrograms().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No available programs");
        }
        return thePrograms.getAllAvailablePrograms();
    }

    @PostMapping("/{name}")
    public DishwasherProgram startProgram(@PathVariable(name = "name") String programName){

        return thePrograms.startProgram(programName);
    }

    @GetMapping("/statistics")
    public String getStatistics() {
        String s = this.thePrograms.statistics();
        if(s.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return s;
    }

    @GetMapping("/150")
    public ArrayList<DishwasherProgram> getLast150() {
        ArrayList<DishwasherProgram> last150 = new ArrayList<>();
        last150 = this.thePrograms.getLast150();
        if(last150.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return last150;

    }

    @GetMapping("/current")
    public String getRunning(){
        if(this.thePrograms.getRunning() == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No program is running");
        }
        return this.thePrograms.getCurrentRunningInfo();
    }

    @DeleteMapping("/cancel")
    public DishwasherProgram cancelRunningProgram(){

        if(this.thePrograms.cancelRunningProgram()==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No program is running");
        }
        return this.thePrograms.cancelRunningProgram();
    }

}
