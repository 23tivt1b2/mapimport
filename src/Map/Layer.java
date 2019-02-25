package Map;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Layer {
    private ArrayList<BufferedImage> images;
    private ArrayList<Integer> data;
    private int height;
    private int id;
    private String name;
    private int opacity;
    private String type;
    private boolean visible;
    private int width;
    private int x;
    private int y;

    public Layer(ArrayList<BufferedImage> images){
        this.images = images;
    }

    public void draw(Graphics2D g2d){

    }
}
