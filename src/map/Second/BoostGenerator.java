package map.Second;


import Entity.Boost.*;
import Entity.ghosts.Ghost;
import view.GamePanel;
import map.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoostGenerator {

    private List<Boost> boosts;
    private Map map;
    private Random random;
    private GamePanel gamePanel;
    private Ghost ghost; // Reference to a ghost

    public BoostGenerator(Map map, GamePanel gamePanel, Ghost ghost) {
        this.map = map;
        this.boosts = new ArrayList<>();
        this.random = new Random();
        this.gamePanel = gamePanel;
        this.ghost = ghost; // Store ghost reference

        Thread boostCreationThread = new Thread(this::boostCreationTask);
        boostCreationThread.start();
    }

    private void boostCreationTask() {
        while (true) {
            try {
                Thread.sleep(5000); // Wait for 5 seconds
                System.out.println("WAITED FOR 5 SEC");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (random.nextDouble() < 0.25) { // 25% chance to create boost
                System.out.println("GENERATING new BOOST");
                createRandomBoost();
            }
        }
    }

    private void createRandomBoost() {
        int ghostRow = ghost.getRow();
        int ghostCol = ghost.getCol();

        Boost boost = null;
        switch (random.nextInt(5)) {
            case 0 -> boost = new BoostHealth(ghostRow, ghostCol, map.getTileSize(), map);
            case 1 -> boost = new BoostThunder(ghostRow, ghostCol, map.getTileSize(), map);
            case 2 -> boost = new BoostShield(ghostRow, ghostCol, map.getTileSize(), map);
            case 3 -> boost = new BoostPoison(ghostRow, ghostCol, map.getTileSize(), map);
            case 4 -> boost = new BoostIce(ghostRow, ghostCol, map.getTileSize(), map);
        }

        if (boost != null) {
            boosts.add(boost);
            gamePanel.addBoost(boost);
        }
    }


    public List<Boost> getBoosts() {
        return boosts;
    }
}