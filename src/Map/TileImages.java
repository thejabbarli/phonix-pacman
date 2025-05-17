package Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TileImages {

    private static BufferedImage wall;
    private static BufferedImage wallBroken;

    static {
        loadImages();
    }

    private static void loadImages() {
        try {
            wall = ImageIO.read(new File("res/tiles/wallBlue.png"));
            wallBroken = ImageIO.read(new File("res/tiles/wallDestroyed.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ImageIcon getImageForTile(char tileType, int tileSize) {
        BufferedImage originalImage = getImage(tileType);
        Image resizedImage = null;
        if (originalImage != null) resizedImage = originalImage.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
        if (originalImage != null) {
            return new ImageIcon(resizedImage);
        } else {
            return null;
        }
    }

    private static BufferedImage getImage(char tileType) {
        BufferedImage image = null;

        switch (tileType) {
            case 'o':
                break;
            case 'O':
                break;
            case '#':
                image = wall;
        }
        return image;
    }
}
