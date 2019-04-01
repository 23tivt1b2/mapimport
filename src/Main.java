import data.Performance;
import gui.pages.timetable.PerformanceBox;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;
import sun.misc.Perf;

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

    private ArrayList<Location> entrances;
    private ArrayList<Location> stages;
    private ArrayList<Location> bars;
    private ArrayList<Location> bathrooms;
    private ArrayList<Location> artistEntranceAndExit;
    private ArrayList<Location> exits;

    private LocationHandler locationHandler;

    private double timer;
    private Random rnd = new Random();
    private final int MAX_MOVEMENT_SPEED = 2;
    private boolean timeElapsed;

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
        MapDataLoader.getInstance().loadJson("mapc.json");
        this.map = new Map();

        locations = this.map.getLocations();
        this.entrances = new ArrayList<>();
        this.stages = new ArrayList<>();
        this.bars = new ArrayList<>();
        this.bathrooms = new ArrayList<>();
        this.artistEntranceAndExit = new ArrayList<>();
        this.exits = new ArrayList<>();

        this.locationHandler = new LocationHandler();

        ArrayList<Location> locationsOfLimits = new ArrayList<>();

        for(Location location : locations) {
            if(location.getName().toLowerCase().equals("entrance")) {
                this.entrances.add(location);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().contains("stage") || location.getName().toLowerCase().contains("waitingroom")) {

                this.stages.add(location);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().contains("bar")) {
                this.bars.add(location);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().contains("bathroom")) {
                this.bathrooms.add(location);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().equals("artistentrance")) {
                this.artistEntranceAndExit.add(location);

                locationsOfLimits.add(location);
                continue;
            }

            if(location.getName().toLowerCase().equals("exit")) {
                this.exits.add(location);

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

        Clock.getInstance().setStartTime(startTime.getSecond(), startTime.getMinute(), startTime.getHour());
        Clock.getInstance().setEndTime(endtime);

        this.locationHandler.addLocations(this.locations);
        this.locationHandler.addLocations(this.entrances);
        this.locationHandler.addLocations(this.bars);
        this.locationHandler.addLocations(this.bathrooms);
        this.locationHandler.addLocations(this.artistEntranceAndExit);
        this.locationHandler.addLocations(this.exits);
        this.locationHandler.addLocations(this.stages);
    }


    public void draw(FXGraphics2D graphics) {

        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.black);
        graphics.clearRect(0,0,(int)canvas.getWidth(), (int)canvas.getHeight());

        AffineTransform origianlTransform = graphics.getTransform();

        graphics.setTransform(camera.getTransform((int) canvas.getWidth(), (int) canvas.getHeight()));

        this.map.draw(graphics);

        this.locationHandler.draw(graphics);

        Clock.getInstance().draw(graphics);

        graphics.setTransform(origianlTransform);
    }

    private void update(double deltaTime) {

        this.locationHandler.update();

        if(!this.locationHandler.getTimeOver()) {
            Clock.getInstance().addSecond();

            if(Clock.getInstance().isTimeUp()) {
                this.locationHandler.setTimeOver(true);
                for(Location location : locations) {
                    location.removeVisitors();
                }

                for(Person person : AllPersons.getInstance().getPersons()) {
                    exits.get(rnd.nextInt(exits.size())).addVisitor(person);
                }
            }
            else {

                this.agenda.update(MAX_MOVEMENT_SPEED, rnd, this.artistEntranceAndExit, this.stages);

                if(timer > 0.5) {
                    if(AllPersons.getInstance().getAllPersons().size() < 500) {
                        timer = 0;

                        int entranceTileNumber = rnd.nextInt(this.entrances.size());
                        //Random movementSpeed, max to -1, then when initializing add +1 to make sure movementSpeed is not 0;
                        int movementSpeed = rnd.nextInt(MAX_MOVEMENT_SPEED - 1);
                        Point2D pos = new Point2D.Double(this.entrances.get(entranceTileNumber).getPosition().getX(), this.entrances.get(entranceTileNumber).getPosition().getY());
                        Person person = new Person(pos, 5, 5, movementSpeed + 1, false);

                        AllPersons.getInstance().addPerson(person);

                        Location location = locations.get(rnd.nextInt(locations.size()));
                        location.addVisitor(person);
                    }
                }
                else {
                    timer+=deltaTime;
                }
            }
        }
    }


    public static void main(String[] args) {
        launch(Main.class);
    }
}
