package data;

import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Stage implements Serializable {
    private ArrayList<Performance> performances;

    private int maxVisitors;
    private String stageName;
    private String color;

    private ArrayList<LocalTime> timeList;

    public Stage(int maxVisitors, String name, String color, ArrayList<LocalTime> timeList) {
        this.timeList = timeList;
        this.maxVisitors = maxVisitors;
        this.stageName = name;
        this.color = color;
        this.performances = new ArrayList<>();
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

    public Stage() {
        this.maxVisitors = 0;
        this.stageName = "";
        this.color = "pink";
        this.performances = new ArrayList<>();
    }

    public void addPerfomance(Performance performance) {
        this.performances.add(performance);
    }

    public Stage(ArrayList<Performance> performances, int maxVisitors) {
        this.maxVisitors = 0;
        this.stageName = "";
        this.performances = new ArrayList<>();
    }

    public void setMaxVisitors(int maxVisitors) {
        this.maxVisitors = maxVisitors;
    }

    public void setPerformances(ArrayList<Performance> performances) {
        this.performances = performances;
    }

    public void removePerformance(Performance performance) {
        this.performances.remove(performance);
    }

    public int getMaxVisitors() {
        return this.maxVisitors;
    }

    public String getStageName() {
        return this.stageName;
    }

    public void setStageName(String stageName) {
        this.stageName = stageName;
    }

    public ArrayList<Performance> getPerformances() {
        return this.performances;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.stageName;
    }
}