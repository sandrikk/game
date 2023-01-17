package entities;
import static utilz.Constants.EnemyConstants.*;

public class Puma extends Enemy {
    public Puma(float x, float y) {
        super(x, y, PUMA_WIDTH, PUMA_HEIGHT, PUMA);
    }
}
