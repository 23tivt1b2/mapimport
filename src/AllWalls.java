import java.util.ArrayList;

public class AllWalls {

    private static AllWalls instance;
    private ArrayList<Tile> tiles;

    private AllWalls() {
        tiles = new ArrayList<>();
    }

    public void addWall(Tile tile) {
        tiles.add(tile);
    }

    public ArrayList<Tile> getAllWalls() {
        return this.tiles;
    }

    public static AllWalls getInstance() {
        if(instance == null) {
            instance = new AllWalls();
        }

        return instance;
    }
}
