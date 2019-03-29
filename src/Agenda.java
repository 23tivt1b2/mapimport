import data.Performance;
import data.Stage;
import data.Timetable;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Agenda {

    private Timetable agenda;
    private ArrayList<Stage> stages;
    private ArrayList<Performance> performances;
    private ArrayList<Performance> newPerformance;
    private ArrayList<Performance> runningPerformances;
    private HashMap<Performance, Stage> performanceStageHashMap;
    private HashMap<Performance, Location> locationPerformanceHashMap;

    public Agenda() {
        try {
            File file = new File("resources/TimeTable1.ttd");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream is = new ObjectInputStream(fis);
            Timetable readTimeTable = (Timetable) is.readObject();
            is.close();
            fis.close();

            this.agenda = readTimeTable;

        } catch(Exception ex) {
            System.out.println(ex);
        }

        this.stages = new ArrayList<>();
        this.performances = new ArrayList<>();
        this.newPerformance = new ArrayList<>();
        this.runningPerformances = new ArrayList<>();
        this.performanceStageHashMap = new HashMap<>();
        this.locationPerformanceHashMap = new HashMap<>();
        init();
    }

    private void init() {
        for(Stage stage : this.agenda.getStages()) {
            stages.add(stage);
            for(Performance performance : stage.getPerformances()) {
                performances.add(performance);
                this.performanceStageHashMap.put(performance, stage);
            }
        }
    }

    public void update(int maxMovementSpeed, Random rnd, ArrayList<Location> artistEntranceAndExit, ArrayList<Location> stages) {
        ArrayList<Performance> performancesToRemove = new ArrayList<>();

        for(Performance performance : performances) {
            if(Clock.getInstance().hasTimePassed(performance.getBeginTime())) {
                runningPerformances.add(performance);
                newPerformance.add(performance);
                performancesToRemove.add(performance);
            }
        }

        for(Performance performance : performancesToRemove) {
            performances.remove(performance);
        }

        //For all performance's that are new in this update tick.
        for(Performance p : this.getNewPerformance()) {
            int entranceTileNumber = rnd.nextInt(artistEntranceAndExit.size());
            //Random movementSpeed, max to -1, then when initializing add +1 to make sure movementSpeed is not 0;
            int movementSpeed = rnd.nextInt(maxMovementSpeed - 1);
            Point2D pos = new Point2D.Double(artistEntranceAndExit.get(entranceTileNumber).getPosition().getX(), artistEntranceAndExit.get(entranceTileNumber).getPosition().getY());
            Person artist = new Person(pos, 5, 5, movementSpeed + 1, true);

            String stageName = this.getPerformanceStageHashMap().get(p).getStageName();
            Location locationStage = null;

            for(Location location : stages) {
                if(location.getName().toLowerCase().equals(stageName.toLowerCase())) {
                    locationStage = location;
                }
            }

            if(locationStage != null) {
                locationStage.addVisitor(artist);
                this.getLocationPerformanceHashMap().put(p, locationStage);
            }
            else {
                System.out.println("stage not found");
            }
        }

        this.clearNewPerformances();

        performancesToRemove.clear();
        for(Performance p : this.getRunningPerformances()) {
            if(Clock.getInstance().hasTimePassed(p.getEndTime())) {
                Location location = this.getLocationPerformanceHashMap().get(p);
                ArrayList<Person> artists = new ArrayList<>();

                for(Person person : location.getPersons()) {
                    artists.add(new Person(person.getOldPosition(), person.getWidth(), person.getHeight() ,person.getMovementSpeed(), true));
                }

                location.removeVisitors();

                for(Person person : artists) {
                    artistEntranceAndExit.get(0).addVisitor(person);
                }

                performancesToRemove.add(p);
            }
        }

        for (Performance p : performancesToRemove) {
            this.getRunningPerformances().remove(p);
        }
    }

    public HashMap<Performance, Location> getLocationPerformanceHashMap() {
        return this.locationPerformanceHashMap;
    }

    public ArrayList<Performance> getRunningPerformances() {
        return this.runningPerformances;
    }

    public void clearNewPerformances() {
        this.newPerformance.clear();
    }

    public HashMap<Performance, Stage> getPerformanceStageHashMap() {
        return this.performanceStageHashMap;
    }

    public ArrayList<Performance> getNewPerformance() {
        return this.newPerformance;
    }

    public Timetable getAgenda() {
        return this.agenda;
    }
}
