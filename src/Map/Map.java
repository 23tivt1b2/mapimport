package Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.util.ArrayList;

public class Map {
    private int width;
    private int height;
    private JsonArray layers;
    private ArrayList<TileMap> tileMaps;

    public Map(String filename){
        JsonReader reader = null;
        reader = Json.createReader(getClass().getResourceAsStream(filename));
        JsonObject root = reader.readObject();

        layers = root.getJsonArray("tilesets");

        this.width = root.getInt("width");
        this.height = root.getInt("height");
    }

    public void draw(Graphics2D g2d){

    }

    public void getwidth(){
        System.out.println(layers);
    }
}
