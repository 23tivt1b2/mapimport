import data.Performance;
import javafx.scene.transform.Scale;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
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

                    if (!this.canMove) {
                        direction = randomDirection(direction);
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
                            if(person.getOldPosition().distance(this.newPosition) < 3.0) {
                                this.canMove = false;
                                //System.out.println("COLLISION");
                                break;
                            } else {
                                this.canMove = true;
                            }
                        }
                    }
                    for (Tile tile : AllWalls.getInstance().getAllWalls()) {
                        if(tile.getRealPosition().getX() < this.getNewPosition().getX() + this.width && tile.getRealPosition().getX() + tile.getTileWidth() > this.getNewPosition().getX()) {
                            if(tile.getRealPosition().getY() < this.getNewPosition().getY() + this.height && tile.getRealPosition().getY() + tile.getTileHeight() > this.getNewPosition().getY()) {
                                this.canMove = false;
                                System.out.println("COLLISION");
                                break;
                            }
                        }
                        //if(tile.getRealCenter().distance(this.newPosition) < 5) {
                        //    this.canMove = false;
                        //    System.out.println("COLLISION");
                        //    break;
                        //}
                    }
                    if(this.canMove) {
                        this.oldPosition = this.newPosition;
                    }
                }
            }
        }
    }

    public void draw(FXGraphics2D g2d) {
        Shape shape = new Ellipse2D.Double((int)this.oldPosition.getX(), (int)this.oldPosition.getY(), this.width, this.height);
        //Shape shape = new Rectangle((int)this.oldPosition.getX(), (int)this.oldPosition.getY(), this.width, this.height);
        g2d.setColor(Color.GREEN);
        g2d.fill(shape);
        g2d.setColor(Color.GRAY);
        g2d.draw(shape);
    }

    public Direction randomDirection(Direction direction) {
        int randomFactor = (int) (Math.random() * 4);
        switch (randomFactor) {
            case 1:
                if(direction.equals(Direction.UP)) {
                    randomDirection(direction);
                } else {
                    return Direction.UP;
                }
            case 2:
                if(direction.equals(Direction.DOWN)) {
                    randomDirection(direction);
                } else {
                    return Direction.DOWN;
                }
            case 3:
                if(direction.equals(Direction.LEFT)) {
                    randomDirection(direction);
                } else {
                    return Direction.LEFT;
                }
            case 4:
                if(direction.equals(Direction.RIGHT)) {
                    randomDirection(direction);
                } else {
                    return Direction.RIGHT;
                }
        }
        return Direction.UP;
    }

    public Point2D getNewPosition() {
        return this.newPosition;
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
