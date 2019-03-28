package gui.pages.timetable.menus;

import gui.pages.timetable.PerformanceBox;
import gui.pages.timetable.StageBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageAdd {

    private data.Timetable timetableData;
    private PerformanceBox performanceBox;
    private gui.menu.Background background = new gui.menu.Background();

    private StageBox stageBox;

    private Stage stage;

    private ToggleGroup setColor;

    private double x;
    private double y;

    public void start(data.Timetable timetableData, StageBox stageBox, PerformanceBox performanceBox) {
        this.stageBox = stageBox;
        this.timetableData = timetableData;
        this.performanceBox = performanceBox;
        createPopUpMenu(createStageAdd());
    }
    public Node createStageAdd() {
        StackPane center = new StackPane();
        center.setMinSize(270,139);
        center.setPadding(new Insets(6,0,0,0));
        VBox content = new VBox();
        content.setMinWidth(258);
        content.setMaxWidth(258);
        content.setMaxHeight(127);

        Label nameStage = new Label("name: ");
        nameStage.setAlignment(Pos.CENTER_RIGHT);
        nameStage.getStyleClass().addAll("box","timeline");
        nameStage.setMinSize(83,30);
        TextField setStage = new TextField();
        setStage.getStyleClass().addAll("box","timeline");
        setStage.setMinSize(169,30);

        Label capacity = new Label("capacity: ");
        capacity.getStyleClass().addAll("box","timeline");
        capacity.setMinSize(83,30);
        capacity.setAlignment(Pos.CENTER_RIGHT);
        TextField setCapacity = new TextField();
        setCapacity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                setCapacity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        setCapacity.getStyleClass().addAll("box","timeline");
        setCapacity.setMinSize(169,30);

        Label color = new Label("color: ");
        color.getStyleClass().addAll("box","timeline");
        color.setMinSize(83,30);
        color.setAlignment(Pos.CENTER_RIGHT);

        this.setColor = new ToggleGroup();

        ToggleButton pink = new ToggleButton();
        pink.getStyleClass().addAll("toggle","pink");
        pink.setMinSize(28,28);
        pink.setMaxSize(28,28);
        pink.setToggleGroup(setColor);
        pink.setUserData("pink");

        ToggleButton blue = new ToggleButton();
        blue.getStyleClass().addAll("toggle","blue");
        blue.setMinSize(28,28);
        blue.setMaxSize(28,28);
        blue.setToggleGroup(setColor);
        blue.setUserData("blue");

        ToggleButton green = new ToggleButton();
        green.getStyleClass().addAll("toggle","green");
        green.setMinSize(28,28);
        green.setMaxSize(28,28);
        green.setToggleGroup(setColor);
        green.setUserData("green");

        ToggleButton yellow = new ToggleButton();
        yellow.getStyleClass().addAll("toggle","yellow");
        yellow.setMinSize(28,28);
        yellow.setMaxSize(28,28);
        yellow.setToggleGroup(setColor);
        yellow.setUserData("yellow");

        ToggleButton red = new ToggleButton();
        red.getStyleClass().addAll("toggle","red");
        red.setMinSize(28,28);
        red.setMaxSize(28,28);
        red.setToggleGroup(setColor);
        red.setUserData("red");

        ToggleButton cyan = new ToggleButton();
        cyan.getStyleClass().addAll("toggle","cyan");
        cyan.setMinSize(28,28);
        cyan.setMaxSize(28,28);
        cyan.setToggleGroup(setColor);
        cyan.setUserData("cyan");

        HBox colorGroup = new HBox();
        colorGroup.getChildren().addAll(pink,blue,green,yellow,red,cyan);
        colorGroup.setPadding(new Insets(1,0,0,0));

        Button add = new Button("add");
        add.getStyleClass().add("option-button");
        add.setOnAction(event1 -> {
            data.Stage temporary1 = new data.Stage(Integer.valueOf(setCapacity.getText()), setStage.getText(),(String)this.setColor.getSelectedToggle().getUserData(),this.timetableData.getTimeList());
            this.timetableData.addStage(temporary1, this.stageBox);
            this.performanceBox.update(this.timetableData);
            setStage.clear();
            setCapacity.clear();
            this.setColor.getSelectedToggle().setSelected(false);
        });
        Button clear = new Button("clear");
        clear.getStyleClass().add("option-button");
        clear.setOnAction(event1 -> {
            setStage.clear();
            setCapacity.clear();
        });
        Button exit = new Button("exit");
        exit.getStyleClass().add("option-button");
        exit.setOnAction(event1 -> {
            this.stage.close();
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(add, clear, exit);

        GridPane gridPane = new GridPane();
        gridPane.add(nameStage, 0, 1);
        gridPane.add(setStage, 1, 1);
        gridPane.add(capacity, 0, 2);
        gridPane.add(setCapacity, 1, 2);
        gridPane.add(color, 0, 3);
        gridPane.add(colorGroup, 1, 3);
        gridPane.add(buttons, 0 ,4, 4, 1);
        gridPane.setAlignment(Pos.BASELINE_CENTER);

        content.getChildren().addAll(gridPane);
        content.getStyleClass().add("table-box");
        center.getChildren().add(content);
        center.setAlignment(Pos.TOP_CENTER);
        return center;
    }
    public Node createToggleButton(String name) {
        ToggleButton pink = new ToggleButton();
        pink.getStyleClass().addAll("toggle",name);
        pink.setMinSize(24,24);
        pink.setMaxSize(24,24);
        pink.setToggleGroup(setColor);
        return pink;
    }
    public void createPopUpMenu(Node node) {
        Scene scene = new Scene(new StackPane(background.setBackground(139, (double) 270 / 30), node), 270, 139);
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

