import Map.Map;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import javax.imageio.ImageIO;

import java.awt.geom.AffineTransform;

public class Main extends Application {

    private Canvas canvas;
    private Map map;

    @Override
    public void start(Stage stage) throws Exception {
        this.map = null;
        try {
            this.map = new Map("/map.json");
        } catch (Exception e) {
            System.out.println("error");
        }

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        this.map.draw(graphics);
    }


    public static void main(String[] args) {
        launch(Main.class);
    }
}
