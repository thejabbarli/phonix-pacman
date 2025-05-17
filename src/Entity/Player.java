package Entity;

import Main.Keys;
import Map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Player extends Entity implements Runnable {
    private static final int NUM_FRAMES = 3;
    private int playerX;
    private int playerY;
    private int playerSpeed;
    private Keys keyManager;
    private Thread animationThread;
    private ImageIcon myFrame;
    private ImageIcon[] pacmanFrames;
    private AllDirections currentDirectionP = AllDirections.NULL;
    private AllDirections queuedDirectionP = AllDirections.NULL;

    private boolean immortal;
    private int lives;

    private Map map;

    public Player(int playerX, int playerY, int playerSpeed, Map map, String myImage, Keys keyManager) {
        super(playerX, playerY, playerSpeed, myImage);

        this.playerX = playerX;
        this.playerY = playerY;
        this.playerSpeed = playerSpeed;
        this.keyManager = keyManager;
        this.map = map;
        this.lives = 3;
        this.immortal = false;

        // Add explicit position and size settings
        setBounds(playerX, playerY, map.getTileSize(), map.getTileSize());

        loadPacmanFrames();

        // Set initial icon immediately after loading frames
        setIcon(pacmanFrames[0]);

        // Make sure the component is visible
        setVisible(true);

        animationThread = new Thread(this);
        animationThread.start();
    }

    private void loadPacmanFrames() {
        pacmanFrames = new ImageIcon[NUM_FRAMES];

        // Load and resize images to match tile size
        int tileSize = map.getTileSize();

        try {
            for (int i = 0; i < NUM_FRAMES; i++) {
                String path = "res/pacman/pacman" + (i+1) + ".png";
                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage();
                Image resizedImg = img.getScaledInstance(tileSize, tileSize, Image.SCALE_SMOOTH);
                pacmanFrames[i] = new ImageIcon(resizedImg);
            }
        } catch (Exception e) {
            System.err.println("Error loading Pacman images: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        int frameIndex = 0;
        boolean increasing = true;

        while (true) {
            try {
                // Only set icon if valid
                if (pacmanFrames != null && frameIndex >= 0 && frameIndex < pacmanFrames.length && pacmanFrames[frameIndex] != null) {
                    myFrame = pacmanFrames[frameIndex];
                    setImageIcon(myFrame);
                }

                // Update animation frame index
                if (increasing) {
                    frameIndex++;
                    if (frameIndex >= NUM_FRAMES - 1) {
                        increasing = false;
                    }
                } else {
                    frameIndex--;
                    if (frameIndex <= 0) {
                        increasing = true;
                    }
                }

                // Ensure visibility and position
                setVisible(true);
                setLocation(playerX, playerY);

                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Error in player animation: " + e.getMessage());
            }
        }
    }

    public ImageIcon getImageIcon() {
        return myFrame;
    }

    // Setter method for setting the ImageIcon
    public void setImageIcon(ImageIcon icon) {
        if (icon == null) {
            System.err.println("Warning: Attempting to set null icon for Pacman");
            return;
        }

        this.myFrame = icon;
        setIcon(icon);

        // Force a repaint
        revalidate();
        repaint();
    }

    public void setKeyManager(Keys keyManager) {
        this.keyManager = keyManager;
    }

    public void loseLife() {
        lives--;
        System.out.println("Lives remaining: " + lives);
        if (lives <= 0) {
            // Handle game over (e.g., stop the game, show game over screen, etc.)
            System.out.println("Game Over!");
        } else {
            // Reset player position or take other actions
            setPlayerPosition(50, 50);
            // Optionally, start a boost timer thread here
        }
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int x) {
        lives = x;
    }

    public boolean getImmortal() {
        return immortal;
    }

    public void setImmortal(boolean isImmortal) {
        immortal = isImmortal;
    }

    public void movePlayer() {
        int nextPlayerX = playerX;
        int nextPlayerY = playerY;

        // Calculate the next position based on the queued direction
        queuedDirectionP = keyManager.getQueuedDirection();

        switch (queuedDirectionP) {
            case UP:
                nextPlayerY = playerY - playerSpeed;
                break;
            case LEFT:
                nextPlayerX = playerX - playerSpeed;
                break;
            case DOWN:
                nextPlayerY = playerY + playerSpeed;
                break;
            case RIGHT:
                nextPlayerX = playerX + playerSpeed;
                break;
            default:
                break;
        }

        // Check if the next position in the queued direction is walkable
        if (queuedDirectionP != AllDirections.NULL && !collidesWithWalls(nextPlayerX, nextPlayerY, queuedDirectionP)) {
            AllDirections previousDirection = currentDirectionP;
            currentDirectionP = queuedDirectionP;
            queuedDirectionP = AllDirections.NULL;

            // Only update rotation if direction actually changed
            if (previousDirection != currentDirectionP) {
                updatePacmanRotation();
            }
        }

        // Calculate the next position based on the current direction
        nextPlayerX = playerX;
        nextPlayerY = playerY;

        switch (currentDirectionP) {
            case UP:
                nextPlayerY = playerY - playerSpeed;
                break;
            case LEFT:
                nextPlayerX = playerX - playerSpeed;
                break;
            case DOWN:
                nextPlayerY = playerY + playerSpeed;
                break;
            case RIGHT:
                nextPlayerX = playerX + playerSpeed;
                break;
            default:
                break;
        }

        // Update the position if the movement in the current direction is possible
        if (!collidesWithWalls(nextPlayerX, nextPlayerY, currentDirectionP)) {
            playerX = nextPlayerX;
            playerY = nextPlayerY;
        }

        setLocation(playerX, playerY);
        setVisible(true); // Ensure the player is visible
    }

    private void updatePacmanRotation() {
        // Reset images to original orientation first
        loadPacmanFrames();

        // Now apply the appropriate transformation
        switch (currentDirectionP) {
            case LEFT:
                // Flip horizontally
                for (int i = 0; i < pacmanFrames.length; i++) {
                    pacmanFrames[i] = flipImageHorizontally(pacmanFrames[i]);
                }
                break;
            case UP:
                // Rotate 90 degrees counterclockwise
                for (int i = 0; i < pacmanFrames.length; i++) {
                    pacmanFrames[i] = rotateImage(pacmanFrames[i], 270);
                }
                break;
            case DOWN:
                // Rotate 90 degrees clockwise
                for (int i = 0; i < pacmanFrames.length; i++) {
                    pacmanFrames[i] = rotateImage(pacmanFrames[i], 90);
                }
                break;
            case RIGHT:
                // Original orientation - no transformation needed
                break;
        }

        // Update the current frame
        setImageIcon(pacmanFrames[0]);
    }

    private ImageIcon flipImageHorizontally(ImageIcon icon) {
        if (icon == null) return null;

        Image img = icon.getImage();
        BufferedImage buffImg = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = buffImg.createGraphics();
        g2d.scale(-1, 1);
        g2d.drawImage(img, -icon.getIconWidth(), 0, null);
        g2d.dispose();

        return new ImageIcon(buffImg);
    }

    private ImageIcon rotateImage(ImageIcon icon, int degrees) {
        if (icon == null) return null;

        Image img = icon.getImage();
        BufferedImage buffImg = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        Graphics2D g2d = buffImg.createGraphics();
        g2d.rotate(Math.toRadians(degrees), icon.getIconWidth()/2, icon.getIconHeight()/2);
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();

        return new ImageIcon(buffImg);
    }

    private boolean collidesWithWalls(int x, int y, AllDirections direction) {
        int tileSize = map.getTileSize();

        // Calculate the indices of the tiles around the player
        int leftTileX = x / tileSize;
        int rightTileX = (x + tileSize - 1) / tileSize;
        int upTileY = y / tileSize;
        int downTileY = (y + tileSize - 1) / tileSize;

        // Ensure indices are within bounds
        if (leftTileX < 0 || rightTileX >= map.getMap()[0].length || upTileY < 0 || downTileY >= map.getMap().length) {
            return true;
        }

        // Check for collisions based on the direction
        switch (direction) {
            case UP:
                return !isWalkable(map.getMap()[upTileY][leftTileX]) || !isWalkable(map.getMap()[upTileY][rightTileX]);
            case DOWN:
                return !isWalkable(map.getMap()[downTileY][leftTileX]) || !isWalkable(map.getMap()[downTileY][rightTileX]);
            case LEFT:
                return !isWalkable(map.getMap()[upTileY][leftTileX]) || !isWalkable(map.getMap()[downTileY][leftTileX]);
            case RIGHT:
                return !isWalkable(map.getMap()[upTileY][rightTileX]) || !isWalkable(map.getMap()[downTileY][rightTileX]);
            default:
                return false;
        }
    }

    // Helper method to determine if a tile is walkable
    private boolean isWalkable(char tile) {
        return tile == 'o' || tile == 'O';
    }

    @Override
    public char[][] getMap() {
        return new char[0][];
    }

    public void setPlayerPosition(int x, int y) {
        playerX = x;
        playerY = y;
    }
    // In Player.java
    public AllDirections getCurrentDirection() {
        return currentDirectionP;
    }
    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(int playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

}