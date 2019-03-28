
import org.jfree.fx.FXGraphics2D;

import java.util.ArrayList;

public class LocationHandler {
    private ArrayList<ArrayList<Location>> allLocations;
    private ArrayList<Location> exits;
    private boolean isTimeOver;

    public LocationHandler() {
        this.allLocations = new ArrayList<>();
        this.exits = new ArrayList<>();
        this.isTimeOver = false;
    }

    public void update() {
        if(isTimeOver) {
            if(this.exits.size() == 0) {
                for(ArrayList<Location> locations : allLocations) {
                    for(Location location : locations) {
                        if(location.getName().toLowerCase().equals("exit")) {
                            exits.add(location);
                        }
                    }
                }
            }
            else {
                for (Location location : exits) {
                    location.updateVisitors();
                }
            }
        }
        else {
            for(ArrayList<Location> locations : allLocations) {
                for(Location location : locations) {
                    location.updateVisitors();
                }
            }
        }
    }

    public void draw(FXGraphics2D g2d) {
        for(ArrayList<Location> locations : allLocations) {
            for(Location location : locations) {
                location.drawVisitors(g2d);
            }
        }
    }

    public void addLocations(ArrayList<Location> locations) {
        allLocations.add(locations);
    }

    public void setTimeOver(boolean isTimeOver) {
        this.isTimeOver = isTimeOver;
    }

    public boolean getTimeOver() {
        return this.isTimeOver;
    }
}
