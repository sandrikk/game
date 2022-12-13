package main;

public class Game implements Runnable{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_Set = 133;
    private final int UPS_Set= 210;

    public Game() {
        gamePanel=new GamePanel();
        gameWindow= new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();

    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }
public void update() {
        gamePanel.updateGame();
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
}