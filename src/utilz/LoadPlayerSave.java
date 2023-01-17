package utilz;

import entities.Puma;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utilz.Constants.EnemyConstants.PUMA;

public class LoadPlayerSave {

    public static final String Player_Atlas = "characters8.png";
    public static final String Background_Atlas = "backgroundJungle.png";
    public static final String Level_Atlas = "sand_tiles.png";
    //public static final String Level_One_Data = "level_one_data.png";
    public static final String Level_One_Data = "level_longer3.png";
    public static final String Menu_Buttons = "button_atlas.png";
    public static final String Menu_Background = "background_menu.jpg";
    public static final String Puma_Sprite = "puma_sprite.png";

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

    public static ArrayList<Puma> getPumas() {
        BufferedImage img = GetSpriteAtlas(Level_One_Data);
        ArrayList<Puma> list = new ArrayList<>();

        for (int j = 0; j < img.getHeight(); j++) {
            for (int i = 0; i < img.getWidth(); i++) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == PUMA) {
                    list.add(new Puma(i * Game.tiles_size, j * Game.tiles_size));
                }
            }
        }
        return list;
    }

    public static int[][] GetLevelInfo() {
        BufferedImage img = GetSpriteAtlas(Level_One_Data);
        int[][] levelInfo = new int[img.getHeight()][img.getWidth()];

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
