package gameStates;

import entities.Player;
import levels.LevelHandler;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class RunningGame extends State implements GameStateMethod{
    private Player player;
    private LevelHandler levelHandler;

    public RunningGame(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelHandler = new LevelHandler(game);
        player = new Player(200, 200, (int) (64 * Game.scaling), (int) (40 * Game.scaling));
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
    }



    @Override
    public void update() {
        levelHandler.update();
        player.update();

    }

    @Override
    public void draw(Graphics g) {
levelHandler.draw(g);
player.render(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouse clicked");
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
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_S:
                player.setDown(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_S:
                player.setDown(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }
    public void windowFocusLost() {
        player.resetDirectionsBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
