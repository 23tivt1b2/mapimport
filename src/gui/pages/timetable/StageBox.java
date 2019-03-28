package gui.pages.timetable;

import gui.pages.timetable.menus.StageOptions;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class StageBox {

    private data.Timetable timetableData;
    private PerformanceBox performanceBox;

    private VBox stageBox;
    private VBox stages;
    private Label title;
    private Button setTime;

    public Node createStageBox(data.Timetable timetableData, Node setTime, PerformanceBox performanceBox) {
        this.timetableData = timetableData;
        this.performanceBox = performanceBox;
        this.setTime = (Button)setTime;
        this.stageBox = new VBox();
        this.stages = new VBox();
        StackPane empty = new StackPane();
        VBox cells = new VBox();
        for (int i = 0; i < 3; i++) {
            Label temporary = new Label();
            temporary.getStyleClass().addAll("box");
            temporary.setMinSize(155, 30);
            temporary.setMaxSize(155, 30);
            cells.getChildren().add(temporary);
        }
        empty.getChildren().addAll(cells,this.stages);
        this.stageBox.setMinWidth(150);
        stageBox.getChildren().addAll(createTitleArea(),empty);
        addSettings();
        update(this.timetableData);
        return this.stageBox;
    }
    public Node createTitleArea() {
        this.title = new Label();
        this.title.setText(timetableData.getName());
        this.title.setAlignment(Pos.BOTTOM_LEFT);
        this.title.setPadding(new Insets(3));
        this.title.setMinHeight(61);
        this.title.setMaxWidth(155);
        this.title.getStyleClass().addAll("box","title-box");
        return this.title;
    }
    public void addSettings() {
        StackPane bottom = new StackPane();
        bottom.getChildren().add(setTime);
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        this.stageBox.getChildren().add(bottom);
        VBox.setVgrow(bottom, Priority.ALWAYS);
    }
    public void update(data.Timetable timetable) {
        this.timetableData = timetable;
        this.stages.getChildren().clear();
        this.title.setText(this.timetableData.getName());
        for (data.Stage stage : timetable.getStages()) {
            Button temporary = new Button(stage.toString());
            temporary.getStyleClass().addAll("stage-box",stage.getColor());
            temporary.setMinSize(155, 30);
            temporary.setMaxSize(155, 30);
            temporary.setOnMouseClicked(event -> {
                new StageOptions().start(this.timetableData,stage,this,this.performanceBox);
            });
            this.stages.getChildren().add(temporary);
        }
    }
}
