import data.Performance;
import javafx.scene.transform.Scale;
import org.jfree.fx.FXGraphics2D;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Random;

public class Person {

    private Point2D newPosition;
    private Point2D oldPosition;
    private Point2D movement;
    private int width;
    private int height;
    private int movementSpeed;
    private Boolean canMove = true;

    private boolean hasArrived;

    private int randomX;
    private int randomY;
    private boolean hasArrivedOnOffPosition;

    private int minXLocation, minYLocation, maxXLocation, maxYLocation;

    private Random rnd;

    private Boolean isArtist;

    public Person(Point2D position, int width, int height, int movementSpeed, boolean isArtist) {
        this.oldPosition = position;
        this.width = width;
        this.height = height;
        this.movementSpeed = movementSpeed;
        this.movement = new Point2D.Double(0,0);
        this.hasArrived = false;

        this.randomX = 0;
        this.randomY = 0;
        this.hasArrivedOnOffPosition = false;
        this.rnd = new Random();

        this.isArtist = isArtist;
    }

    public void move(Location location) {

        AllPersons.getInstance().getAllPersons();

        if (!this.hasArrived) {
            Tile currentTile = location.findTile(this.oldPosition, this.width, this.height);

            if(currentTile != null) {

                if(currentTile.getDistanceTo() < 2.5) {
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
        //if the person has arrived on the right location - put him slightly off so the visitors don't form a big lump.
        else {
            if(!this.hasArrivedOnOffPosition) {
                if(randomX < this.getNewPosition().getX() + this.width && randomX + this.width > this.getNewPosition().getX()) {
                    if(randomY < this.getNewPosition().getY() + this.height && randomY + this.height > this.getNewPosition().getY()) {
                        if(!isArtist) {
                            this.randomX = rnd.nextInt(this.maxXLocation - this.minXLocation + 1) + this.minXLocation;
                            this.randomY = rnd.nextInt(this.maxYLocation - this.minYLocation + 1) + this.minYLocation;
                        }
                        else {
                            this.hasArrivedOnOffPosition = true;
                        }
                    }
                }

                Direction direction = null;

                if(this.oldPosition.getX() < randomX) {
                    direction = Direction.RIGHT;
                }
                else if(this.oldPosition.getX() > randomX) {
                    direction = Direction.LEFT;
                }
                else if(this.oldPosition.getY() < randomY) {
                    direction = Direction.DOWN;
                }
                else if(this.oldPosition.getY() > randomY) {
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
                            break;
                        }
                    }
                }
                //for (Tile tile : AllWalls.getInstance().getAllWalls()) {
                //    if(tile.getRealPosition().getX() < this.getNewPosition().getX()  + 1 && tile.getRealPosition().getX() + tile.getTileWidth() > this.getNewPosition().getX()) {
                //        if(tile.getRealPosition().getY() < this.getNewPosition().getY() + 1&& tile.getRealPosition().getY() + tile.getTileHeight() > this.getNewPosition().getY()) {
                //            this.canMove = false;
                //            break;
                //        }
                //    }
                //}
                if(this.canMove) {
                    this.oldPosition = this.newPosition;
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
        int randomFactor = 1+ ((int)(Math.random() * 4));
        System.out.println(randomFactor);
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

    public void setPersonSlightlyOff(int minX, int minY, int maxX, int maxY) {
        this.minXLocation = minX;
        this.minYLocation = minY;
        this.maxXLocation = maxX;
        this.maxYLocation = maxY;

        this.randomX = rnd.nextInt(maxX - minX + 1) + minX;
        this.randomY = rnd.nextInt(maxY - minY + 1) + minY;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getMovementSpeed() {
        return this.movementSpeed;
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
