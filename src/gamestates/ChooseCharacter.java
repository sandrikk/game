
package gamestates;

import entities.Player;
import main.Game;
import ui.CharacterButton;
import utilz.LoadPlayerSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class ChooseCharacter extends State implements Gamestatemethods {

    private CharacterButton[] buttons = new CharacterButton[3];
    private BufferedImage menu_background;

    public ChooseCharacter(Game game) {

        super(game);
        loadButtons();
        loadMenuBackground();
    }

    private void loadMenuBackground() {
        menu_background = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Menu_Background);
    }

    private void loadButtons() {
        buttons[0] = new CharacterButton(Game.game_width / 2 - 300, (int) (100 * Game.scaling), 0,  Gamestate.PLAYING);
        buttons[1] = new CharacterButton(Game.game_width / 2 - 75, (int) (100 * Game.scaling), 1,  Gamestate.PLAYING);
        buttons[2] = new CharacterButton(Game.game_width / 2 + 125, (int) (100 * Game.scaling), 2,  Gamestate.PLAYING);
    }

    @Override
    public void update() {
        for (CharacterButton chb: buttons) {
            chb.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(menu_background,0,0,null);

        for (CharacterButton chb: buttons) {
            chb.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (CharacterButton chb: buttons) {
            if (isInCh(e,chb)) {
                chb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (CharacterButton chb: buttons) {
            if (isInCh(e,chb)) {
                if (chb.isMousePressed()) {
                    chb.applyGameState();
                }
                break;
            }
        }
        resetButtons();

    }

    private void resetButtons() {
        for (CharacterButton chb: buttons) {
            chb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (CharacterButton chb: buttons) {
            chb.setMouseOver(false);
        }

        for (CharacterButton chb: buttons) {
            if (isInCh(e, chb)) {
                chb.setMouseOver(true);
                break;
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {

            case KeyEvent.VK_Q:

                break;
            case KeyEvent.VK_D:

                break;
            case KeyEvent.VK_SPACE:

                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }




}

