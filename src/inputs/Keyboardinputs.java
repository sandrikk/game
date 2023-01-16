package inputs;

import gamestates.Gamestate;
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
        switch (Gamestate.state) {
            case MAINMENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            default:
                break;


        }
    }

        @Override
        public void keyReleased(KeyEvent e){
            switch (Gamestate.state) {
                case MAINMENU:
                    gamePanel.getGame().getMenu().keyReleased(e);
                    break;
                case PLAYING:
                    gamePanel.getGame().getPlaying().keyReleased(e);
                    break;
                default:
                    break;

            }

        }

}
