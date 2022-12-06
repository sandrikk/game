package main;

import java.awt.*;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    public GamePanel() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.fillRect(50,50,50,100);
    }
}
