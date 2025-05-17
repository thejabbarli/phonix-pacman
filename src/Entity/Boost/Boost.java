package Entity.Boost;

import Entity.Player;
import Entity.ghosts.Ghost;
import Map.Map;

import javax.swing.*;

public abstract class Boost extends JLabel {
    protected int x, y, size;
    protected Map map;
    private ImageIcon icon;

    public Boost(int x, int y, int size, Map map, String imagePath) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.map = map;
        this.icon = new ImageIcon(imagePath);
        setBounds(x, y, size, size);
        setIcon(icon);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract void boostTaken(Player player, Ghost ghost);

}
