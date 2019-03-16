import org.jfree.fx.FXGraphics2D;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class Location {

    private Map map;
    private Tile[][] heatmap;
    private ArrayList<Person> persons;
    private int zoneX;
    private int zoneY;

    private ArrayList<Tile> allTiles;

    public Location(int x, int y, Map map) {
        this.map = map;
        this.persons = new ArrayList<>();

        this.zoneX = x;
        this.zoneY = y;

        initMap();
        generateHeatMap(x, y);
        initAllTiles();
    }

    public void initMap() {
        this.heatmap = new Tile[map.getHeight()][map.getWidth()];

        for(int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
                this.heatmap[y][x] = new Tile(map.getTileMap()[y][x].getImage(), map.getTileMap()[y][x].getX(), map.getTileMap()[y][x].getY(), map.getTileMap()[y][x].getTileWidth(), map.getTileMap()[y][x].getTileHeight(), map.getTileMap()[y][x].getScale(), map.getTileMap()[y][x].getIsWall());
            }
        }
    }

    public void initAllTiles() {
        this.allTiles = new ArrayList<>();

        for(int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
                this.allTiles.add(heatmap[y][x]);
            }
        }
    }

    public void addVisitor(Person person) {
        this.persons.add(person);
    }

    public void updateVisitors() {

        boolean everyoneHere = true;

        for(Person p : persons) {
            p.move(this);
            if(!p.getHasArrived()) {
                everyoneHere = false;
            }
        }

        if(everyoneHere) {
            generateHeatMap(5, 0);

            for(Person p : persons) {
                p.setHasArrived(false);
            }
        }
    }

    public void drawVisitors(FXGraphics2D g2d) {

        for(Person p : persons) {
            p.draw(g2d);
        }
    }

    private void generateHeatMap(int x, int y) {
        LinkedList<Tile> frontier = new LinkedList<>();
        ArrayList<Tile> visited = new ArrayList<>();

        frontier.add(heatmap[y][x]);
        visited.add(heatmap[y][x]);
        markTile(heatmap[y][x], -1);

        Tile currentTile;

        while(!frontier.isEmpty()) {
            currentTile = frontier.pop();
            Tile[] neightboorTiles = getNeightboorTiles(currentTile);

            for(int i = 0; i < neightboorTiles.length; i++) {
                if(neightboorTiles[i] != null) {
                    boolean hasVisisted = false;

                    for(Tile tileVisited : visited) {
                        if(tileVisited.equals(neightboorTiles[i])) {
                            hasVisisted = true;
                            break;
                        }
                    }

                    if(!hasVisisted) {
                        if(!neightboorTiles[i].getIsWall()) {
                            markTile(neightboorTiles[i], currentTile.getDistanceTo());
                            frontier.add(neightboorTiles[i]);
                            visited.add(neightboorTiles[i]);
                        } else {
                           // frontier.add(neightboorTiles[i]);
                            //visited.add(neightboorTiles[i]);
                        }
                    }
                }
            }
        }
    }

    public Tile[] getNeightboorTiles(Tile tile) {
        Tile[] neighboors = new Tile[4];

        if(tile.getY() - 1 >= 0) {
            neighboors[0] = heatmap[tile.getY() - 1][tile.getX()];
        } else {
            neighboors[0] = null;
        }

        if(tile.getY() + 1 < map.getWidth()) {
            neighboors[1] = heatmap[tile.getY() + 1][tile.getX()];
        } else {
            neighboors[1] = null;
        }

        if(tile.getX() - 1 >= 0) {
            neighboors[2] = heatmap[tile.getY()][tile.getX() - 1];
        } else {
            neighboors[2] = null;
        }

        if(tile.getX() + 1 < map.getHeight()) {
            neighboors[3] = heatmap[tile.getY()][tile.getX() + 1];
        } else {
            neighboors[3] = null;
        }

        return neighboors;
    }

    public Tile findTile(Point2D position, int particleWidth, int particleHeight) {

        ArrayList<Tile> tiles = new ArrayList<>();

        for(Tile t : allTiles) {
            if (position.getX() <= t.getRealPosition().getX() + MapDataLoader.getInstance().getTileWidth()) {
                if (position.getX() >= t.getRealPosition().getX()) {
                    if (position.getY() <= t.getRealPosition().getY() + MapDataLoader.getInstance().getTileHeight()) {
                        if (position.getY() >= t.getRealPosition().getY()) {
                            tiles.add(t);
                        }
                    }
                }
            }
        }

        if(tiles.size() > 0) {
            int heighest = -MapDataLoader.getInstance().getTileWidth() - 1;
            Tile bestTile = tiles.get(0);
            for(Tile t : tiles) {

                int xDiff;
                int yDiff;

                if((t.getRealPosition().getX() + t.getTileWidth()) >= (position.getX() + particleWidth)) {
                    xDiff = (int)position.getX() + ((int)t.getRealPosition().getX() + t.getTileWidth());
                }
                else {
                    xDiff = (int)position.getX() + ((int)position.getX() + particleWidth);
                }


                if((t.getRealPosition().getY() + t.getTileHeight()) >= (position.getY() + particleHeight)) {
                    yDiff = (int)position.getY() + ((int)t.getRealPosition().getY() + t.getTileHeight());
                }
                else {
                    yDiff = (int)position.getY() + ((int)position.getY() + particleHeight);
                }

                if(xDiff * yDiff >= heighest) {
                    bestTile = t;
                }

            }

            return bestTile;
        }

        return null;
    }

    private void markTile(Tile tile, int distanceNeighboor) {
        tile.setDistanceSet(true);
        tile.setDistanceTo(distanceNeighboor + 1);
    }
}
