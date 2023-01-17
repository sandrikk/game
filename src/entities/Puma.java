package entities;
import main.Game;

import static utilz.Constants.EnemyConstants.*;

public class Puma extends Enemy {
    public Puma(float x, float y) {
        super(x, y, PUMA_WIDTH, PUMA_HEIGHT, PUMA);
        initImagebox(x,y,(int)(22* Game.scaling),(int)(19*Game.scaling));
    }
}
