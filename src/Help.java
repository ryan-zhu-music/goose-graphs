// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: help page

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Help {
    public static BufferedImage helpMenu;
    public static BufferedImage goose3;
    public static BufferedImage goose4;

    public Help() {
        try {
            helpMenu = ImageIO.read(new File("assets/helpMenu.png"));
            goose3 = ImageIO.read(new File("assets/goose3.png"));
            goose4 = ImageIO.read(new File("assets/goose4.png"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }
    }

    public static void draw(Graphics2D g2) {
        g2.drawImage(helpMenu, 0, 0, null);

        if (Menu.goose) {
            g2.drawImage(goose3, 820, 150, null);
        } else if (!Menu.goose) {
            g2.drawImage(goose4, 820, 150, null);
        }
    }
}
