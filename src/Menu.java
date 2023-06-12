import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Menu extends JPanel implements Runnable {
    public static BufferedImage bg;
    public static BufferedImage logo;
    public static BufferedImage goose1;
    public static BufferedImage goose2;
    public static MenuButton playButton;
    public static MenuButton levelButton;
    public static MenuButton helpButton;
    public static boolean goose = false;

    public Menu() {
        JPanel menu = new JPanel();
        menu.setPreferredSize(new Dimension(1000, 800));

        playButton = new MenuButton("play.png", "play1.png", 244, 500);
        levelButton = new MenuButton("play.png", "play1.png", 460, 500);
        helpButton = new MenuButton("help.png", "help1.png", 685, 500);
        addMouseListener(playButton);
        addMouseListener(levelButton);
        addMouseListener(helpButton);
        addMouseMotionListener(playButton);
        addMouseMotionListener(levelButton);
        addMouseMotionListener(helpButton);

        try {
            logo = ImageIO.read(new File("logo.png"));
            bg = ImageIO.read(new File("bg.png"));
            goose1 = ImageIO.read(new File("goose1.png"));
            goose2 = ImageIO.read(new File("goose2.png"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println(":(");
        }

        Thread thread = new Thread(this);
        thread.start();
        this.setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
        g.drawImage(logo, 120, 40, null);
        playButton.draw(g);
        levelButton.draw(g);
        helpButton.draw(g);

        if (!goose) {
            g.drawImage(goose1, 40, 500, null);
            goose = !goose;
        } else if (goose) {
            g.drawImage(goose2, 40, 500, null);
            goose = !goose;
        }
    }

    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("Menu Test");
        Menu panel = new Menu();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(1000, 800));
        f.add(panel);
        f.pack();
        f.setVisible(true);
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(300);
            } catch (Exception e) {
            }
        }
    }
}
