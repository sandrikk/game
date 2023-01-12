package gamestates;

import main.Game;
import ui.MenuButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class MainMenu extends State implements Gamestatemethods {
    private MenuButton[] buttons = new MenuButton[3];

    public MainMenu(Game game) {

        super(game);
        loadButtons();
    }

    private void loadButtons() {
        buttons[0] = new MenuButton(Game.game_width / 2, (int) (150 * Game.scaling), 0, Gamestate.PLAYING);
        buttons[1] = new MenuButton(Game.game_width / 2, (int) (220 * Game.scaling), 0, Gamestate.OPTIONS);
        buttons[2] = new MenuButton(Game.game_width / 2, (int) (290 * Game.scaling), 0, Gamestate.QUIT);
    }

    @Override
    public void update() {
        for (MenuButton mb: buttons) {
            mb.update();
        }

    }

    @Override
    public void draw(Graphics g) {
        for (MenuButton mb: buttons) {
            mb.draw(g);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

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
