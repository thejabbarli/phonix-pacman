package Entity.ghosts;

import Entity.Player;
import map.BoardUpdater;
import map.Map;

public class Blinky extends Ghost {
    public Blinky(int startRow, int startCol, Map map, BoardUpdater boardUpdater, Player player) {
        super(startRow, startCol, map, boardUpdater, player);
    }

    @Override
    protected void updateTarget() {
        if (currentMode == GhostMode.SCATTER) {
            // Top-right corner of the board
            setTarget(map.getMap()[0].length * map.getTileSize(), 0);
        } else {
            // CHASE or FRIGHTENED: target Pacman directly (still pixel values)
            setTarget(player.getCol() * map.getTileSize(), player.getRow() * map.getTileSize());
        }
    }

}
