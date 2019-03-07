import Json.Layer;
import Json.Tileset;
import org.jfree.fx.FXGraphics2D;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {
    private static Map map;

    private ArrayList<BufferedImage> tilesets;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;

    private double scale;

    private Map() {
        this.tilesets = new ArrayList<>();

        if(MapDataLoader.getInstance().getHasData()) {
            this.width = MapDataLoader.getInstance().getWidth();
            this.height = MapDataLoader.getInstance().getHeight();
            this.tileWidth = MapDataLoader.getInstance().getTileWidth();
            this.tileHeight = MapDataLoader.getInstance().getTileHeight();

            this.scale = 1;

            initializeMap();
        }
    }

    private void initializeMap() {
        try {
            for(Tileset tile : MapDataLoader.getInstance().getTilesets()) {
                String imageFileName = tile.getImageName();
                BufferedImage tilemap = ImageIO.read(getClass().getResourceAsStream(imageFileName));

                for(int y = 0; y < tilemap.getHeight(); y += tileHeight)
                {
                    for(int x = 0; x < tilemap.getWidth(); x += tileWidth)
                    {
                        tilesets.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
                    }
                }
            }
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void draw(FXGraphics2D graphics) {

        for(Layer layer : MapDataLoader.getInstance().getLayers()) {
            int x = 0;
            int y = 0;

            for(int i = 0; i < layer.getData().size(); i++) {
                if(x > width  - 1) {
                    x = 0;
                    y++;
                }

                if(layer.getData().get(i) > 0) {

                    AffineTransform tx = new AffineTransform();
                    tx.translate((x) * (tileWidth * scale), y * (tileHeight * scale));

                    tx.scale(scale, scale);

                    graphics.drawImage(tilesets.get(layer.getData().get(i) - 1), tx, null);
                }

                x++;
            }
        }
    }

    public static Map getInstance() {
        if(map == null) {
            map = new Map();
        }

        return map;
    }
}
