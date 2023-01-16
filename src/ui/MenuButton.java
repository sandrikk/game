package ui;

import gamestates.Gamestate;
import main.Game;
import utilz.LoadPlayerSave;
import static utilz.Constants.UI.Buttons.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton {
    private int xPos, yPos, rowIndex, index;
    private int xOffsetCenter = B_WIDTH / 2;
    private Gamestate state;
    private BufferedImage[] images;
    private boolean mouseOver, mousePressed;
    private Rectangle bounds;
    public MenuButton(int xPos, int yPos, int rowIndex, Gamestate state) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.state = state;
        this.rowIndex = rowIndex;
        loadImages();
        initBounds();
    }

    private void initBounds() {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    private void loadImages() {
        images = new BufferedImage[3];
        BufferedImage temp = LoadPlayerSave.GetSpriteAtlas(LoadPlayerSave.Menu_Buttons);
        for (int i = 0; i < images.length; i++) {
            images[i] = temp.getSubimage(i * B_WIDTH_DEFAULT,rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g) {
        g.drawImage(images[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);

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
        index = 0;
        if (mouseOver) {
            index = 1;
        }
        if (mousePressed) {
            index = 2;
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


