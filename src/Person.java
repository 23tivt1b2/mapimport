import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.LinkedList;

public class Person {

    private Point2D position;
    private Point2D movement;
    private int width;
    private int height;
    private int movementSpeed;

    private boolean hasArrived;

    public Person(Point2D position, int width, int height, int movementSpeed) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.movement = new Point2D.Double(0,0);
        this.hasArrived = false;
    }

    public void move(Location location) {

        if (!this.hasArrived) {
            Tile currentTile = location.findTile(this.position, this.width, this.height);

            if(currentTile != null) {

                if(currentTile.getDistanceTo() == 0) {
                    hasArrived = true;
                    return;
                }

                Tile neighboorTiles[] = location.getNeightboorTiles(currentTile);
                boolean hasNeighboors = false;
                int neighboorStartAt = 0;

                for(int i = 0; i < neighboorTiles.length; i++) {
                    if(neighboorTiles[i] != null) {
                        hasNeighboors = true;
                        neighboorStartAt = i;
                        break;
                    }
                }

                if(hasNeighboors) {
                    int lowest = neighboorTiles[neighboorStartAt].getDistanceTo();
                    Tile lowestTile = neighboorTiles[neighboorStartAt];

                    for(int i = 0; i < neighboorTiles.length; i++) {
                        if(neighboorTiles[i] != null) {
                            if(lowest > neighboorTiles[i].getDistanceTo()) {
                                lowest = neighboorTiles[i].getDistanceTo();
                                lowestTile = neighboorTiles[i];
                            }
                        }

                    }

                    Direction direction = null;

                    if(this.position.getX() < lowestTile.getRealPosition().getX()) {
                        direction = Direction.RIGHT;
                    }
                    else if(this.position.getX() > lowestTile.getRealPosition().getX()) {
                        direction = Direction.LEFT;
                    }
                    else if(this.position.getY() < lowestTile.getRealPosition().getY()) {
                        direction = Direction.DOWN;
                    }
                    else if(this.position.getY() > lowestTile.getRealPosition().getY()) {
                        direction = Direction.UP;
                    }

                    switch (direction) {
                        case UP:
                            this.movement = new Point2D.Double(0, -movementSpeed);
                            break;
                        case DOWN:
                            this.movement = new Point2D.Double(0, movementSpeed);
                            break;
                        case LEFT:
                            this.movement = new Point2D.Double(-movementSpeed, 0);
                            break;
                        case RIGHT:
                            this.movement = new Point2D.Double(movementSpeed, 0);
                            break;
                        default:
                            break;
                    }

                    this.position = new Point2D.Double(this.position.getX() + this.movement.getX(), this.position.getY() + this.movement.getY());
                }
            }
        }
    }

    public void draw(FXGraphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.fillRect((int)this.position.getX(), (int)this.position.getY(), this.width, this.height);
    }

    public boolean getHasArrived() {
        return this.hasArrived;
    }

    public void setHasArrived(boolean hasArrived) {
        this.hasArrived = hasArrived;
    }

    public enum Direction {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }
}
