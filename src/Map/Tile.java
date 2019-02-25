package Map;

import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tile {
    private JsonObject object;
    private int columns;
    private int firstgid;
    private String imageName;
    private int imageheight;
    private int imagewidth;
    private int margin;
    private String name;
    private int spacing;
    private int tilecount;
    private int tileheight;
    private int tilewidth;

    public Tile(JsonObject object){
        this.object = object;

    }
}
