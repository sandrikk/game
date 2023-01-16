package levels;

import main.Game;
import utilz.LoadPlayerSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static main.Game.tiles_size;

public class LevelHandler {

    private Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage backgroundSprite;
    private Level levelOne;
    public LevelHandler(Game game) {
        this.game = game;
        backgroundSprite = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Background_Atlas);
        importOutsideSprites();
        levelOne = new Level(LoadPlayerSave.GetLevelInfo());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Level_Atlas);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i*32,j*32,32,32);
            }
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        for (int j = 0; j < Game.tiles_in_height; j++) {
            for (int i = 0; i < levelOne.getLevelData()[0].length; i++) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index],tiles_size*i - xLevelOffset,tiles_size*j,tiles_size,tiles_size,null);
            }
        }
    }

    public void drawBackground(Graphics g, int xLevelOffset) {
        g.drawImage(backgroundSprite,0,0,null);
    }

    public void update(){

    }

    public Level getCurrentLevel() {
        return levelOne;
    }
}
