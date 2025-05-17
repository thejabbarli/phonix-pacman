package map;

import map.BoardTableModel.CellType;

public interface BoardUpdater {
    void updateCell(int row, int col, CellType type);
}
