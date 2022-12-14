package inputs;

import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static utilz.Constants.Directions.*;

public class Keyboardinputs implements KeyListener {
    private GamePanel gamePanel;
    public Keyboardinputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                //gamePanel.changeYDelta(-5);
                gamePanel.setDirection(UP);
                break;
            case KeyEvent.VK_A:
                //gamePanel.changeXDelta(-5);
                gamePanel.setDirection(LEFT);
                break;
            case KeyEvent.VK_S:
                //gamePanel.changeYDelta(5);
                gamePanel.setDirection(DOWN);
                break;
            case KeyEvent.VK_D:
                //gamePanel.changeXDelta(5);
                gamePanel.setDirection(RIGHT);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_A:
            case KeyEvent.VK_S:
            case KeyEvent.VK_D:
                gamePanel.setMoving(false);
                break;

        }
    }
}
