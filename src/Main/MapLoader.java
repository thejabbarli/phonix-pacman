package Main;

import map.BoardUpdater;
import map.BoardTableModel.CellType;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MapLoader {

    public char[][] loadMapFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .map(line -> line.replaceAll(" ", "").toCharArray())
                    .toArray(char[][]::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new char[0][0];
        }
    }

    public void setupMap(BoardUpdater boardUpdater, char[][] mapData) {
        for (int row = 0; row < mapData.length; row++) {
            for (int col = 0; col < mapData[row].length; col++) {
                char ch = mapData[row][col];
                CellType type = switch (ch) {
                    case '#' -> CellType.WALL;
                    case 'o', 'O' -> CellType.POINT;
                    default -> CellType.EMPTY;
                };
                boardUpdater.updateCell(row, col, type);
            }
        }
    }
}
