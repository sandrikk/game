package utilz;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadPlayerSave {

    public static final String Player_Atlas = "characters8.png";
    public static final String Background_Atlas = "backgroundJungle.png";
    public static final String Level_Atlas = "sand_tiles2.png";
    public static final String Level_One_Data = "level_one_data.png";
    public static final String Menu_Buttons = "button_atlas.png";
    public static final String Menu_Background = "background_menu.jpg";

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

    public static int[][] GetLevelInfo() {
        int[][] levelInfo = new int[Game.tiles_in_height][Game.tiles_in_width];
        BufferedImage img = GetSpriteAtlas(Level_One_Data);

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i,j));
                int value = color.getRed();
                if (value >= 48) {
                    value = 0;
                }
                levelInfo[j][i] = value;
            }
        }
        return levelInfo;
    }

}
