package gui.pages.timetable;

import gui.pages.timetable.menus.*;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Timetable {

    private data.Timetable timetableData = new data.Timetable();
    private BorderPane timetableContent;
    private Timeline timeline = new Timeline();
    private PerformanceBox performanceBox = new PerformanceBox();
    private StageBox stageBox = new StageBox();

    private Boolean created = false;

    private HBox buttons;

    public Node start() throws Exception {
        this.timetableContent = new BorderPane();
        if (this.created) {
            createTimetable();
        } else {
            this.timetableContent.setCenter(createSetup());
        }
        return this.timetableContent;
    }
    public Node createSetup() {
        FlowPane center = new FlowPane();
        VBox content = new VBox();

        Text title = new Text("set up your timetable");
        title.getStyleClass().add("setup-text");

        Label nameTimetable = new Label("name: ");
        nameTimetable.setAlignment(Pos.CENTER_RIGHT);
        nameTimetable.getStyleClass().addAll("box","timeline");
        nameTimetable.setMinSize(72,30);
        TextField setName = new TextField();
        setName.getStyleClass().addAll("box","timeline");
        setName.setMinSize(150,30);

        Label timeText = new Label("time span: ");
        timeText.getStyleClass().addAll("box","timeline");
        timeText.setMinSize(72,30);
        timeText.setAlignment(Pos.CENTER_RIGHT);

        ArrayList<String> timeList = new ArrayList<>();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        for (LocalTime time : this.timetableData.getFullTimeList()) {
            timeList.add(time.format(dtf));
        }

        ComboBox<String> beginTime = new ComboBox<>();
        beginTime.getStyleClass().addAll("box","timeline");
        beginTime.setMinSize(83,30);
        ComboBox<String> endTime = new ComboBox<>();
        endTime.getStyleClass().addAll("box","timeline");
        endTime.setMinSize(83,30);


        beginTime.setItems(FXCollections.observableList(timeList));
        beginTime.getSelectionModel().select(timeList.indexOf("12:00"));
        endTime.setItems(FXCollections.observableList(timeList));
        endTime.getSelectionModel().select(timeList.indexOf("02:00"));
        HBox comboBox = new HBox();
        comboBox.getChildren().addAll(beginTime, endTime);

        Button create = new Button("create");
        create.getStyleClass().add("option-button");
        create.setOnAction(event1 -> {
            this.timetableData.setName(setName.getText());
            this.timetableData.setTime(LocalTime.parse(beginTime.getValue()),LocalTime.parse(endTime.getValue()));
            this.created = true;
            try {
                this.timetableContent.setCenter(start());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Button load = new Button("load");
        load.getStyleClass().add("option-button");
        load.setOnAction(event1 -> {
            new Load().start(this);
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(create);

        GridPane gridPane = new GridPane();
        gridPane.add(nameTimetable, 0, 1);
        gridPane.add(setName, 1, 1);
        gridPane.add(timeText, 0, 2);
        gridPane.add(comboBox, 1, 2);
        gridPane.add(buttons, 0 ,3, 3, 1);
        gridPane.setAlignment(Pos.BOTTOM_LEFT);
        gridPane.setPadding(new Insets(5,0,0,0));

        content.getChildren().addAll(title,gridPane);
        content.getStyleClass().add("table-box");
        center.getChildren().add(content);
        center.setAlignment(Pos.TOP_LEFT);
        center.setPadding(new Insets(46,0,0,30));
        return center;
    }
    public void createTimetable(){
        this.buttons = new HBox();
        createAddStage();
        createArtistMenu();
        createSaveOption();
        createLoadOption();
        BorderPane content = new BorderPane();
        content.setTop(buttons);
        content.setCenter(createRight());
        this.timetableContent.setCenter(content);
        this.timetableContent.setLeft(stageBox.createStageBox(this.timetableData,createSettings(),this.performanceBox));
        this.timetableContent.setPadding(new Insets(0,3,0,3));
    }
    public Node createRight() {
        ScrollPane scroll = new ScrollPane();
        FlowPane scrollContent = new FlowPane(Orientation.VERTICAL);
        scrollContent.getChildren().addAll(this.timeline.createTimeline(this.timetableData),this.performanceBox.createPerformanceBox(this.timetableData));
        scroll.setContent(scrollContent);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setPannable(true);
        return scroll;
    }
    public void createAddStage() {
        Button stages = new Button("add stage");
        stages.getStyleClass().add("option-button");
        stages.setPrefSize(120,30);
        this.buttons.getChildren().add(stages);
        stages.setOnMouseClicked(event -> {
            new StageAdd().start(this.timetableData,this.stageBox,this.performanceBox);
        });
    }
    public void createArtistMenu() {
        Button addArtist = new Button("artists");
        addArtist.getStyleClass().add("option-button");
        addArtist.setPrefSize(120,30);
        this.buttons.getChildren().add(addArtist);
        addArtist.setOnMouseClicked(event -> {
            new Artist().start(this.timetableData,this.performanceBox);
        });
    }
    public Node createSettings() {
        Button setTime = new Button("settings");
        setTime.getStyleClass().add("option-button");
        setTime.setPrefSize(155,30);
        setTime.setOnMouseClicked(event -> {
            new Settings().start(this.timetableData,this.stageBox,this.performanceBox,this.timeline);
        });
        return setTime;
    }
    public void createSaveOption() {
        Button saveTimeTable = new Button("save");
        saveTimeTable.getStyleClass().add("option-button");
        saveTimeTable.setPrefSize(120,30);
        this.buttons.getChildren().add(saveTimeTable);
        saveTimeTable.setOnMouseClicked(event -> {
            new Save().start(this.timetableData);
        });
    }
    public void createLoadOption() {
        Button loadTimeTable = new Button("load");
        loadTimeTable.getStyleClass().add("option-button");
        loadTimeTable.setPrefSize(120,30);
        this.buttons.getChildren().add(loadTimeTable);
        loadTimeTable.setOnMouseClicked(event -> {
            new Load().start(this);
        });
    }
    public void update(data.Timetable timetableData) {
        this.timetableData = timetableData;
        System.out.println(this.timetableData.getName());
        this.performanceBox.update(this.timetableData);
        this.timeline.update(this.timetableData);
        this.stageBox.update(this.timetableData);
    }
    public void setCreated(Boolean created) {
        this.created = created;
    }
}
