package Entity.Boost;

import Entity.Player;
import Entity.ghosts.Ghost;
import map.Map;

public class BoostThunder extends Boost {

    private static final int BOOST_DURATION_MS = 500;

    public BoostThunder(int row, int col, int tileSize, Map map) {
        super(row, col, tileSize, map);
    }

    @Override
    public void boostTaken(Player player, Ghost ghost) {
        System.out.println("Thunder Boost Taken");
        player.setPlayerSpeed(player.getPlayerSpeed() + 30);

        Thread boostTimerThread = new Thread(() -> {
            try {
                Thread.sleep(BOOST_DURATION_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            player.setPlayerSpeed(player.getPlayerSpeed() - 30);
            System.out.println("THUNDER BOOST FINISHED");
        });

        boostTimerThread.start();
    }
}
