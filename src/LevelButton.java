import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class LevelButton implements MouseListener, MouseMotionListener, Comparable<LevelButton> {
    public BufferedImage img, img1;
    public int imgX, imgY, mouseX, mouseY, width, height, levelID;
    public boolean hovered = false;

    public LevelButton(String fileName, String fileName1, int imgX, int imgY, int levelID) {
        try {
            this.img = ImageIO.read(new File(fileName));
            this.img1 = ImageIO.read(new File(fileName1));
            this.imgX = imgX;
            this.imgY = imgY;
            this.imgY = imgY;
            this.levelID = levelID;
            this.width = img.getWidth();
            this.height = img.getHeight();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }
    }

    public int compareTo(LevelButton other) {
        if (Menu.levels[this.levelID].isCompleted() && !Menu.levels[other.levelID].isCompleted()) {
            return 1;
        } else if (!Menu.levels[this.levelID].isCompleted() && Menu.levels[other.levelID].isCompleted()) {
            return -1;
        } else {
            return Menu.levels[this.levelID].getDifficulty() - Menu.levels[other.levelID].getDifficulty();
        }
    }

    public void draw(Graphics2D g2) {
        if (hovered) {
            g2.drawImage(this.img1, this.imgX - 5, this.imgY - 5, null);
        } else {
            g2.drawImage(this.img, this.imgX, this.imgY, null);
        }
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (mouseX >= this.imgX && mouseX <= (this.imgX + this.width) && mouseY >= this.imgY
                && mouseY <= (this.imgY + height)) {
            hovered = true;
        } else {
            hovered = false;
        }
    }

    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (mouseX >= imgX && mouseX <= (imgX + width) && mouseY >= imgY && mouseY <= (imgY + height)) {
            System.out.println(levelID);
            Menu.level = this.levelID;
        }
    }

    public void mousePressed(MouseEvent e) {

    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }
}
