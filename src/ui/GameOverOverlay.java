package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utilz.LoadPlayerSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class GameOverOverlay {
    private Playing playing;
    private BufferedImage BufferedGameOver;
    public static final String GameOver = "GameOver.png";


    public GameOverOverlay(Playing playing) {

        this.playing = playing;
        BufferedGameOver = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.GameOver);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.game_width, Game.game_height);

        g.drawImage(BufferedGameOver,0,0,Color.BLACK,null);



    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            playing.resetAll();
            Gamestate.state = Gamestate.MAINMENU;
        }
    }
}
