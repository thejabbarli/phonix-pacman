package Entity;

import map.BoardUpdater;
import map.BoardTableModel.CellType;
import map.Map;

public class Player extends Entity {
    private final BoardUpdater boardUpdater;
    private final Map map;
    private int row;
    private int col;
    private int lives = 3;
    private boolean immortal = false;
    private int playerSpeed = 1;
    private AllDirections currentDirection = AllDirections.NULL;

    public Player(int startRow, int startCol, Map map, BoardUpdater boardUpdater) {
        super(startCol * map.getTileSize(), startRow * map.getTileSize(), 1, map);
        this.row = startRow;
        this.col = startCol;
        this.map = map;
        this.boardUpdater = boardUpdater;

        boardUpdater.updateCell(row, col, CellType.PLAYER);
    }

    public void move(int dx, int dy) {
        int newRow = row + dy;
        int newCol = col + dx;

        char[][] mapData = map.getMap();
        if (isInBounds(mapData, newRow, newCol) && isWalkable(mapData[newRow][newCol])) {
            boardUpdater.updateCell(row, col, CellType.EMPTY);

            row = newRow;
            col = newCol;

            boardUpdater.updateCell(row, col, CellType.PLAYER);
        }
    }

    private boolean isWalkable(char tile) {
        return tile == 'o' || tile == 'O';
    }

    private boolean isInBounds(char[][] mapData, int r, int c) {
        return r >= 0 && r < mapData.length && c >= 0 && c < mapData[0].length;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }
    public boolean getImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public void setCurrentDirection(AllDirections direction) {
        this.currentDirection = direction;
    }

    public AllDirections getCurrentDirection() {
        return currentDirection;
    }


    @Override
    public void run() {
        // Optional movement thread logic
    }

    @Override
    public char[][] getMap() {
        return map.getMap();
    }
}
