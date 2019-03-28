package gui.menu;

import javafx.animation.FillTransition;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.Random;

public class Background {

    private Color LIGHT_FILL_1 = Color.rgb(148,1,135);
    private Color LIGHT_FILL_2 = Color.rgb(148,1,130);
    private Color DARK_FILL_1 = Color.rgb(138,1,120);
    private Color DARK_FILL_2 = Color.rgb(138,1,115);
    private Color[] colors = {LIGHT_FILL_1, LIGHT_FILL_2, DARK_FILL_1,DARK_FILL_2};
    private int [] durations = {3500, 4000, 4500, 5000, 5500};

    private GridPane background = new GridPane();
    private Random rand = new Random();

    public GridPane setBackground(double stageHeight, double widthIn30px) {
        for (int row = 0; row < stageHeight/30; row++) {
            for (int col = 0; col < widthIn30px; col++) {
                int color1 = rand.nextInt(4);
                int color2 = rand.nextInt(4);
                int duration = rand.nextInt(5);
                Rectangle rec = new Rectangle();
                rec.setWidth(30);
                rec.setHeight(30);
                FillTransition ft = new FillTransition(Duration.millis(durations[duration]), rec, colors[color1], colors[color2]);
                ft.setCycleCount(6000);
                ft.setAutoReverse(true);
                ft.play();
                GridPane.setRowIndex(rec, row);
                GridPane.setColumnIndex(rec, col);
                background.getChildren().addAll(rec);
            }
        }
        background.setMinHeight(stageHeight);
        return background;
    }
    public GridPane getBackground() {
        return background;
    }
}
