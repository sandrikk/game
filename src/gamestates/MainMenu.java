package gamestates;

import main.Game;
import ui.MenuButton;
import utilz.LoadPlayerSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class MainMenu extends State implements Gamestatemethods {
    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage menu_background;

    public MainMenu(Game game) {

        super(game);
        loadButtons();
        loadMenuBackground();
    }

    private void loadMenuBackground() {
        menu_background = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Menu_Background);
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.game_width / 2, (int) (150 * Game.scaling), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.game_width / 2, (int) (220 * Game.scaling), 1, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.game_width / 2, (int) (290 * Game.scaling), 2, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb: buttons) {
            mb.update();
        }

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(menu_background,0,0,null);

        for (MenuButton mb: buttons) {
            mb.draw(g);
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb: buttons) {
            if (isIn(e,mb)) {
                mb.setMousePressed(true);
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb: buttons) {
            if (isIn(e,mb)) {
                if (mb.isMousePressed()) {
                    mb.applyGameState();
                }
                break;
            }
        }
        resetButtons();

    }

    private void resetButtons() {
        for (MenuButton mb: buttons) {
            mb.resetBools();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb: buttons) {
            mb.setMouseOver(false);
        }

        for (MenuButton mb: buttons) {
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            Gamestate.state = Gamestate.PLAYING;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
