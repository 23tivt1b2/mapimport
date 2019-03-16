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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private Canvas canvas;
    private Camera camera;
    private ArrayList<Location> locations;
    private Map map;

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
        this.map = new Map();

        Entrance.getInstance().addTileToEntrance(map.getTileMap()[0][3]);
        Entrance.getInstance().addTileToEntrance(map.getTileMap()[0][4]);
        Entrance.getInstance().addTileToEntrance(map.getTileMap()[0][5]);

        Random rnd = new Random();
        int maxMovementSpeed = 2;

        locations = new ArrayList<>();
        Location location = new Location(56, 58, map);

        for(int i = 0; i < 10; i++) {
            int entranceTileNumber = rnd.nextInt(Entrance.getInstance().getPositions().size());
            //Random movementSpeed, max to -1, then when initializing add +1 to make sure movementSpeed is not 0;
            int movementSpeed = rnd.nextInt(maxMovementSpeed - 1);
            Person person = new Person(new Point2D.Double(Entrance.getInstance().getPositions().get(entranceTileNumber).getRealPosition().getX(), Entrance.getInstance().getPositions().get(entranceTileNumber).getRealPosition().getY()), 5, 5, movementSpeed + 1);
            location.addVisitor(person);
        }

        locations.add(location);

        Location location2 = new Location(35, 14, map);
        for(int i = 0; i < 10; i++) {
            int entranceTileNumber = rnd.nextInt(Entrance.getInstance().getPositions().size());
            //Random movementSpeed, max to -1, then when initializing add +1 to make sure movementSpeed is not 0;
            int movementSpeed = rnd.nextInt(maxMovementSpeed - 1);
            Person person = new Person(new Point2D.Double(Entrance.getInstance().getPositions().get(entranceTileNumber).getRealPosition().getX(), Entrance.getInstance().getPositions().get(entranceTileNumber).getRealPosition().getY()), 5, 5, movementSpeed + 1);
            location2.addVisitor(person);
        }

        locations.add(location2);
    }


    public void draw(FXGraphics2D graphics) {

        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.black);
        graphics.clearRect(0,0,(int)canvas.getWidth(), (int)canvas.getHeight());

        AffineTransform origianlTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));

        this.map.draw(graphics);

        for(Location location : locations) {
            location.drawVisitors(graphics);
        }

        graphics.setTransform(origianlTransform);
    }

    private void update(double deltaTime) {
        for(Location location : locations) {
            location.updateVisitors();
        }
    }


    public static void main(String[] args) {
        launch(Main.class);
    }
}
