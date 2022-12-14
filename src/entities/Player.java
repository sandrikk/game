package entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Player extends Entity{
    private BufferedImage[][] animations;
    public Player(float x, float y) {
        super(x, y);
    }
    public void update() {

    }
    public void render() {

    }
    private void loadAnimations() {
        InputStream istream = getClass().getResourceAsStream("/characters8.png");
        try {
          BufferedImage  img = ImageIO.read(istream);
            animations = new BufferedImage[5][5];

            for (int j = 0; j < animations.length; j++) {
                for (int i = 0; i < animations[j].length; i++) {
                    animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
        } catch (IOException e) {
            e.printStackTrace();
        }

            }

        }
    }
}
