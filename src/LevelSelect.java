// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: The screen for selecting which level to play

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class LevelSelect implements MouseListener {
    private static BufferedImage normalBg;
    private static BufferedImage challengeBg;
    public static int currentScreen = 1;
    private static int mouseX, mouseY;
    public static boolean isDrawn = false;

    public LevelSelect() {
        try {
            normalBg = ImageIO.read(new File("assets/levelMenuNormal.png"));
            challengeBg = ImageIO.read(new File("assets/levelMenuChallenge.png"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }
    }

    public static void draw(Graphics g) {
        isDrawn = true;
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
            } else if (mouseX >= 507 && mouseX <= 713 && mouseY >= 128 && mouseY <= 150) {
                currentScreen = 2;
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
