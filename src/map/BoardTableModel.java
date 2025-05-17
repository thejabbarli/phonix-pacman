package map;

import javax.swing.table.AbstractTableModel;

public class BoardTableModel extends AbstractTableModel {
    public enum CellType {
        EMPTY, WALL, PLAYER, GHOST, POINT, BOOST
    }

    private final int rows;
    private final int cols;
    private final CellType[][] board;

    public BoardTableModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.board = new CellType[rows][cols];
        resetBoard();
    }

    public void resetBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = CellType.EMPTY;
            }
        }
        fireTableDataChanged();
    }

    public void setCell(int row, int col, CellType type) {
        board[row][col] = type;
        fireTableCellUpdated(row, col);
    }

    public CellType getCell(int row, int col) {
        return board[row][col];
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return cols;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return board[rowIndex][columnIndex];
    }
}
