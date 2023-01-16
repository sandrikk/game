package gamestates;

import entities.Player;
import levels.LevelHandler;
import main.Game;
import utilz.LoadPlayerSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Date;

public class Playing extends State implements Gamestatemethods {
    private Player player;
    private LevelHandler levelHandler;

    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.game_width);
    private int rightBorder = (int) (0.8 * Game.game_width);
    private int levelWideTiles = LoadPlayerSave.GetLevelInfo()[0].length;
    private int maximumTilesOffset = levelWideTiles - Game.tiles_in_width;
    private int maximumLevelOffsetX = maximumTilesOffset * Game.tiles_size;


    private long lastTime = new Date().getTime() / 1000;
    private int count = 101;

    public Playing(Game game) {
        super(game);
        initClasses();
    }


    private void initClasses() {
        levelHandler = new LevelHandler(game);
        player = new Player(200, 200, (int) (64 * Game.scaling), (int) (40 * Game.scaling));
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
    }


    public void windowFocusLost() {
        player.resetDirectionsBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {

        levelHandler.update();
        player.update();
        checkIfCloseToBorder();

    }
    private void checkIfCloseToBorder() {
        int playerX = (int) player.getImagebox().x;
        int difference = playerX - xLevelOffset;

        if (difference > rightBorder){
            xLevelOffset += difference - rightBorder;
        }else if (difference < leftBorder){
            xLevelOffset += difference-leftBorder;
        }
        if (xLevelOffset > maximumLevelOffsetX){
            xLevelOffset = maximumLevelOffsetX;
        }else if (xLevelOffset < 0)
            xLevelOffset = 0;
    }


    @Override
    public void draw(Graphics g) {
        levelHandler.drawBackground(g,xLevelOffset);
        levelHandler.draw(g);
        player.render(g);
        showTimer(g);


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            // player.setAttacking(true);

        }


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

            case KeyEvent.VK_A:
                player.setLeft(true);
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
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }

    }

    public void showTimer(Graphics g) {

        long currentTime = new Date().getTime() / 1000;

        if (currentTime - lastTime >= 1) {
            count--;
            this.lastTime = new Date().getTime() / 1000;
        }

            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g.drawString(String.valueOf(count), 1150, 50);

        if (count==0){
            System.exit(0);
        }

    }


}
