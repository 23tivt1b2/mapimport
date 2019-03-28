package gui.pages.timetable;

import data.Performance;
import data.Timetable;
import gui.pages.timetable.menus.AddPerformance;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class PerformanceBox {
    private data.Timetable timetableData;

    private StackPane content;
    private GridPane background;
    private GridPane performances;


    public Node createPerformanceBox(Timetable timetableData) {
        this.timetableData = timetableData;
        this.background = new GridPane();
        this.performances = new GridPane();
        this.content = new StackPane();

        for (int c = 1; c < 6; c++) {
            HBox temporary = new HBox();
            for (String time : this.timetableData.getTimeListInStrings()) {
                Label cell = new Label();
                cell.getStyleClass().add("box");
                cell.setMinSize(60, 30);
                cell.setMaxSize(60, 30);
                temporary.getChildren().add(cell);
            }
            this.background.add(temporary, 0, c);
            c++;
        }
        this.content.getChildren().addAll(this.performances);
        this.content.setPickOnBounds(false);
        update(this.timetableData);
        return this.content;
    }
    public void update(Timetable timetableData) {
        this.timetableData = timetableData;
        this.performances.getChildren().clear();
        this.performances.getChildren().clear();

        for (data.Stage stage : this.timetableData.getStages()) {
            for (LocalTime time : this.timetableData.getTimeList()) {
                Button cell = new Button();
                cell.setUserData(time.toString());
                cell.getStyleClass().add("box");
                cell.setMinSize(60, 30);
                cell.setMaxSize(60, 30);
                cell.setOnAction(event -> {
                    new AddPerformance().start(this.timetableData,stage,this,time.toString());
                });
                if (stage.getPerformances().isEmpty()) {
                    this.performances.add(cell,this.timetableData.getTimeListInStrings().indexOf(time.toString()),this.timetableData.getStages().indexOf(stage));
                }
                for(Performance performance : stage.getPerformances()) {
                    long minutes = ChronoUnit.MINUTES.between(performance.getBeginTime(), performance.getEndTime());
                    int cellSize = (int)minutes*60/15;

                    Label performanceCell = new Label();
                    performanceCell.setMinSize(cellSize, 30);
                    performanceCell.setMaxSize(cellSize, 30);
                    performanceCell.setText(performance.getArtist().getName());
                    performanceCell.getStyleClass().addAll("box","timeline",stage.getColor());
                    performanceCell.setAlignment(Pos.CENTER);
                    if (performance.getBeginTime().isAfter(time)) {
                        this.performances.add(performanceCell,this.timetableData.getTimeListInStrings().indexOf(time.toString()),this.timetableData.getStages().indexOf(stage),cellSize/60,1);
                    } else {
                        this.performances.add(cell,this.timetableData.getTimeListInStrings().indexOf(time.toString()),this.timetableData.getStages().indexOf(stage));
                    }
                }

            }
        }
    }
}

