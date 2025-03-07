// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: This class is used to create buttons for the menu screen that switch between screens when clicked.

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

public class MenuButton implements MouseListener, MouseMotionListener {
    public BufferedImage img, img1;
    public int imgX, imgY, mouseX, mouseY, screenID, parentScreen, width, height;
    public boolean hovered = false;

    public MenuButton(String fileName, String fileName1, int imgX, int imgY, int screenID, int parentScreen) {
        try {
            this.img = ImageIO.read(new File("assets/" + fileName));
            this.img1 = ImageIO.read(new File("assets/" + fileName1));
            this.imgX = imgX;
            this.imgY = imgY;
            this.screenID = screenID;
            this.parentScreen = parentScreen;
            this.width = img.getWidth();
            this.height = img.getHeight();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }
    }

    public void draw(Graphics2D g2) {
        if (hovered) {
            g2.drawImage(this.img1, this.imgX - 5, this.imgY - 5, null);
        } else {
            g2.drawImage(this.img, this.imgX, this.imgY, null);
        }
    }

    // getters
    public int getX() {
        return this.imgX;
    }

    public int getY() {
        return this.imgY;
    }

    // mouse events
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (mouseX >= imgX && mouseX <= (imgX + width) && mouseY >= imgY && mouseY <= (imgY + height)
                && (Menu.currentScreen == this.parentScreen || this.parentScreen == -1 && Menu.currentScreen != 0)) {
            if (this.screenID == 1) { // level select screen
                ArrayList<LevelButton> normal = new ArrayList<>();
                ArrayList<LevelButton> challenge = new ArrayList<>();
                for (LevelButton b : Menu.buttons) {
                    if (b.isChallenge()) {
                        challenge.add(b);
                    } else {
                        normal.add(b);
                    }
                }
                Collections.sort(normal);
                Collections.sort(challenge);
                for (int i = 0; i < 15; i++) {
                    if (i < 9) {
                        Menu.buttons.set(i, normal.get(i));
                    } else {
                        Menu.buttons.set(i, challenge.get(i - 9));
                    }
                }
            } else if (this.screenID == 0) {
                Level.halt();
                LevelSelect.isDrawn = false;
            }
            Menu.currentScreen = this.screenID;
            Level.currentLevel = -1;
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (mouseX >= this.imgX && mouseX <= (this.imgX + this.width) && mouseY >= this.imgY
                && mouseY <= (this.imgY + height)) {
            hovered = true;
        } else
            hovered = false;
    }
}
