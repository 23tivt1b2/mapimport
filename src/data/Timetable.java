package data;


import gui.pages.timetable.StageBox;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Timetable implements Serializable {

    private ArrayList<Stage> stages;
    private ArrayList<Artist> artists;

    private ArrayList<LocalTime> timeList;

    private ArrayList<LocalTime> fullTimeList;

    private String name;

    private LocalTime beginTime;
    private LocalTime endTime;

    private Duration duration;

    public Timetable() {
        this.stages = new ArrayList<>();
        this.artists = new ArrayList<>();
        this.duration = Duration.ofHours(3);
        setFullTimelist();
    }

    public Duration getDuration() {
        return duration;
    }

    public void setFullTimelist() {
        this.fullTimeList = new ArrayList<>();
        int cycle = 0;
        for (LocalTime time = LocalTime.of(0,0); cycle<=0; time=time.plusMinutes(30)) {
            this.fullTimeList.add(time);
            if (time.getHour()==23&&time.getMinute()==30) {
                cycle++;
            }
        }
    }
    public void setTime(LocalTime beginTime, LocalTime endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
    public LocalTime getBeginTime() {
        return beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }
    public ArrayList<LocalTime> getFullTimeList() {
        return this.fullTimeList;
    }
    public ArrayList<LocalTime> getTimeList() {
        return timeList;
    }
    public ArrayList<String> getTimeListInStrings() {
        ArrayList<String> timeList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        for (LocalTime time : getTimeList()) {
            timeList.add(time.format(dtf));
        }
        return timeList;
    }
    public void setTimeList(ArrayList<LocalTime> timeList) {
        this.timeList = timeList;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addStage(Stage stage, StageBox stageBox) {
        this.stages.add(stage);
        stageBox.update(this);
    }
    public void removeStage(Stage stage) {
        this.stages.remove(stage);
    }

    public void setStages(ArrayList<Stage> stages) {
        this.stages = stages;
    }

    public ArrayList<Stage> getStages() {
        return this.stages;
    }

    public ArrayList<String> getStageNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Stage stage : this.stages) {
            names.add(stage.getStageName());
        }
        return names;
    }

    public Stage getIndex(int i) {
        return this.stages.get(i);
    }

    public void addArtist(Artist artist) {
        this.artists.add(artist);
    }

    public void removeArtist(Artist artist) {
        this.artists.remove(artist);
    }

    public ArrayList<Artist> getArtists() {
        return this.artists;
    }

    public ArrayList<String> getArtistsName() {
        ArrayList<String> names = new ArrayList<>();
        for (Artist artist : this.artists) {
            names.add(artist.getName());
        }
        return names;
    }

    public int getSize() {
        return this.stages.size();
    }

    public void updateTimeTableInterface(StageBox stageBox) {
        stageBox.update(this);
    }
}