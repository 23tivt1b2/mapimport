import Json.Area;
import Json.Layer;
import Json.Tileset;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Map {

    private ArrayList<BufferedImage> tilesets;
    private BufferedImage cacheImage;
    private int width;
    private int height;
    private int tileWidth;
    private int tileHeight;

    private double scale;

    private Tile[][] tileMap;
    private Tile[][] collisionTiles;
    private ArrayList<Location> locations;

    public Map() {
        this.tilesets = new ArrayList<>();
        this.locations = new ArrayList<>();

        if (MapDataLoader.getInstance().getHasData()) {
            this.width = MapDataLoader.getInstance().getWidth();
            this.height = MapDataLoader.getInstance().getHeight();
            this.tileWidth = MapDataLoader.getInstance().getTileWidth();
            this.tileHeight = MapDataLoader.getInstance().getTileHeight();

            this.scale = 1;

            this.tileMap = new Tile[height][width];
            this.collisionTiles = new Tile[height][width];

            initializeMap();
            redrawCache();
        }
    }

    public void redrawCache(){
        cacheImage = new BufferedImage(1920,1080,BufferedImage.TYPE_INT_ARGB);
        Graphics2D imageGraphics = cacheImage.createGraphics();
        drawCache(imageGraphics);
    }

    private void initializeMap() {
        try {
            for (Tileset tile : MapDataLoader.getInstance().getTilesets()) {

                String imageFileName = tile.getImageName();
                BufferedImage tilemap = ImageIO.read(getClass().getResourceAsStream(imageFileName));

                for (int y = 0; y < tilemap.getHeight(); y += tileHeight) {
                    for (int x = 0; x < tilemap.getWidth(); x += tileWidth) {
                        tilesets.add(tilemap.getSubimage(x, y, tileWidth, tileHeight));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        for (Layer layer : MapDataLoader.getInstance().getLayers()) {
            int x = 0;
            int y = 0;

            if (layer.getName().equals("collision")) {
                for (int i = 0; i < layer.getData().size(); i++) {
                    if (x > width - 1) {
                        x = 0;
                        y++;
                    }

                    //So if the current number in the layer is higher than 0, it is a wall
                    //If not we still add it to the list but as a non wall.
                    //Later in this method we merge the collision layer with the other layers and check whetever something is a wall or not.
                    if (layer.getData().get(i) > 0) {
                        collisionTiles[y][x] = new Tile(tilesets.get(layer.getData().get(i) - 1), x, y, tileWidth, tileHeight, scale, true);
                    } else {
                        collisionTiles[y][x] = new Tile(tilesets.get(layer.getData().get(i)), x, y, tileWidth, tileHeight, scale, false);
                    }
                    x++;
                }
            }
             else {
                for (int i = 0; i < layer.getData().size(); i++) {
                    if (x > width - 1) {
                        x = 0;
                        y++;
                    }

                    if (layer.getData().get(i) > 0) {
                        tileMap[y][x] = new Tile(tilesets.get(layer.getData().get(i) - 1), x, y, tileWidth, tileHeight, scale, false);
                    }

                    x++;
                }
            }
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (collisionTiles[y][x].getIsWall()) {
                    tileMap[y][x].setIsWall(true, Integer.MAX_VALUE);
                    AllWalls.getInstance().addWall(tileMap[y][x]);
                }
            }
        }

        for(Area area : MapDataLoader.getInstance().getAreas()) {
            int zoneWidht = (int)area.getWidth() / MapDataLoader.getInstance().getTileWidth();
            int zoneHeight = (int)area.getHeight() / MapDataLoader.getInstance().getTileHeight();

            int x = (int)area.getX() / MapDataLoader.getInstance().getTileWidth();
            int y = (int)area.getY() / MapDataLoader.getInstance().getTileHeight();

            Location location = new Location(x + 1, y, area.getName(), zoneWidht, zoneHeight, this, new Point2D.Double(area.getX() + 1, area.getY()));
            locations.add(location);
        }
    }

    public void draw(Graphics2D graphics){
        graphics.drawImage(cacheImage,new AffineTransform(),null);
    }

    public void drawCache(Graphics2D graphics) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tileMap[y][x].draw(graphics);
            }
        }
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }
}
