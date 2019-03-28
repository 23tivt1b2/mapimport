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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    private Canvas canvas;
    private Camera camera;
    private ArrayList<Location> locations;
    private Map map;
    private Agenda agenda;

    private ArrayList<Tile> entrances;
    private ArrayList<Tile> stages;
    private ArrayList<Tile> bars;
    private ArrayList<Tile> bathrooms;
    private ArrayList<Tile> artistEntrance;
    private ArrayList<Tile> exits;

    private double timer;
    private Random rnd = new Random();
    private final int MAX_MOVEMENT_SPEED = 2;

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

        locations = this.map.getLocations();
        this.entrances = new ArrayList<>();
        this.stages = new ArrayList<>();
        this.bars = new ArrayList<>();
        this.bathrooms = new ArrayList<>();
        this.artistEntrance = new ArrayList<>();
        this.exits = new ArrayList<>();

        ArrayList<Location> locationsOfLimits = new ArrayList<>();

        for(Location location : locations) {
            if(location.getName().toLowerCase().equals("entrance")) {
                for(int x = 0; x < location.getZoneX(); x++) {
                    this.entrances.add(map.getTileMap()[location.getZoneY() + 1][location.getZoneX() + x]);
                }
                for(int y = 0; y < location.getZoneY(); y++) {
                    this.entrances.add(map.getTileMap()[location.getZoneY() + 1 + y][location.getZoneX()]);
                }
                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().contains("stage") || location.getName().toLowerCase().contains("waitingroom")) {

                this.stages.add(map.getTileMap()[location.getZoneY() + 1][location.getZoneX()]);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().contains("bar")) {
                this.bars.add(map.getTileMap()[location.getZoneY() + 1][location.getZoneX()]);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().contains("bathroom")) {
                this.bathrooms.add(map.getTileMap()[location.getZoneY() + 1][location.getZoneX()]);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().equals("artistentrance")) {
                this.artistEntrance.add(map.getTileMap()[location.getZoneY() + 1][location.getZoneX()]);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().equals("exit")) {
                this.exits.add(map.getTileMap()[location.getZoneY()][location.getZoneX()]);

                locationsOfLimits.add(location);
                continue;
            }
        }

        for(Location location : locationsOfLimits) {
            this.locations.remove(location);
        }

        this.agenda = new Agenda();

        LocalTime startTime = this.agenda.getAgenda().getTimeList().get(0);
        LocalTime endtime = this.agenda.getAgenda().getTimeList().get(this.agenda.getAgenda().getTimeList().size() - 1);
        endtime = endtime.plusMinutes(30);

        System.out.println(startTime);
        System.out.println(endtime);

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

        Clock.getInstance().addSecond();

        if(timer > 0.5) {
            if(AllPersons.getInstance().getAllPersons().size() < 500) {
                timer = 0;

                int entranceTileNumber = rnd.nextInt(this.entrances.size());
                //Random movementSpeed, max to -1, then when initializing add +1 to make sure movementSpeed is not 0;
                int movementSpeed = rnd.nextInt(MAX_MOVEMENT_SPEED - 1);
                Person person = new Person(new Point2D.Double(this.entrances.get(entranceTileNumber).getRealPosition().getX(), this.entrances.get(entranceTileNumber).getRealPosition().getY()), 5, 5, movementSpeed + 1);

                AllPersons.getInstance().addPerson(person);

                Location location = locations.get(rnd.nextInt(locations.size()));
                location.addVisitor(person);

                System.out.println(location.getName());
            }
        }
        else {
            timer+=deltaTime;
        }

    }


    public static void main(String[] args) {
        launch(Main.class);
    }
}
