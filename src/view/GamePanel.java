package view;

import Entity.Boost.Boost;
import map.BoardTableModel;
import map.BoardTableModel.CellType;
import map.BoardUpdater;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements BoardUpdater {
    private final JTable gameTable;
    private final BoardTableModel boardModel;
    private final int tileSize = 32; // adjust as needed

    public GamePanel(int rows, int cols) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        boardModel = new BoardTableModel(rows, cols);
        gameTable = new JTable(boardModel);
        gameTable.setDefaultRenderer(Object.class, new BoardCellRenderer(tileSize));

        gameTable.setRowHeight(tileSize);
        for (int i = 0; i < gameTable.getColumnModel().getColumnCount(); i++) {
            gameTable.getColumnModel().getColumn(i).setPreferredWidth(tileSize);
        }

        gameTable.setShowGrid(false);
        gameTable.setIntercellSpacing(new Dimension(0, 0));
        gameTable.setEnabled(false);

        add(new JScrollPane(gameTable), BorderLayout.CENTER);
    }

    public void addBoost(Boost boost) {
        updateCell(boost.getRow(), boost.getCol(), CellType.BOOST);
    }

    public void setCell(int row, int col, CellType type) {
        boardModel.setCell(row, col, type);
    }

    public void resetBoard() {
        boardModel.resetBoard();
    }

    @Override
    public void updateCell(int row, int col, CellType type) {
        setCell(row, col, type);
    }

}
