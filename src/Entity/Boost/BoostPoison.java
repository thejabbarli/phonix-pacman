package Entity.Boost;

import Entity.Player;
import Entity.ghosts.Ghost;
import map.Map;

public class BoostPoison extends Boost {

    public BoostPoison(int row, int col, int tileSize, Map map) {
        super(row, col, tileSize, map);
    }

    @Override
    public void boostTaken(Player player, Ghost ghost) {
        System.out.println("Poison Boost Taken");

        ghost.setPoisonated(true);
        Thread boostTimerThread = new Thread(() -> {
            try {
                Thread.sleep(5000);  // Poison effect duration
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            ghost.setPoisonated(false);
        });

        boostTimerThread.start();
    }
}
