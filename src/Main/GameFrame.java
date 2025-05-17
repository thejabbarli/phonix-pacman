package Main;

import Entity.Player;
import Entity.ghosts.Blinky;
import map.Map;
import map.Third.GameSettings;
import view.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    private MainMenu mainMenu;
    private MapSelection mapSelection;
    GamePanel gamePanel;

    public GameFrame() {
        setTitle("Pac-Man");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.BLACK);
        setResizable(true);
        showMainMenu();
    }

    public void setMapConfig(String mapName) {
        GameSettings.getInstance().setMapConfig(mapName);
    }

    public void showMainMenu() {
        mainMenu = new MainMenu();
        setContentPane(mainMenu);
        pack();
        setLocationRelativeTo(null);
    }

    public void showMapSelectionScreen() {
        mapSelection = new MapSelection(this);
        setContentPane(mapSelection);
        revalidate();
        pack();
    }

    public void startGame(String mapFilePath) {
        Keys keyListener = new Keys();
        setFocusable(true);
        addKeyListener(keyListener);

        // ✅ Temp map to get dimensions
        Map tempMap = new Map(mapFilePath, 50);
        int rows = tempMap.getMap().length;
        int cols = tempMap.getMap()[0].length;

        // ✅ Create GamePanel first (needed as boardUpdater)
        GamePanel gamePanel = new GamePanel(rows, cols);
        this.gamePanel = gamePanel;

        // ✅ Create the actual Map now that we have boardUpdater
        Map map = new Map(mapFilePath, 50, gamePanel);

        setContentPane(gamePanel);

        // ✅ Spawn player and ghost
        Player player = new Player(1, 1, map, gamePanel);
        Blinky ghost1 = new Blinky(3, 3, map, gamePanel, player);

        pack();
        setLocationRelativeTo(null);
    }
}
