package gui.pages.timetable;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Timeline {

    private HBox timeline;
    private data.Timetable timetableData;

    private ArrayList<LocalTime> timeList;

    public HBox createTimeline(data.Timetable timetableData) {
        this.timeline = new HBox();
        this.timeList = new ArrayList<>();
        this.timeline.setMaxSize(60, 30);
        update(timetableData);
        return this.timeline;
    }
    public void update(data.Timetable timetable) {
        this.timetableData = timetable;
        this.timeline.getChildren().clear();
        this.timeList.clear();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

        LocalDateTime endTime = LocalDateTime.of(LocalDate.now(),this.timetableData.getEndTime());
        if(endTime.toLocalTime().isAfter(LocalTime.of(0,0))&& Duration.between(this.timetableData.getBeginTime(),this.timetableData.getEndTime()).toHours()<this.timetableData.getDuration().toHours()) {
            endTime=endTime.plusDays(1);
        }
        for (LocalDateTime time = LocalDateTime.of(LocalDate.now(),this.timetableData.getBeginTime()); time.isBefore(endTime); time=time.plusMinutes(30)) {
            Label timeText = new Label();
            timeText.setText(dtf.format(time));
            timeText.getStyleClass().add("box");
            timeText.getStyleClass().add("timeline");
            timeText.setMinSize(120, 30);
            this.timeList.add(time.toLocalTime());
            this.timeline.getChildren().add(timeText);
        }
        this.timetableData.setTimeList(this.timeList);
    }
}