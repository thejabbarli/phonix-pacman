package Entity.Boost;

import Entity.Player;
import Entity.ghosts.Ghost;
import map.Map;

public class BoostHealth extends Boost {
    private static final int MAX_PART_LIFE = 4;
    private int partLife;

    public BoostHealth(int row, int col, int tileSize, Map map) {
        super(row, col, tileSize, map);
        this.partLife = 0;
    }

    @Override
    public void boostTaken(Player player, Ghost ghost) {
        System.out.println("Heal Boost Taken");
        partLife++;
        if (partLife <= MAX_PART_LIFE) {
            player.setLives(player.getLives() + 1);
        }
    }
}
