import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LevelSelect implements MouseListener {
    public static BufferedImage normalBg;
    public static BufferedImage challengeBg;
    public static int currentScreen = 1, mouseX, mouseY;

    // when displaying levels, sort by completion status (put completed at the end,
    // sort by difficulty)

    public LevelSelect() {
        try {
            normalBg = ImageIO.read(new File("levelMenuNormal.png"));
            challengeBg = ImageIO.read(new File("levelMenuChallenge.png"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }
    }

    public static void draw(Graphics g) {
        if (currentScreen == 1) {
            g.drawImage(normalBg, 0, 0, null);
        } else if (currentScreen == 2) {
            g.drawImage(challengeBg, 0, 0, null);
        }
    }

    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (Menu.currentScreen == 1) {
            if (mouseX >= 304 && mouseX <= 447 && mouseY >= 128 && mouseY <= 150) {
                currentScreen = 1;
                System.out.println("clicked1");
            } else if (mouseX >= 507 && mouseX <= 713 && mouseY >= 128 && mouseY <= 150) {
                currentScreen = 2;
                System.out.println("clicked1");
            }
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
