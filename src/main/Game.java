package main;

import entities.Player;
import levels.LevelHandler;

import java.awt.Graphics;
import java.awt.*;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_Set = 133;
    private final int UPS_Set= 210;
    private Player player;

    public final static int default_size_tiles = 32;
    public final static float scaling = 1.5f;
    public final static int tiles_in_width = 26;
    public final static int tiles_in_height = 14;
    public final static int tiles_size = (int) (default_size_tiles * scaling);
    public final static int game_width = tiles_size * tiles_in_width;
    public final static int game_height = tiles_size * tiles_in_height;
    private LevelHandler levelHandler;

    public Game() {
        initClasses();
        gamePanel=new GamePanel(this);
        gameWindow= new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        player=new Player(200,200, (int) (64*scaling), (int) (40*scaling));
        levelHandler = new LevelHandler(this);
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void update() {
          //  gamePanel.updateGame();
        levelHandler.update();
        player.update();

    }
    public void render(Graphics g) {
        levelHandler.draw(g);
            player.render(g);

    }
    @Override
    public void run() {

        double timePerFrame = 1000000000.0/FPS_Set;
        double timePerUpdate= 1000000000.0/UPS_Set;

        long previousTime = System.nanoTime();

        int frames= 0;
        int updates=0;
        long lastCheck= System.currentTimeMillis();

        double indexUpdate=0;
        double indexFrame=0;

        while (true) {

            long currentTime= System.nanoTime();

            indexUpdate += (currentTime-previousTime) /timePerUpdate;
            indexFrame +=  (currentTime-previousTime) /timePerFrame;
            previousTime = currentTime;

            if (indexUpdate>=1) {
                update();
                updates++;
                indexUpdate--;
            }
            if (indexFrame>=1) {
                gamePanel.repaint();
                frames++;
                indexFrame--;
            }


            if(System.currentTimeMillis()- lastCheck >= 1000) {
                lastCheck= System.currentTimeMillis();
                System.out.println("FPS: " + frames + "| UPS:" + updates);
                frames= 0;
                updates=0;
            }


        }
    }
    public void windowFocusLost() {
        player.resetDirectionsBooleans();
    }
    public Player getPlayer() {
        return player;
    }
}