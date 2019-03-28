import data.Performance;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;

public class Person {

    private Point2D newPosition;
    private Point2D oldPosition;
    private Point2D movement;
    private int width;
    private int height;
    private int movementSpeed;
    private Boolean canMove = true;

    private boolean hasArrived;

    public Person(Point2D position, int width, int height, int movementSpeed) {
        this.oldPosition = position;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.movement = new Point2D.Double(0,0);
        this.hasArrived = false;
    }

    public void move(Location location) {

        AllPersons.getInstance().getAllPersons();

        if (!this.hasArrived) {
            Tile currentTile = location.findTile(this.oldPosition, this.width, this.height);

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

                    if(this.oldPosition.getX() < lowestTile.getRealPosition().getX()) {
                        direction = Direction.RIGHT;
                    }
                    else if(this.oldPosition.getX() > lowestTile.getRealPosition().getX()) {
                        direction = Direction.LEFT;
                    }
                    else if(this.oldPosition.getY() < lowestTile.getRealPosition().getY()) {
                        direction = Direction.DOWN;
                    }
                    else if(this.oldPosition.getY() > lowestTile.getRealPosition().getY()) {
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

                    this.newPosition = new Point2D.Double(this.oldPosition.getX() + this.movement.getX(), this.oldPosition.getY() + this.movement.getY());

                    for (Person person : AllPersons.getInstance().getAllPersons()) {
                        if(!person.equals(this)) {
                            if((person.getOldPosition().distance(this.newPosition) < 2.5)) {
                                this.canMove = false;
                                break;
                            } else {
                                this.canMove = true;
                            }
                        }
                    }
                    if(this.canMove) {
                        this.oldPosition = this.newPosition;
                    }
                }
            }
        }
    }

    public void draw(FXGraphics2D g2d) {
        g2d.setColor(Color.GREEN);
        g2d.fillRect((int)this.oldPosition.getX(), (int)this.oldPosition.getY(), this.width, this.height);
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

    public Point2D getOldPosition() {
        return this.oldPosition;
    }
}
