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
    private ArrayList<Layer> layers;
    private ArrayList<Tile> tilesets;
    private ArrayList<TileMap> tileMaps;

    public Map(String filename){

        layers = new ArrayList<>();
        tilesets = new ArrayList<>();

        JsonReader reader = null;
        reader = Json.createReader(getClass().getResourceAsStream(filename));
        JsonObject root = reader.readObject();

        convertJsonTileToTiles(root.getJsonArray("tilesets"));
        convertJsonLayersToLayers(root.getJsonArray("layers"));

        this.width = root.getInt("width");
        this.height = root.getInt("height");
    }

    public void draw(Graphics2D g2d){

    }

    public void convertJsonTileToTiles(JsonArray array) {
        for(int i = 0; i < array.size(); i++) {
            Tile tile = new Tile();

            tile.setColumns(array.getJsonObject(i).getInt("columns"));
            tile.setFirstgid(array.getJsonObject(i).getInt("firstgid"));
            tile.setImageName(array.getJsonObject(i).getString("image"));
            tile.setImageheight(array.getJsonObject(i).getInt("imageheight"));
            tile.setImagewidth(array.getJsonObject(i).getInt("imagewidth"));
            tile.setMargin(array.getJsonObject(i).getInt("margin"));
            tile.setName(array.getJsonObject(i).getString("name"));
            tile.setSpacing(array.getJsonObject(i).getInt("spacing"));
            tile.setTilecount(array.getJsonObject(i).getInt("tilecount"));
            tile.setTileheight(array.getJsonObject(i).getInt("tileheight"));
            tile.setTilewidth(array.getJsonObject(i).getInt("tilewidth"));

            tilesets.add(tile);
        }
    }

    public void convertJsonLayersToLayers(JsonArray array) {
        for(int i = 0; i < array.size(); i++) {
            Layer layer = new Layer();

            JsonArray dataLayer = array.getJsonObject(i).getJsonArray("data");
            ArrayList<Integer> data = new ArrayList<>();
            for(int j = 0; j < dataLayer.size(); j++) {
                data.add(dataLayer.getInt(j));
            }
            layer.setData(data);

            layer.setHeight(array.getJsonObject(i).getInt("height"));
            layer.setId(array.getJsonObject(i).getInt("id"));
            layer.setName(array.getJsonObject(i).getString("name"));
            layer.setOpacity(array.getJsonObject(i).getInt("opacity"));
            layer.setType(array.getJsonObject(i).getString("type"));
            layer.setVisible(array.getJsonObject(i).getBoolean("visible"));
            layer.setWidth(array.getJsonObject(i).getInt("width"));
            layer.setX(array.getJsonObject(i).getInt("x"));
            layer.setY(array.getJsonObject(i).getInt("y"));

            layers.add(layer);
        }
    }

    public void getwidth(){

    }
}
