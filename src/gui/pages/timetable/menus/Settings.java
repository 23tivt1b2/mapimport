package gui.pages.timetable.menus;

import gui.pages.timetable.PerformanceBox;
import gui.pages.timetable.StageBox;
import gui.pages.timetable.Timeline;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Settings {

    private data.Timetable timetableData;
    private gui.menu.Background background = new gui.menu.Background();

    private Stage stage;

    private double x;
    private double y;
    private StageBox stageBox;
    private PerformanceBox performanceBox;
    private Timeline timeline;

    public void start(data.Timetable timetableData, StageBox stageBox, PerformanceBox performanceBox, Timeline timeline) {
        this.timetableData = timetableData;
        this.stageBox = stageBox;
        this.performanceBox = performanceBox;
        this.timeline = timeline;
        createPopUpMenu(createSettings());
    }
    public Node createSettings() {
        StackPane center = new StackPane();
        center.setMinSize(240,109);
        center.setPadding(new Insets(6,0,0,0));
        VBox content = new VBox();
        content.setMinWidth(228);
        content.setMaxWidth(228);
        content.setMaxHeight(97);

        Label nameTimetable = new Label("name: ");
        nameTimetable.setAlignment(Pos.CENTER_RIGHT);
        nameTimetable.getStyleClass().addAll("box","timeline");
        nameTimetable.setMinSize(70,30);
        TextField setName = new TextField(this.timetableData.getName());
        setName.getStyleClass().addAll("box","timeline");
        setName.setMinSize(140,30);

        Label timeText = new Label("time span: ");
        timeText.getStyleClass().addAll("box","timeline");
        timeText.setMinSize(70,30);
        timeText.setAlignment(Pos.CENTER_RIGHT);

        ArrayList<String> timeList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        for (LocalTime time : this.timetableData.getFullTimeList()) {
            timeList.add(time.format(dtf));
        }

        ComboBox<String> beginTime = new ComboBox<>();
        beginTime.getStyleClass().addAll("box","timeline");
        beginTime.setMinSize(76,30);
        ComboBox<String> endTime = new ComboBox<>();
        endTime.getStyleClass().addAll("box","timeline");
        endTime.setMinSize(76,30);


        beginTime.setItems(FXCollections.observableList(timeList));
        beginTime.getSelectionModel().select(timeList.indexOf(this.timetableData.getBeginTime().toString()));
        endTime.setItems(FXCollections.observableList(timeList));
        endTime.getSelectionModel().select(timeList.indexOf(this.timetableData.getEndTime().toString()));
        HBox comboBox = new HBox();
        comboBox.getChildren().addAll(beginTime, endTime);


        Button save = new Button("save");
        save.getStyleClass().add("option-button");
        save.setOnAction(event1 -> {
            this.timetableData.setName(setName.getText());
            this.timetableData.setTime(LocalTime.parse(beginTime.getValue()),LocalTime.parse(endTime.getValue()));
            this.stageBox.update(this.timetableData);
            this.timeline.update(this.timetableData);
            this.performanceBox.update(this.timetableData);
            this.stage.close();
        });
        Button exit = new Button("exit");
        exit.getStyleClass().add("option-button");
        exit.setOnAction(event1 -> {
            this.stage.close();
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(save,exit);

        GridPane gridPane = new GridPane();
        gridPane.add(nameTimetable, 0, 1);
        gridPane.add(setName, 1, 1);
        gridPane.add(timeText, 0, 2);
        gridPane.add(comboBox, 1, 2);
        gridPane.add(buttons, 0 ,3, 3, 1);
        gridPane.setAlignment(Pos.BASELINE_CENTER);

        content.getChildren().addAll(gridPane);
        content.getStyleClass().add("table-box");
        center.getChildren().add(content);
        center.setAlignment(Pos.TOP_CENTER);
        return center;
    }
    public void createPopUpMenu(Node node) {
        Scene scene = new Scene(new StackPane(background.setBackground(109, (double) 240 / 30), node), 240, 109);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        this.stage = new Stage();
        this.stage.setOpacity(0.9);
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.setFullScreen(false);
        this.stage.setResizable(false);
        this.stage.initStyle(StageStyle.TRANSPARENT);
        this.stage.setScene(scene);
        this.stage.show();
        scene.setOnMousePressed(mouseEvent -> {
            x = stage.getX() - mouseEvent.getScreenX();
            y = stage.getY() - mouseEvent.getScreenY();
        });
        scene.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() + x);
            stage.setY(mouseEvent.getScreenY() + y);
        });
    }
}
