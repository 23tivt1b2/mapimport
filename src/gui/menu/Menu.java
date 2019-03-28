package gui.menu;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Menu {

    private Background menuBackground = new Background();

    private StackPane menu = new StackPane();
    private VBox menuButtons = new VBox();

    public StackPane menu(double stageHeight) throws FileNotFoundException {

        FlowPane menuBox = new FlowPane(Orientation.VERTICAL);
        StackPane logo = new StackPane();

        Image logoImage = new Image(new FileInputStream("recources/images/logo.png"));
        ImageView logoImageView = new ImageView(logoImage);

        Rectangle logoFill = new Rectangle();
        logoFill.setWidth(240);
        logoFill.setHeight(150);
        logoFill.setFill(Color.TRANSPARENT);

        logo.getChildren().addAll(logoFill,logoImageView);
        logo.setAlignment(Pos.CENTER);

        menuBox.getChildren().addAll(logo,menuButtons);
        menu.getChildren().addAll(menuBackground.setBackground(stageHeight,8),menuBox);
        return menu;
    }
    public void setSize(double stageHeight){
        menu.getChildren().remove(menuBackground.getBackground());
        menu.getChildren().add(0,menuBackground.setBackground(stageHeight,8));
    }
    public void addButton(String name, int padding) {
        Button button = new Button(name);
        button.setMinSize(240,60);
        button.getStyleClass().add("map-buttons");
        button.setAlignment(Pos.BASELINE_LEFT);
        button.setPadding(new Insets(0,0,0,padding));
        button.setOnAction(event -> {
        });
        menuButtons.getChildren().add(button);
    }
    public Button getButton(int index) {
        return (Button)menuButtons.getChildren().get(index);
    }
}
