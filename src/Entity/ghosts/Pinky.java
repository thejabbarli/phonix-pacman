package Entity.ghosts;

import Entity.AllDirections;
import Entity.Player;
import map.BoardUpdater;
import map.Map;

public class Pinky extends Ghost {
    public Pinky(int startRow, int startCol, Map map, BoardUpdater boardUpdater, Player player) {
        super(startRow, startCol, map, boardUpdater, player);
    }

    @Override
    protected void updateTarget() {
        int pacRow = player.getRow();
        int pacCol = player.getCol();
        int tileSize = map.getTileSize();

        AllDirections dir = player.getCurrentDirection();

        int targetRow = pacRow;
        int targetCol = pacCol;

        switch (dir) {
            case UP -> {
                targetRow -= 4;
                targetCol -= 4; // original Pac-Man bug
            }
            case DOWN -> targetRow += 4;
            case LEFT -> targetCol -= 4;
            case RIGHT -> targetCol += 4;
        }

        setTarget(targetCol * tileSize, targetRow * tileSize);
    }

}
