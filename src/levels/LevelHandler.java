package levels;

import main.Game;
import utilz.LoadPlayerSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelHandler {

    private Game game;
    private BufferedImage levelSprite;
    public LevelHandler(Game game) {
        this.game = game;
        levelSprite = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Level_Atlas);

    }

    public void draw(Graphics g) {
        g.drawImage(levelSprite,0,0,null);

    }

    public void update(){

    }
}
