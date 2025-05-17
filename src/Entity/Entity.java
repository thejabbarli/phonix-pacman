package Entity;

import map.Map;

public abstract class Entity {
    protected int entityX;
    protected int entityY;
    protected int entitySpeed;
    protected int tileSize;

    protected Map map;
    protected AllDirections currentDirection = AllDirections.NULL;

    public Entity(int x, int y, int speed, Map map) {
        this.entityX = x;
        this.entityY = y;
        this.entitySpeed = speed;
        this.map = map;
        this.tileSize = map.getTileSize();
    }

    public void setPosition(int x, int y) {
        this.entityX = x;
        this.entityY = y;
    }

    public int getXCoordinate() {
        return entityX;
    }

    public int getYCoordinate() {
        return entityY;
    }

    public int getSpeed() {
        return entitySpeed;
    }

    public void setCurrentDirection(AllDirections direction) {
        this.currentDirection = direction;
    }

    public AllDirections getCurrentDirection() {
        return currentDirection;
    }

    public void move() {
        int nextX = entityX;
        int nextY = entityY;

        switch (currentDirection) {
            case UP -> nextY -= entitySpeed;
            case DOWN -> nextY += entitySpeed;
            case LEFT -> nextX -= entitySpeed;
            case RIGHT -> nextX += entitySpeed;
            default -> {}
        }

        if (!checkCollisionWithMap(map.getMap(), nextX, nextY)) {
            entityX = nextX;
            entityY = nextY;
        }
    }

    public boolean checkCollisionWithMap(char[][] mapData, int x, int y) {
        int tileX = x / tileSize;
        int tileY = y / tileSize;

        if (tileX < 0 || tileX >= mapData[0].length || tileY < 0 || tileY >= mapData.length) {
            return true; // Out of bounds
        }

        char tile = mapData[tileY][tileX];
        return !isWalkable(tile);
    }

    private boolean isWalkable(char tile) {
        return tile == 'o' || tile == 'O';
    }

    public abstract void run();

    public abstract char[][] getMap();
}
