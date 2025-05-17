package Entity.Boost;

import Entity.Player;
import Entity.ghosts.Ghost;
import map.Map;

public class BoostIce extends Boost {
    private static final int BOOST_DURATION_MS = 5000;

    public BoostIce(int row, int col, int tileSize, Map map) {
        super(row, col, tileSize, map);
    }

    @Override
    public void boostTaken(Player player, Ghost ghost) {
        System.out.println("ICE Boost Taken");

        ghost.setGhostSpeed(0);
        System.out.println("Ghost speed now: " + ghost.getSpeed());

        Thread boostTimerThread = new Thread(() -> {
            try {
                Thread.sleep(BOOST_DURATION_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            ghost.setGhostSpeed(5);
            System.out.println("ICE BOOST FINISHED");
        });

        boostTimerThread.start();
    }
}
