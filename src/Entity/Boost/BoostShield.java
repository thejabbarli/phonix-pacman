package Entity.Boost;

import Entity.Player;
import Entity.ghosts.Ghost;
import map.Map;

public class BoostShield extends Boost {
    private static final long BOOST_DURATION_MS = 5000;

    public BoostShield(int row, int col, int tileSize, Map map) {
        super(row, col, tileSize, map);
    }

    @Override
    public void boostTaken(Player player, Ghost ghost) {
        System.out.println("Shield Boost Taken");
        player.setImmortal(true);

        Thread boostTimerThread = new Thread(() -> {
            try {
                Thread.sleep(BOOST_DURATION_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Boost timer interrupted: " + e.getMessage());
            }

            player.setImmortal(false);
            System.out.println("Shield Boost Finished");
            System.out.println("Player immortal status: " + player.getImmortal());
        });

        boostTimerThread.start();
    }
}
