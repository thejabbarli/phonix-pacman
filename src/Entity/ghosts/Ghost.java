    package Entity.ghosts;

    import Entity.AllDirections;
    import Entity.Entity;
    import Entity.Player;
    import map.BoardUpdater;
    import map.BoardTableModel.CellType;
    import map.Map;

    import java.util.Arrays;
    import java.util.List;

    public abstract class Ghost extends Entity {
        protected int row;
        protected int col;
        protected int ghostSpeed;
        protected boolean poisonated;
        protected Player player;

        protected GhostMode currentMode;
        protected int scatterTimer;
        protected int chaseTimer;

        protected AllDirections currentDirectionG = AllDirections.NULL;
        protected AllDirections queuedDirectionG = AllDirections.NULL;

        protected int targetX;
        protected int targetY;

        protected final Map map;
        protected final BoardUpdater boardUpdater;

        public enum GhostMode {
            CHASE,
            SCATTER,
            FRIGHTENED
        }

        public Ghost(int startRow, int startCol, Map map, BoardUpdater boardUpdater, Player player) {
            super(startCol * map.getTileSize(), startRow * map.getTileSize(), 1, map);
            this.row = startRow;
            this.col = startCol;
            this.ghostSpeed = 1;
            this.map = map;
            this.boardUpdater = boardUpdater;
            this.player = player;

            this.currentMode = GhostMode.SCATTER;
            this.scatterTimer = 700;
            this.chaseTimer = 2000;
            this.poisonated = false;

            boardUpdater.updateCell(row, col, CellType.GHOST);
        }

        public void move() {
            updateMode();

            AllDirections direction = recommendedQueuedDirection();

            int newRow = row, newCol = col;

            switch (direction) {
                case UP -> newRow -= ghostSpeed;
                case DOWN -> newRow += ghostSpeed;
                case LEFT -> newCol -= ghostSpeed;
                case RIGHT -> newCol += ghostSpeed;
            }

            if (canMoveTo(newRow, newCol)) {
                boardUpdater.updateCell(row, col, CellType.EMPTY);
                row = newRow;
                col = newCol;
                boardUpdater.updateCell(row, col, CellType.GHOST);
            }
        }

        private boolean canMoveTo(int r, int c) {
            char[][] mapData = map.getMap();
            return r >= 0 && r < mapData.length && c >= 0 && c < mapData[0].length && isWalkable(mapData[r][c]);
        }

        protected boolean isWalkable(char tile) {
            return tile == 'o' || tile == 'O';
        }

        public void setPoisonated(boolean poisonated) {
            this.poisonated = poisonated;
            currentMode = poisonated ? GhostMode.FRIGHTENED : GhostMode.CHASE;
        }

        public boolean getPoisonated() {
            return poisonated;
        }

        public void updateMode() {
            if (!poisonated) {
                if (currentMode == GhostMode.SCATTER) {
                    if (--scatterTimer <= 0) {
                        currentMode = GhostMode.CHASE;
                        chaseTimer = 2000;
                    }
                } else if (currentMode == GhostMode.CHASE) {
                    if (--chaseTimer <= 0) {
                        currentMode = GhostMode.SCATTER;
                        scatterTimer = 700;
                    }
                }
            }
        }

        public AllDirections recommendedQueuedDirection() {
            updateTarget();

            List<DirectionChoice> choices = Arrays.asList(
                    new DirectionChoice(AllDirections.RIGHT, row, col + 1),
                    new DirectionChoice(AllDirections.LEFT, row, col - 1),
                    new DirectionChoice(AllDirections.UP, row - 1, col),
                    new DirectionChoice(AllDirections.DOWN, row + 1, col)
            );

            return choices.stream()
                    .filter(d -> canMoveTo(d.r, d.c) && d.direction != currentDirectionG.opposite())
                    .min((a, b) -> Double.compare(distanceTo(a.r, a.c), distanceTo(b.r, b.c)))
                    .map(d -> poisonated ? d.direction.opposite() : d.direction)
                    .orElse(AllDirections.NULL);
        }

        private double distanceTo(int r, int c) {
            return Math.hypot(r - targetY(), c - targetX());
        }

        protected record DirectionChoice(AllDirections direction, int r, int c) {}

        protected abstract void updateTarget();

        public void setGhostSpeed(int speed) {
            this.ghostSpeed = speed;
        }

        protected int targetX() {
            return targetX / map.getTileSize();
        }

        protected int targetY() {
            return targetY / map.getTileSize();
        }

        public void setTarget(int x, int y) {
            this.targetX = x;
            this.targetY = y;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        @Override
        public void run() {
            // Optional: continuous AI movement logic
        }

        @Override
        public char[][] getMap() {
            return map.getMap();
        }
    }
