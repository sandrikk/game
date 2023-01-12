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


        }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}
