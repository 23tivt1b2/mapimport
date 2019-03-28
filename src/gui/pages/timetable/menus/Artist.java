package gui.pages.timetable.menus;

import gui.pages.timetable.PerformanceBox;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Artist {

    private data.Timetable timetableData;
    private PerformanceBox performanceBox;
    private gui.menu.Background background = new gui.menu.Background();

    private Stage stage;

    private double x;
    private double y;

    private GridPane artists;

    private Color LIGHT_FILL_1 = Color.rgb(148,1,135);

    public void start(data.Timetable timetableData, PerformanceBox performanceBox) {
        this.performanceBox = performanceBox;
        this.timetableData = timetableData;
        BorderPane root = new BorderPane();
        root.setLeft(createArtistView());
        root.setRight(createArtistAdd());
        createPopUpMenu(root);
        updateArtistView();
    }
    public Node createArtistView() {
        StackPane center = new StackPane();
        center.setMinSize(240,270);
        VBox content = new VBox();
        content.setMinWidth(228);
        content.setMaxWidth(228);
        content.setMaxHeight(258);

        this.artists = new GridPane();
        this.artists.setMinWidth(228);
        ScrollPane scroll = new ScrollPane();
        FlowPane scrollContent = new FlowPane(Orientation.VERTICAL);
        scrollContent.getChildren().addAll(this.artists);
        scroll.setContent(scrollContent);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setPannable(false);

        Label name = new Label("artist");
        name.setMinSize(106,30);
        name.getStyleClass().addAll("box","timeline");
        Label popularity = new Label("popularity");
        popularity.setMinSize(71,30);
        popularity.getStyleClass().addAll("box","timeline");
        Label delete = new Label();
        delete.setMinSize(45,30);
        delete.getStyleClass().addAll("box","timeline");
        HBox tableTop = new HBox();
        tableTop.getChildren().addAll(name,popularity,delete);

        content.getChildren().addAll(tableTop,scroll);
        content.getStyleClass().add("table-box");
        center.getChildren().add(content);
        return center;
    }
    public void updateArtistView() {
        artists.getChildren().clear();
        int i=1;
        for (data.Artist artist : this.timetableData.getArtists()) {
            Label name = new Label(artist.getName());
            name.setMinSize(106,30);
            name.getStyleClass().addAll("box","timeline");
            Label popularity = new Label(""+artist.getPopularity());
            popularity.setMinSize(71,30);
            popularity.getStyleClass().addAll("box","timeline");

            SVGPath deleteIcon = new SVGPath();
            deleteIcon.setContent("m 11 11 l 9 9 m -9 0 l 9 -9");
            deleteIcon.setStroke(LIGHT_FILL_1);

            Button delete = new Button();
            delete.setGraphic(deleteIcon);
            delete.setMinSize(30,30);
            delete.getStyleClass().addAll("box","control-buttons");
            delete.setOnAction(event -> {
                this.timetableData.removeArtist(artist);
                this.performanceBox.update(this.timetableData);
                updateArtistView();
            });
            this.artists.add(name,1,i);
            this.artists.add(popularity,2,i);
            this.artists.add(delete,3,i);
            i++;
        }
    }
    public Node createArtistAdd() {
        StackPane center = new StackPane();
        center.setMinSize(240,270);
        center.setPadding(new Insets(6,0,0,0));
        VBox content = new VBox();
        content.setMinWidth(228);
        content.setMaxWidth(228);
        content.setMaxHeight(97);

        Label nameArtist = new Label("name: ");
        nameArtist.setAlignment(Pos.CENTER_RIGHT);
        nameArtist.getStyleClass().addAll("box","timeline");
        nameArtist.setMinSize(72,30);
        TextField setArtist = new TextField();
        setArtist.getStyleClass().addAll("box","timeline");
        setArtist.setMinSize(150,30);

        Label popularityArtist = new Label("popularity: ");
        popularityArtist.getStyleClass().addAll("box","timeline");
        popularityArtist.setMinSize(72,30);
        popularityArtist.setAlignment(Pos.CENTER_RIGHT);
        TextField setPopularity = new TextField();
        setPopularity.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                setPopularity.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        setPopularity.getStyleClass().addAll("box","timeline");
        setPopularity.setMinSize(150,30);

        Button add = new Button("add");
        add.getStyleClass().add("option-button");
        add.setOnAction(event1 -> {
            data.Artist temporary = new data.Artist(setArtist.getText(), Integer.valueOf(setPopularity.getText()));
            this.timetableData.addArtist(temporary);
            updateArtistView();
            setArtist.clear();
            setPopularity.clear();
        });
        Button clear = new Button("clear");
        clear.getStyleClass().add("option-button");
        clear.setOnAction(event1 -> {
            setArtist.clear();
            setPopularity.clear();
        });
        Button exit = new Button("exit");
        exit.getStyleClass().add("option-button");
        exit.setOnAction(event1 -> {
            this.stage.close();
        });

        HBox buttons = new HBox();
        buttons.getChildren().addAll(add, clear, exit);

        GridPane gridPane = new GridPane();
        gridPane.add(nameArtist, 0, 1);
        gridPane.add(setArtist, 1, 1);
        gridPane.add(popularityArtist, 0, 2);
        gridPane.add(setPopularity, 1, 2);
        gridPane.add(buttons, 0 ,3, 3, 1);
        gridPane.setAlignment(Pos.BASELINE_CENTER);

        content.getChildren().addAll(gridPane);
        content.getStyleClass().add("table-box");
        center.getChildren().add(content);
        center.setAlignment(Pos.TOP_CENTER);
        return center;
    }
    public void createPopUpMenu(Node node) {
        Scene scene = new Scene(new StackPane(background.setBackground(270, (double) 480 / 30), node), 480, 270);
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
