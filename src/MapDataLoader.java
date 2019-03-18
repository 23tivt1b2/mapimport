import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.util.ArrayList;

import Json.*;

public class MapDataLoader {

    private static MapDataLoader mapDataLoader;
    private boolean hasData;
    private int height;
    private boolean infinite;
    private ArrayList<Layer> layers;
    private int nextLayerId;
    private int nextObjectId;
    private String orientation;
    private String renderOrder;
    private String tiledVersion;
    private int tileHeight;
    private ArrayList<Tileset> tilesets;
    private int tileWidth;
    private String type;
    private double version;
    private int width;
    private JsonObject root;

    private MapDataLoader() {
        tilesets = new ArrayList<>();
        layers = new ArrayList<>();
    }

    public void loadJson(String fileName) {
        this.loadJson(fileName, false);
    }

    public void loadJson(String fileName, boolean override) {
        if (!hasData || override) {
            try {
                JsonReader reader = null;
                reader = Json.createReader(getClass().getResourceAsStream(fileName));
                this.root = reader.readObject();

                convertJsonTileToTiles(root.getJsonArray("tilesets"));
                convertJsonLayersToLayers(root.getJsonArray("layers"));
                convertJsonToVariables(root);

                hasData = true;

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void convertJsonTileToTiles(JsonArray array) {
        for (int i = 0; i < array.size(); i++) {
            Tileset tile = new Tileset(root);

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

    private void convertJsonLayersToLayers(JsonArray array) {
        for (int i = 0; i < array.size(); i++) {
            Layer layer = new Layer();

            JsonArray dataLayer = array.getJsonObject(i).getJsonArray("data");
            if(array.getJsonObject(i).getString("name").equals("Area")){
                convertJsonToObject(array);
            }else {
                ArrayList<Integer> data = new ArrayList<>();
                for (int j = 0; j < dataLayer.size(); j++) {
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
    }

    private void convertJsonToVariables(JsonObject object) {
        this.height = object.getInt("height");
        this.infinite = object.getBoolean("infinite");
        this.nextLayerId = object.getInt("nextlayerid");
        this.nextObjectId = object.getInt("nextobjectid");
        this.orientation = object.getString("orientation");
        this.renderOrder = object.getString("renderorder");
        this.tiledVersion = object.getString("tiledversion");
        this.tileHeight = object.getInt("tileheight");
        this.tileWidth = object.getInt("tilewidth");
        this.type = object.getString("type");
        this.version = object.getInt("version");
        this.width = object.getInt("width");
    }

    private void convertJsonToObject(JsonArray array) {
        for (int i = 0; i < array.size(); i++) {
            Layer layer = new Layer();
            JsonArray objectLayer = array.getJsonObject(i).getJsonArray("objects");
            for (int j = 0; j < objectLayer.size(); j++) {
                Area area = new Area();
                area.setHeight(objectLayer.getJsonObject(i).getInt("height"));
                area.setId(objectLayer.getJsonObject(i).getInt("id"));
                area.setName(objectLayer.getJsonObject(i).getString("name"));
                area.setWidth(objectLayer.getJsonObject(i).getInt("width"));
                area.setX(objectLayer.getJsonObject(i).getInt("x"));
                area.setY(objectLayer.getJsonObject(i).getInt("y"));
                layer.addArea(area);
            }
            layer.setId(array.getJsonObject(i).getInt("id"));
            layer.setName(array.getJsonObject(i).getString("name"));
            layer.setOpacity(array.getJsonObject(i).getInt("opacity"));
            layer.setType(array.getJsonObject(i).getString("type"));
            layer.setVisible(array.getJsonObject(i).getBoolean("visible"));
            layer.setX(array.getJsonObject(i).getInt("x"));
            layer.setY(array.getJsonObject(i).getInt("y"));

        }
    }

    public static MapDataLoader getInstance() {
        if (mapDataLoader == null) {
            mapDataLoader = new MapDataLoader();
        }

        return mapDataLoader;
    }

    public boolean getHasData() {
        return hasData;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInfinite() {
        return infinite;
    }

    public ArrayList<Layer> getLayers() {
        return layers;
    }

    public int getNextLayerId() {
        return nextLayerId;
    }

    public int getNextObjectId() {
        return nextObjectId;
    }

    public String getOrientation() {
        return orientation;
    }

    public String getRenderOrder() {
        return renderOrder;
    }

    public String getTiledVersion() {
        return tiledVersion;
    }

    public int getTileHeight() {
        return tileHeight;
    }

    public ArrayList<Tileset> getTilesets() {
        return tilesets;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public String getType() {
        return type;
    }

    public double getVersion() {
        return version;
    }

    public int getWidth() {
        return width;
    }
}
