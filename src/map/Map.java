package map;

import Main.MapLoader;

import javax.swing.*;
import java.awt.*;

public class Map extends JPanel {
    private char[][] mapData;
    private int tileSize = 50;
    private MapLoader mapLoader = new MapLoader();

    // ✅ Constructor for dimension reading only (no GUI update)
    public Map(String mapFilePath, int tileSize) {
        this.tileSize = tileSize;
        this.mapData = mapLoader.loadMapFromFile(mapFilePath); // just load, no update
    }

    // ✅ Full constructor with GUI update
    public Map(String mapFilePath, int tileSize, BoardUpdater boardUpdater) {
        this.tileSize = tileSize;
        this.mapData = mapLoader.loadMapFromFile(mapFilePath);
        if (boardUpdater != null) {
            mapLoader.setupMap(boardUpdater, mapData);
        }
    }

    public java.awt.Point findTopLeftOpenPosition() {
        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[0].length; x++) {
                if (mapData[y][x] == 'o' || mapData[y][x] == 'O') {
                    return new java.awt.Point(x * tileSize, y * tileSize);
                }
            }
        }
        return new java.awt.Point(tileSize, tileSize); // Fallback
    }

    public char[][] getMap() {
        return mapData;
    }

    public int getWidth() {
        return mapData[0].length * tileSize;
    }

    public int getHeight() {
        return mapData.length * tileSize;
    }

    public int getTileSize() {
        return tileSize;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(getWidth(), getHeight());
    }
}
