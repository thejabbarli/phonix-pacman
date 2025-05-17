package Entity.ghosts;

import Entity.Player;
import map.BoardUpdater;
import map.Map;

public class Clyde extends Ghost {

    public Clyde(int startRow, int startCol, Map map, BoardUpdater boardUpdater, Player player) {
        super(startRow, startCol, map, boardUpdater, player);
    }

    @Override
    protected void updateTarget() {
        int pacRow = player.getRow();
        int pacCol = player.getCol();
        double distance = Math.hypot(row - pacRow, col - pacCol);

        if (currentMode == GhostMode.SCATTER || distance < 8) {
            // Lower-left corner of the board
            setTarget(0, (map.getMap().length - 1) * map.getTileSize());
        } else {
            // Chase Pacman
            setTarget(pacCol * map.getTileSize(), pacRow * map.getTileSize());
        }
    }

}
