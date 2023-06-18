// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: About page

import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class About {
    public static BufferedImage aboutMenu;
    public static BufferedImage pinkGoose1;
    public static BufferedImage pinkGoose2;
    public static BufferedImage purpleGoose1;
    public static BufferedImage purpleGoose2;
    public static MenuButton exitButton;

    public About() {
        try {
            aboutMenu = ImageIO.read(new File("assets/aboutMenu.png"));
            pinkGoose1 = ImageIO.read(new File("assets/pinkGoose1.png"));
            pinkGoose2 = ImageIO.read(new File("assets/pinkGoose2.png"));
            purpleGoose1 = ImageIO.read(new File("assets/purpleGoose1.png"));
            purpleGoose2 = ImageIO.read(new File("assets/purpleGoose2.png"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }
    }

    public static void draw(Graphics2D g2) {
        g2.drawImage(aboutMenu, 0, 0, null);

        if (Menu.goose) {
            g2.drawImage(pinkGoose1, 100, 510, null);
            g2.drawImage(purpleGoose1, 270, 510, null);
        } else if (!Menu.goose) {
            g2.drawImage(pinkGoose2, 100, 510, null);
            g2.drawImage(purpleGoose2, 270, 510, null);
        }
    }
}
