package gui.pages.timetable.menus;

import data.Artist;
import data.Performance;
import gui.pages.timetable.PerformanceBox;
import gui.pages.timetable.StageBox;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AddPerformance {
    private data.Timetable timetableData;
    private data.Stage stageData;
    private gui.menu.Background background = new gui.menu.Background();
    private StageBox stageBox;
    private PerformanceBox performanceBox;

    private Stage stage;
    private ToggleGroup setColor;

    private double x;
    private double y;
    private String selectedTime;

    public void start(data.Timetable timetableData, data.Stage stage, PerformanceBox performanceBox, String selectedTime) {
        this.selectedTime = selectedTime;
        this.performanceBox = performanceBox;
        this.stageData = stage;
        this.timetableData = timetableData;
        createPopUpMenu(createAddPerformance());
    }
    public Node createAddPerformance() {
        StackPane center = new StackPane();
        center.setMinSize(250,139);
        center.setPadding(new Insets(6,0,0,0));
        VBox content = new VBox();
        content.setMinWidth(238);
        content.setMaxWidth(238);
        content.setMaxHeight(127);

        HBox titleBox = new HBox();
        Text title = new Text("add performance");
        title.getStyleClass().add("menu-text");
        titleBox.getChildren().add(title);
        titleBox.setPadding(new Insets(3));

        Label addPerformance = new Label("artist: ");
        addPerformance.setMinSize(63,30);
        addPerformance.setAlignment(Pos.CENTER_RIGHT);
        addPerformance.getStyleClass().addAll("box","timeline");
        ComboBox<Artist> artistComboBox = new ComboBox<>();
        for (Artist artist : this.timetableData.getArtists()) {
            artistComboBox.getItems().add(artist);
        }
        artistComboBox.getStyleClass().addAll("box","timeline");
        artistComboBox.setMinSize(166,30);

        Label setTime = new Label("time: ");
        setTime.setMinSize(63,30);
        setTime.setAlignment(Pos.CENTER_RIGHT);
        setTime.getStyleClass().addAll("box","timeline");

        ComboBox<String> beginTime = new ComboBox<>();
        beginTime.getStyleClass().addAll("box","timeline");
        beginTime.setMinSize(83,30);
        beginTime.getSelectionModel().select(this.selectedTime);
        ComboBox<String> endTime = new ComboBox<>();
        endTime.getStyleClass().addAll("box","timeline");
        endTime.setMinSize(83,30);

        ArrayList<String> timeList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        for (LocalTime time : this.timetableData.getTimeList()) {
            timeList.add(time.format(dtf));
        }

        beginTime.setItems(FXCollections.observableList(timeList));
        endTime.setItems(FXCollections.observableList(timeList));
        HBox comboBox = new HBox();
        comboBox.getChildren().addAll(beginTime, endTime);

        Button add = new Button("add");
        add.getStyleClass().add("option-button");
        add.setOnAction(event -> {
            stageData.addPerfomance(new Performance(artistComboBox.getValue(), LocalTime.parse(beginTime.getValue()), LocalTime.parse(endTime.getValue())));
            this.performanceBox.update(this.timetableData);
            this.stage.close();
        });
        Button exit = new Button("exit");
        exit.getStyleClass().add("option-button");
        exit.setOnAction(event1 -> {
            this.stage.close();
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(add,exit);

        GridPane gridPane = new GridPane();
        gridPane.add(titleBox,0,1,2,1);
        gridPane.add(addPerformance, 0, 2);
        gridPane.add(artistComboBox, 1, 2);
        gridPane.add(setTime, 0, 3);
        gridPane.add(comboBox, 1, 3);
        gridPane.add(buttons, 0 ,4, 4, 1);

        content.getChildren().addAll(gridPane);
        content.getStyleClass().add("table-box");
        center.getChildren().add(content);
        center.setAlignment(Pos.TOP_CENTER);
        return center;
    }
    public void createPopUpMenu(Node node) {
        Scene scene = new Scene(new StackPane(background.setBackground(145, (double) 250 / 30), node), 250, 145);
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
