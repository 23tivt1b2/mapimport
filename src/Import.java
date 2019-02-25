import javax.imageio.ImageIO;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Import {
    private int width;
    private int height;

    private int tileHeight;
    private int tileWidth;

    private ArrayList<BufferedImage> tiles = new ArrayList<>();

    private int[][] map;

    public Import(String fileName) {
        JsonReader reader = null;
        reader = Json.createReader(getClass().getResourceAsStream(fileName));
        JsonObject root = reader.readObject();

        this.width = root.getInt("width");
        this.height = root.getInt("height");

        try {
            BufferedImage tilemap = ImageIO.read(getClass().getResourceAsStream(root.getJsonObject("tilelayer").getString("file")));

            this.tileHeight = root.getJsonObject("tilelayer").getJsonObject("tile").getInt("height");
            this.tileWidth = root.getJsonObject("tilelayer").getJsonObject("tile").getInt("width");

            for (int y = 0; y < tilemap.getHeight(); y += this.tileHeight) {
                for (int x = 0; x < tilemap.getWidth(); x += this.tileWidth) {
                    this.tiles.add(tilemap.getSubimage(x, y, this.tileWidth, this.tileHeight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.map = new int[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.map[y][x] = root.getJsonArray("map").getJsonArray(y).getInt(x);
            }
        }
    }

    void draw(Graphics2D g2d) {

        for (int y = 0; y < this.height; y++) {
            for (int x = 0; x < this.width; x++) {
                if (this.map[y][x] < 0) {
                    continue;
                }

                g2d.drawImage(
                        this.tiles.get(this.map[y][x]),
                        AffineTransform.getTranslateInstance(x * this.tileWidth, y * this.tileHeight),
                        null);
            }
        }


    }
}

