package main;

import entities.Player;
import gamestates.ChooseCharacter;
import gamestates.Gamestate;
import gamestates.MainMenu;
import gamestates.Playing;
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
    private LevelHandler levelHandler;

    private Playing playing;
    private MainMenu menu;
    private ChooseCharacter choosecharacter;

    public final static int default_size_tiles = 32;
    public final static float scaling = 1.5f;
    public final static int tiles_in_width = 26;
    public final static int tiles_in_height = 14;
    public final static int tiles_size = (int) (default_size_tiles * scaling);
    public final static int game_width = tiles_size * tiles_in_width;
    public final static int game_height = tiles_size * tiles_in_height;


    public Game() {
        initClasses();
        gamePanel=new GamePanel(this);
        gameWindow= new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void initClasses() {
        menu = new MainMenu(this);
        choosecharacter = new ChooseCharacter(this);
        playing = new Playing(this);


    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void update() {
          //  gamePanel.updateGame();

        switch(Gamestate.state) {
            case MAINMENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case CHOOSECHARACTER:
                choosecharacter.update();
                break;
            case QUIT:
            default:
                System.exit(0);
                break;

        }


    }
    public void render(Graphics g) {

        switch(Gamestate.state) {
            case MAINMENU:
                menu.draw(g);

                break;
            case PLAYING:
                playing.draw(g);
                //levelHandler.drawBackground(g);
                //levelHandler.draw(g);
                //player.render(g);
                break;
            case CHOOSECHARACTER:
                choosecharacter.draw(g);

                break;
            default:
                break;

        }

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

        while (true ) {

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
        if (Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().resetDirectionsBooleans();

        }

    }
    public MainMenu getMenu(){
        return menu;
    }

    public ChooseCharacter getCharacter(){
        return choosecharacter;
    }

    public Playing getPlaying(){
        return playing;
    }

}