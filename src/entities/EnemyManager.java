package entities;

import gamestates.Playing;
import utilz.LoadPlayerSave;
import static utilz.Constants.EnemyConstants.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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

    public void update(int[][] lvlData,Player player) {
        for (Puma p: pumas) {
            if (p.isActive())
                p.update(lvlData,player);
        }
    }

    public void draw(Graphics g, int xLevelOffset) {
        drawPumas(g, xLevelOffset);
    }

    private void drawPumas(Graphics g, int xLevelOffset) {
        for (Puma p: pumas) {
            if (p.isActive()) {
                g.drawImage(pumaArr[p.getEnemyState()][p.getAniIndex()], (int) p.getImagebox().x - xLevelOffset + p.flipX(), (int) p.getImagebox().y, PUMA_WIDTH * p.flipW(), PUMA_HEIGHT, null);
                p.drawImagebox(g, xLevelOffset);
                p.drawAttackBox(g, xLevelOffset);
            }

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

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Puma p : pumas)
            if (p.isActive())
                if (attackBox.intersects(p.getImagebox())) {
                    p.hurt(10);
                    return;
                }
    }

    public void resetAllEnemies() {
        for (Puma p : pumas)
            p.resetEnemy();
    }
}
