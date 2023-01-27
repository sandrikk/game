package ui;

import entities.Player;
import gamestates.Gamestate;
import utilz.LoadPlayerSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utilz.Constants.UI.ButtonsCharacter.*;

public class CharacterButton {
    private int xPos, yPos, index, rowIndex;
    private Gamestate state;

    private BufferedImage[] images;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;


    public CharacterButton(int xPos, int yPos, int index /* row */,  Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.state = state;
        this.index = index;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos, yPos, B_WIDTH, B_HEIGHT);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Character_Buttons);
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * B_WIDTH_DEFAULT,index /* row */ * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(images[rowIndex], xPos, yPos, B_WIDTH, B_HEIGHT, null);

    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public void update() {
        rowIndex = 0;
        if (mouseOver) {
            rowIndex = 1;
        }
        if (mousePressed) {
            rowIndex = 2;
        }

        if (mousePressed) {
            if (index == 0) {
                LoadPlayerSave.Player_Atlas1 = "georgi_sprite.png";
            } else if (index == 1) {
                LoadPlayerSave.Player_Atlas1 = "sandra_char_sprites.png";
            } else if (index == 2) {
                LoadPlayerSave.Player_Atlas1 = "miguel_sprite.png";

            } else {
            }
        }

    }


    public void applyGameState() {
        Gamestate.state = state;
    }

    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }
}