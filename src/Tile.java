import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Tile {

    private BufferedImage image;
    private int x;
    private int y;
    private int tileWidth;
    private int tileHeight;
    private double scale;
    private boolean isWall;

    private int distanceTo;
    private boolean distanceSet;

    private AffineTransform tx;

    public Tile(BufferedImage image, int x, int y, int tileWidth, int tileHeight, double scale, boolean isWall) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        this.scale = scale;
        this.isWall = isWall;

        if(this.isWall) {
            this.distanceSet = true;
            this.distanceTo = Integer.MAX_VALUE;
        } else {
            this.distanceTo = 0;
            this.distanceSet = false;
        }

    }

    public void draw(Graphics2D g2d) {

        this.tx = new AffineTransform();
        tx.translate((x) * (tileWidth * scale), y * (tileHeight * scale));

        tx.scale(scale, scale);
        g2d.drawImage(image, tx, null);


        if(isWall) {
            g2d.setColor(Color.red);
            g2d.drawRect((int)((x) * (tileWidth * scale)), (int)(y * (tileHeight * scale)), 10, 10);
            //g2d.drawString(distanceTo + "", (int)(x * (tileWidth * scale)), (int)(y * (tileHeight * scale)));
        }
        //else {
        //    //g2d.drawString(distanceTo + "", (int)(x * (tileWidth * scale)), (int)(y * (tileHeight * scale)));
        //}

    }

    public Point2D getRealPosition() {
        return new Point2D.Double((x) * (tileWidth * scale), y * (tileHeight * scale));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean getIsWall() {
        return isWall;
    }

    public void setIsWall(boolean value, int distanceTo) {
        this.isWall = value;
        this.distanceTo = distanceTo;
    }

    public int getDistanceTo() {
        return distanceTo;
    }

    public void setDistanceTo(int distanceTo) {
        this.distanceTo = distanceTo;
    }

    public boolean isDistanceSet() {
        return distanceSet;
    }

    public void setDistanceSet(boolean distanceSet) {
        this.distanceSet = distanceSet;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public BufferedImage getImage() {
        return image;
    }

    public double getScale() {
        return this.scale;
    }
}
