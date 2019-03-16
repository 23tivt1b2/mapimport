import java.util.ArrayList;

public class Entrance {

    private static Entrance entrance;

    private ArrayList<Tile> positions;

    private Entrance() {
        this.positions = new ArrayList<>();
    }

    public void addTileToEntrance(Tile tile) {
        positions.add(tile);
    }

    public void setPositions(ArrayList<Tile> positions) {
        this.positions = positions;
    }

    public ArrayList<Tile> getPositions() {
        return this.positions;
    }

    public static Entrance getInstance() {
        if(entrance == null) {
            entrance = new Entrance();
        }

        return entrance;
    }
}
