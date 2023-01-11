package inputs;

import gameStates.GameState;
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
        switch (GameState.state) {
            case MENU:
                //same errorr as the one with Mouselistener cannot have both mouse and key listener method in game for the menu
                gamePanel.getGame().getMenu();
                break;
            case RUNNINGGAME:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                //same errorr as the one with Mouselistener cannot have both mouse and key listener method in game for the menu
                gamePanel.getGame().getMenu();
                break;
            case RUNNINGGAME:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }
}
