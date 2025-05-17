package Entity.Boost;

import Entity.Player;
import Entity.ghosts.Ghost;
import map.Map;

public abstract class Boost {
    protected final int row;
    protected final int col;
    protected final int tileSize;
    protected final Map map;

    public Boost(int row, int col, int tileSize, Map map) {
        this.row = row;
        this.col = col;
        this.tileSize = tileSize;
        this.map = map;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public abstract void boostTaken(Player player, Ghost ghost);
}
