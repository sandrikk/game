package utilz;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadPlayerSave {

    public static final String Player_Atlas = "characters8.png";
    public static final String Level_Atlas = "characters8.png";

    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream istream = LoadPlayerSave.class.getResourceAsStream("/" + fileName);
        try {
            img = ImageIO.read(istream);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                istream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return img;
    }
}
