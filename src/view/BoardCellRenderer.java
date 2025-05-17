package view;

import map.BoardTableModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.EnumMap;

public class BoardCellRenderer extends JLabel implements TableCellRenderer {

    private final EnumMap<BoardTableModel.CellType, BufferedImage> images = new EnumMap<>(BoardTableModel.CellType.class);
    private final int tileSize;

    public BoardCellRenderer(int tileSize) {
        this.tileSize = tileSize;
        setOpaque(true);
        loadImages();
    }

    private void loadImages() {
        loadImage(BoardTableModel.CellType.WALL, "/tiles/wallBlue.png");
        loadImage(BoardTableModel.CellType.PLAYER, "/pacman/pacman1.png");
        loadImage(BoardTableModel.CellType.GHOST, "/ghosts/enemyRed.png");
        loadImage(BoardTableModel.CellType.POINT, "/edibles/tileYem.png");
        loadImage(BoardTableModel.CellType.BOOST, "/boosts/boostShield.png");
    }


    private void loadImage(BoardTableModel.CellType type, String path) {
        try {
            InputStream stream = getClass().getResourceAsStream(path);
            if (stream == null) {
                System.err.println("❌ Image not found: " + path);
                return;
            }
            BufferedImage img = ImageIO.read(stream);
            images.put(type, img);
        } catch (Exception e) {
            System.err.println("❌ Failed to load image: " + path);
            e.printStackTrace();
        }
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {

        BoardTableModel.CellType type = (BoardTableModel.CellType) value;
        setBackground(Color.BLACK);

        BufferedImage icon = images.get(type);
        if (icon != null) {
            Image scaled = icon.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaled));
        } else {
            setIcon(null);
        }

        return this;
    }
}
