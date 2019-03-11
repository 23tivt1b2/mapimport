import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Main extends Application {

    private Canvas canvas;
    private Camera camera;

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        camera = new Camera(canvas, g -> draw(g), g2d);

        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
                if(last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Festival simulator");
        stage.show();
        draw(g2d);
    }

    public void init() {
        MapDataLoader.getInstance().loadJson("map.json");
        Map.getInstance();
    }


    public void draw(FXGraphics2D graphics) {

        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.black);
        graphics.clearRect(0,0,(int)canvas.getWidth(), (int)canvas.getHeight());

        AffineTransform origianlTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));

        Map.getInstance().draw(graphics);

        graphics.setTransform(origianlTransform);


    }

    private void update(double deltaTime) {

    }


    public static void main(String[] args) {
        launch(Main.class);
    }
}
