package entities;

import gamestates.Playing;
import utilz.LoadPlayerSave;
import static utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[][] pumaArr;
    private ArrayList<Puma> pumas = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImages();
        addEnemies();
    }

    private void addEnemies() {
        pumas = LoadPlayerSave.getPumas();
        System.out.println(pumas.size());
    }

    public void update(int[][] lvlData) {
        for (Puma p: pumas) {
            p.update(lvlData);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawPumas(g, xLevelOffset);
    }

    private void drawPumas(Graphics g, int xLevelOffset) {
        for (Puma p: pumas) {
            g.drawImage(pumaArr[p.getEnemyState()][p.getAniIndex()], (int) p.getImagebox().x - xLevelOffset, (int) p.getImagebox().y, PUMA_WIDTH, PUMA_HEIGHT, null);
        }
    }

    private void loadEnemyImages() {
        pumaArr = new BufferedImage[5][9];
        BufferedImage temp = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Puma_Sprite);
        for (int j = 0; j < pumaArr.length; j++) {
            for (int i = 0; i < pumaArr[j].length; i++) {
                pumaArr[j][i] = temp.getSubimage(i * PUMA_WIDTH_DEFAULT, j * PUMA_HEIGHT_DEFAULT, PUMA_WIDTH_DEFAULT, PUMA_HEIGHT_DEFAULT);
            }
        }
    }
}