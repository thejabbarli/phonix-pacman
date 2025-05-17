package Entity.ghosts;

import Entity.AllDirections;
import Entity.Player;
import map.BoardUpdater;
import map.Map;

public class Inky extends Ghost {
    private final Ghost partnerGhost;

    public Inky(int startRow, int startCol, Map map, BoardUpdater boardUpdater, Player player, Ghost partnerGhost) {
        super(startRow, startCol, map, boardUpdater, player);
        this.partnerGhost = partnerGhost;
    }

    @Override
    protected void updateTarget() {
        int tileSize = map.getTileSize();

        int pacRow = player.getRow();
        int pacCol = player.getCol();
        int aheadRow = pacRow;
        int aheadCol = pacCol;

        AllDirections dir = player.getCurrentDirection();
        switch (dir) {
            case UP -> aheadRow -= 2;
            case DOWN -> aheadRow += 2;
            case LEFT -> aheadCol -= 2;
            case RIGHT -> aheadCol += 2;
        }

        int partnerRow = partnerGhost.getRow();
        int partnerCol = partnerGhost.getCol();

        int vectorRow = (aheadRow - partnerRow) * 2;
        int vectorCol = (aheadCol - partnerCol) * 2;

        int targetRow = partnerRow + vectorRow;
        int targetCol = partnerCol + vectorCol;

        setTarget(targetCol * tileSize, targetRow * tileSize);
    }

}
