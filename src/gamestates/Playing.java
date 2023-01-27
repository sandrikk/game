package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelHandler;
import main.Game;
import ui.GameOverOverlay;
import utilz.LoadPlayerSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Playing extends State implements Gamestatemethods {
    private Player player;
    private LevelHandler levelHandler;
    private EnemyManager enemyManager;
    private GameOverOverlay gameOverOverlay;
    private int healthBarWidth = (int) (150 * Game.scaling);
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;

    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.game_width);
    private int rightBorder = (int) (0.8 * Game.game_width);
    private int levelWideTiles = LoadPlayerSave.GetLevelInfo()[0].length;
    private int maximumTilesOffset = levelWideTiles - Game.tiles_in_width;
    private int maximumLevelOffsetX = maximumTilesOffset * Game.tiles_size;



    private long lastTime = new Date().getTime() / 1000;
    private int count = 101;

    private BufferedImage backgroundImg;
    private boolean gameOver;

    public Playing(Game game) {
        super(game);
        initClasses();

    }


    private void initClasses() {

        levelHandler = new LevelHandler(game);
        enemyManager = new EnemyManager(this);
        Player.loadAnimations();
        player = new Player(200, 200, (int) (64 * Game.scaling), (int) (40 * Game.scaling), this);
        player.loadLevelData(levelHandler.getCurrentLevel().getLevelData());
        gameOverOverlay = new GameOverOverlay(this);

    }


    public void windowFocusLost() {
        player.resetDirectionsBooleans();
    }

    public Player getPlayer() {

        return player;
    }

    @Override
    public void update() {
        if (!gameOver) {
            levelHandler.update();

            player.update();

            enemyManager.update(levelHandler.getCurrentLevel().getLevelData(),player);
            checkIfCloseToBorder();
            if (player.getImagebox().y>=540){
                currentHealth = 0;
            }
            if (currentHealth == 0){
                gameOver = true;
                currentHealth = 100;
            }
        }


    }

    public void resetAll() {
        gameOver = false;
        player.resetAll();
        enemyManager.resetAllEnemies();

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;

    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
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
        levelHandler.draw(g, xLevelOffset);
        Player.loadAnimations();
        player.render(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);
        showTimer(g);

        if (gameOver) {
            gameOverOverlay.draw(g);
            currentHealth = 100;
            count = 101;

        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver)
            if (e.getButton() == MouseEvent.BUTTON1) {
                 player.setAttacking(true);

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
        if (gameOver)
            gameOverOverlay.keyPressed(e);
        else
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
        if (!gameOver)
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
        if (!gameOver) {
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
public void checkWherePlayerIs(){
    int playerX = (int) player.getImagebox().x;
    int playerY = (int) player.getImagebox().y;
    System.out.println(playerX);
    System.out.println(playerY);


}


}
