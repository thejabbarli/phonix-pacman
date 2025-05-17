package Entity;

public enum AllDirections {
    UP,
    LEFT,
    DOWN,
    RIGHT,
    NULL;

    public AllDirections opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
            default -> NULL;
        };
    }
}
