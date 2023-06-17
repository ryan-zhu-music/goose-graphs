import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class MenuButton implements MouseListener, MouseMotionListener {
    public BufferedImage img, img1;
    public int imgX, imgY, mouseX, mouseY, screenID, width, height;
    public boolean hovered = false;

    public MenuButton(String fileName, String fileName1, int imgX, int imgY, int screenID) {
        try {
            this.img = ImageIO.read(new File(fileName));
            this.img1 = ImageIO.read(new File(fileName1));
            this.imgX = imgX;
            this.imgY = imgY;
            this.screenID = screenID;
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

    public int getX() {
        return this.imgX;
    }

    public int getY() {
        return this.imgY;
    }

    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (mouseX >= imgX && mouseX <= (imgX + width) && mouseY >= imgY && mouseY <= (imgY + height)
                && (Menu.currentScreen == 0 || this.screenID == 0 && Menu.currentScreen != 0)) {
            if (this.screenID == 1) { // level select screen
                LevelButton[] normal = new LevelButton[9];
                LevelButton[] challenge = new LevelButton[6];
                normal = Arrays.copyOfRange(Menu.buttons, 0, 9);
                challenge = Arrays.copyOfRange(Menu.buttons, 9, 15);
                Arrays.sort(normal);
                Arrays.sort(challenge);
            } else if (this.screenID == 0) {
                Level.halt();
                LevelSelect.isDrawn = false;
            }
            Menu.currentScreen = this.screenID;
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
