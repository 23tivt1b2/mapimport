package Json;

import javax.json.JsonObject;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tileset {
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


    public Tileset(JsonObject object){
        this.object = object;
    }

    public JsonObject getObject() {
        return object;
    }

    public void setObject(JsonObject object) {
        this.object = object;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getFirstgid() {
        return firstgid;
    }

    public void setFirstgid(int firstgid) {
        this.firstgid = firstgid;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public int getImageheight() {
        return imageheight;
    }

    public void setImageheight(int imageheight) {
        this.imageheight = imageheight;
    }

    public int getImagewidth() {
        return imagewidth;
    }

    public void setImagewidth(int imagewidth) {
        this.imagewidth = imagewidth;
    }

    public int getMargin() {
        return margin;
    }

    public void setMargin(int margin) {
        this.margin = margin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpacing() {
        return spacing;
    }

    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    public int getTilecount() {
        return tilecount;
    }

    public void setTilecount(int tilecount) {
        this.tilecount = tilecount;
    }

    public int getTileheight() {
        return tileheight;
    }

    public void setTileheight(int tileheight) {
        this.tileheight = tileheight;
    }

    public int getTilewidth() {
        return tilewidth;
    }

    public void setTilewidth(int tilewidth) {
        this.tilewidth = tilewidth;
    }
}
