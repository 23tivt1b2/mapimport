package gui.pages.timetable.menus;

import data.Performance;
import gui.pages.timetable.PerformanceBox;
import gui.pages.timetable.StageBox;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class StageOptions {

    private data.Timetable timetableData;
    private data.Stage stageData;
    private gui.menu.Background background = new gui.menu.Background();
    private StageBox stageBox;
    private PerformanceBox performanceBox;

    private Stage stage;
    private ToggleGroup setColor;

    private double x;
    private double y;
    private GridPane performances;

    private Color LIGHT_FILL_1 = Color.rgb(148,1,135);

    public void start(data.Timetable timetableData, data.Stage stage, StageBox stageBox, PerformanceBox performanceBox) {
        this.performanceBox = performanceBox;
        this.stageBox = stageBox;
        this.stageData = stage;
        this.timetableData = timetableData;
        BorderPane root = new BorderPane();
        root.setRight(createViewPerformances());
        root.setLeft(createStageOptions());
        createPopUpMenu(root);
        updateViewPerformances();
    }
    public Node createStageOptions() {
        StackPane center = new StackPane();
        center.setMinSize(270,139);
        center.setPadding(new Insets(6,0,0,0));
        VBox content = new VBox();
        content.setMinWidth(258);
        content.setMaxWidth(258);
        content.setMaxHeight(127);

        HBox titleBox = new HBox();
        Text title = new Text("edit stage");
        title.getStyleClass().add("menu-text");
        titleBox.getChildren().add(title);
        titleBox.setPadding(new Insets(3));

        Label nameStage = new Label("name: ");
        nameStage.setAlignment(Pos.CENTER_RIGHT);
        nameStage.getStyleClass().addAll("box","timeline");
        nameStage.setMinSize(83,30);
        TextField setStage = new TextField(this.stageData.getStageName());
        setStage.getStyleClass().addAll("box","timeline");
        setStage.setMinSize(169,30);

        Label capacity = new Label("capacity: ");
        capacity.getStyleClass().addAll("box","timeline");
        capacity.setMinSize(83,30);
        capacity.setAlignment(Pos.CENTER_RIGHT);
        TextField setCapacity = new TextField(""+this.stageData.getMaxVisitors());
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
        for (Toggle toggle : setColor.getToggles()) {
            if (toggle.getUserData().equals(stageData.getColor())) {
                toggle.setSelected(true);
            }
        }

        Button save = new Button("save");
        save.getStyleClass().add("option-button");
        save.setOnAction(event1 -> {
            this.stageData.setStageName(setStage.getText());
            this.stageData.setMaxVisitors(Integer.parseInt(setCapacity.getText()));
            this.stageData.setColor((String)setColor.getSelectedToggle().getUserData());
            this.stageBox.update(this.timetableData);
        });

        Button clear = new Button("clear");
        clear.getStyleClass().add("option-button");
        clear.setOnAction(event1 -> {
            setStage.clear();
            setCapacity.clear();
        });
        Button delete = new Button("delete");
        delete.getStyleClass().add("option-button");
        delete.setOnAction(event ->  {
            this.timetableData.removeStage(this.stageData);
            this.stageBox.update(this.timetableData);
            stage.close();
        });
        Button exit = new Button("exit");
        exit.getStyleClass().add("option-button");
        exit.setOnAction(event1 -> {
            this.stage.close();
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(save,clear,delete,exit);

        GridPane gridPane = new GridPane();
        gridPane.add(titleBox,0,1,2,1);
        gridPane.add(nameStage, 0, 2);
        gridPane.add(setStage, 1, 2);
        gridPane.add(capacity, 0, 3);
        gridPane.add(setCapacity, 1, 3);
        gridPane.add(color, 0, 4);
        gridPane.add(colorGroup, 1, 4);
        gridPane.add(buttons, 0 ,5, 5, 1);

        content.getChildren().addAll(gridPane);
        content.getStyleClass().add("table-box");
        center.getChildren().add(content);
        center.setAlignment(Pos.TOP_CENTER);
        return center;
    }
    public Node createViewPerformances() {
        StackPane center = new StackPane();
        center.setMinSize(250,270);
        VBox content = new VBox();
        content.setMinWidth(238);
        content.setMaxWidth(238);
        content.setMaxHeight(258);

        HBox titleBox = new HBox();
        Text title = new Text("performances");
        title.getStyleClass().add("menu-text");
        titleBox.getChildren().add(title);
        titleBox.setPadding(new Insets(3));

        this.performances = new GridPane();
        this.performances.setMinWidth(238);
        ScrollPane scroll = new ScrollPane();
        FlowPane scrollContent = new FlowPane(Orientation.VERTICAL);
        scrollContent.getChildren().addAll(this.performances);
        scroll.setContent(scrollContent);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPannable(false);

        Label name = new Label("performance");
        name.setMinSize(116,30);
        name.getStyleClass().addAll("box","timeline");
        Label time = new Label("time");
        time.setMinSize(71,30);
        time.getStyleClass().addAll("box","timeline");
        Label delete = new Label();
        delete.setMinSize(45,30);
        delete.getStyleClass().addAll("box","timeline");
        HBox tableTop = new HBox();
        tableTop.getChildren().addAll(name,time,delete);

        content.getChildren().addAll(title,tableTop,scroll);
        content.getStyleClass().add("table-box");
        center.getChildren().add(content);
        return center;
    }
    public void updateViewPerformances() {
        performances.getChildren().clear();
        int i=1;
        for (Performance performance : this.stageData.getPerformances()) {
            Label name = new Label(performance.getArtist().getName());
            name.setMinSize(106,30);
            name.getStyleClass().addAll("box","timeline");
            Label time = new Label(performance.getBeginTime()+" - "+performance.getEndTime());
            time.setMinSize(71,30);
            time.getStyleClass().addAll("box","timeline");

            SVGPath deleteIcon = new SVGPath();
            deleteIcon.setContent("m 11 11 l 9 9 m -9 0 l 9 -9");
            deleteIcon.setStroke(LIGHT_FILL_1);

            Button delete = new Button();
            delete.setGraphic(deleteIcon);
            delete.setMinSize(30,30);
            delete.getStyleClass().addAll("box","timeline");
            delete.setOnAction(event -> {
                this.stageData.removePerformance(performance);
                updateViewPerformances();
                this.performanceBox.update(this.timetableData);
            });
            this.performances.add(name,1,i);
            this.performances.add(time,2,i);
            this.performances.add(delete,3,i);
            i++;
        }
    }
    public void createPopUpMenu(Node node) {
        Scene scene = new Scene(new StackPane(background.setBackground(270, (double) 520 / 30), node), 520, 270);
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
