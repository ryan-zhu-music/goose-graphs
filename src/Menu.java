import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Menu extends JPanel implements MouseListener, Runnable {
    public static BufferedImage mainMenu, logo, goose1, goose2, sprayGoose1, sprayGoose2;
    public static MenuButton playButton, aboutButton, helpButton, exitButton, returnButton;
    public static boolean goose = false;
    public static int mouseX, mouseY, currentScreen = 0;
    public static Help helpScreen;
    public static About aboutScreen;
    public static LevelSelect levelSelectScreen;
    public static Win winScreen;
    public static LevelButton[] buttons = new LevelButton[15];
    public static Level[] levels = new Level[15];
    public static ImageIcon icon;

    public Menu() {
        setPreferredSize(new Dimension(1000, 800));

        helpScreen = new Help();
        aboutScreen = new About();
        levelSelectScreen = new LevelSelect();
        winScreen = new Win();

        playButton = new MenuButton("play.png", "play1.png", 200, 500, 1);
        aboutButton = new MenuButton("about.png", "about1.png", 425, 500, 2);
        helpButton = new MenuButton("help.png", "help1.png", 645, 500, 3);
        exitButton = new MenuButton("exit.png", "exit1.png", 15, 15, 0);
        returnButton = new MenuButton("back.png", "back1.png", 300, 500, 1);
        addMouseListener(exitButton);
        addMouseMotionListener(exitButton);
        addMouseListener(returnButton);
        addMouseMotionListener(returnButton);
        addMouseListener(playButton);
        addMouseListener(aboutButton);
        addMouseListener(helpButton);
        addMouseListener(levelSelectScreen);
        addMouseListener(returnButton);
        addMouseMotionListener(playButton);
        addMouseMotionListener(aboutButton);
        addMouseMotionListener(helpButton);
        addMouseMotionListener(returnButton);

        for (int i = 0; i < 15; i++) {
            if (i < 9) {
                Vector v = new Vector((i % 3) * 325 + 32,
                        (i / 3) * 195 + 176);
                buttons[i] = new LevelButton(i, v, false);
            } else {
                Vector v = new Vector(((i - 9) % 3) * 325 + 32,
                        ((i - 9) / 3) * 195 + 250);
                buttons[i] = new LevelButton(i, v, true);
            }

            levels[i] = new Level(i, Constants.LEVEL_STARTS.get(i), Constants.LEVEL_BOWTIES.get(i), i % 5 + 1, this);
            addMouseListener(buttons[i]);
            addMouseMotionListener(buttons[i]);
        }

        try {
            icon = new ImageIcon("goose.png");
            logo = ImageIO.read(new File("logo.png"));
            mainMenu = ImageIO.read(new File("mainMenu.png"));
            goose1 = ImageIO.read(new File("goose1.png"));
            goose2 = ImageIO.read(new File("goose2.png"));
            sprayGoose1 = ImageIO.read(new File("sprayGoose.png"));
            sprayGoose2 = ImageIO.read(new File("sprayGoose1.png"));
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        }

        Thread thread = new Thread(this);
        thread.start();
        this.setFocusable(true);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        if (currentScreen == 0) {
            g.drawImage(mainMenu, 0, 0, null);
            g.drawImage(logo, 120, 40, null);
            playButton.draw(g2);
            aboutButton.draw(g2);
            helpButton.draw(g2);

            if (!goose) {
                g.drawImage(goose1, 30, 580, null);
                g.drawImage(sprayGoose1, 780, 375, null);
                goose = !goose;
            } else if (goose) {
                g.drawImage(goose2, 30, 580, null);
                g.drawImage(sprayGoose2, 780, 375, null);
                goose = !goose;
            }
        } else if (currentScreen == 1) {
            if (Level.currentLevel > -1) {
                if (Level.isRunning()) {
                    levels[Level.currentLevel].draw(g2);
                }
            } else {
                LevelSelect.draw(g2);
                LevelButton.draw(g2);
            }
            exitButton.draw(g2);
        } else if (currentScreen == 2) {
            About.draw(g2);
            exitButton.draw(g2);
            goose = !goose;
        } else if (currentScreen == 3) {
            Help.draw(g2);
            exitButton.draw(g2);
            goose = !goose;
        } else if (currentScreen == 4) {
            Win.draw(g2);
            returnButton.draw(g2);
        }
    }

    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("Goose Graphs");
        Menu panel = new Menu();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(1000, 800));
        f.setIconImage(icon.getImage());
        f.add(panel);
        f.pack();
        f.setVisible(true);
    }

    public void run() {
        while (true) {
            repaint();
            if (currentScreen == 1 && Level.currentLevel > -1) {
                levels[Level.currentLevel].update();
            }
            try {
                if (currentScreen == 1) {
                    Thread.sleep(20);
                } else {
                    Thread.sleep(400);
                }
            } catch (Exception e) {
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
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
