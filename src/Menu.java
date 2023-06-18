// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: Menu class that represents the menu screen, acts as the driver for the game

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.sound.sampled.*;

public class Menu extends JPanel implements MouseListener, MouseMotionListener, Runnable {
    public static BufferedImage mainMenu, logo, logo1, goose1, goose2, sprayGoose1, sprayGoose2;
    public static MenuButton playButton, aboutButton, helpButton, exitButton, returnButton;
    public static boolean goose = false, hovered = false;
    public static int mouseX, mouseY, currentScreen = 0;
    public static Help helpScreen;
    public static About aboutScreen;
    public static LevelSelect levelSelectScreen;
    public static Win winScreen;
    public static ArrayList<LevelButton> buttons = new ArrayList<>();
    public static Level[] levels = new Level[15];
    public static ImageIcon icon;
    public static Clip menuMusic, gameMusic, honk, win;
    public static AudioInputStream sound;

    public Menu() {
        setPreferredSize(new Dimension(1000, 800));

        // screens
        helpScreen = new Help();
        aboutScreen = new About();
        levelSelectScreen = new LevelSelect();
        winScreen = new Win();
        // menu buttons
        playButton = new MenuButton("play.png", "play1.png", 200, 500, 1, 0);
        aboutButton = new MenuButton("about.png", "about1.png", 425, 500, 2, 0);
        helpButton = new MenuButton("help.png", "help1.png", 645, 500, 3, 0);
        exitButton = new MenuButton("exit.png", "exit1.png", 15, 15, 0, -1);
        returnButton = new MenuButton("back.png", "back1.png", 380, 400, 1, 4);
        addMouseListener(this);
        addMouseMotionListener(this);
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

        // create level buttons
        for (int i = 0; i < 15; i++) {
            if (i < 9) {
                Vector v = new Vector((i % 3) * 325 + 32,
                        (i / 3) * 195 + 176);
                buttons.add(new LevelButton(i, v, false));
            } else {
                Vector v = new Vector(((i - 9) % 3) * 325 + 32,
                        ((i - 9) / 3) * 195 + 250);
                buttons.add(new LevelButton(i, v, true));
            }

            levels[i] = new Level(i, Constants.LEVEL_STARTS.get(i), Constants.LEVEL_BOWTIES.get(i), i / 3 + 1, this);
            addMouseListener(buttons.get(i));
            addMouseMotionListener(buttons.get(i));
        }

        try {
            icon = new ImageIcon("goose.png");
            logo = ImageIO.read(new File("logo.png"));
            logo1 = ImageIO.read(new File("logo1.png"));
            mainMenu = ImageIO.read(new File("mainMenu.png"));
            goose1 = ImageIO.read(new File("goose1.png"));
            goose2 = ImageIO.read(new File("goose2.png"));
            sprayGoose1 = ImageIO.read(new File("sprayGoose.png"));
            sprayGoose2 = ImageIO.read(new File("sprayGoose1.png"));

            sound = AudioSystem.getAudioInputStream(new File("menuMusic.wav"));
            menuMusic = AudioSystem.getClip();
            menuMusic.open(sound);
            sound = AudioSystem.getAudioInputStream(new File("gameMusic.wav"));
            gameMusic = AudioSystem.getClip();
            gameMusic.open(sound);
            sound = AudioSystem.getAudioInputStream(new File("honk.wav"));
            honk = AudioSystem.getClip();
            honk.open(sound);
            sound = AudioSystem.getAudioInputStream(new File("win.wav"));
            win = AudioSystem.getClip();
            win.open(sound);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("Reading Error!");
        } catch (UnsupportedAudioFileException e) {
            System.out.println("The specified audio file is not supported!");
        } catch (LineUnavailableException e) {
            System.out.println("Error in audio line for playing back!");
        }

        Thread thread = new Thread(this);
        thread.start();
        this.setFocusable(true);
    }

    // draw stuff
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        if (currentScreen == 0) {
            win.stop();
            gameMusic.stop();
            menuMusic.loop(Clip.LOOP_CONTINUOUSLY);
            g.drawImage(mainMenu, 0, 0, null);
            if (!hovered)
                g.drawImage(logo, 120, 40, null);
            else
                g.drawImage(logo1, 115, 35, null);
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
                    menuMusic.stop();
                    gameMusic.loop(Clip.LOOP_CONTINUOUSLY);
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
            gameMusic.stop();
            win.loop(Clip.LOOP_CONTINUOUSLY);
            Win.draw(g2);
            goose = !goose;
            returnButton.draw(g2);
        }
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
                } else if (currentScreen == 4) {
                    Thread.sleep(365);
                } else {
                    Thread.sleep(315);
                }
            } catch (Exception e) {
            }
        }

    }

    // mouse events
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (currentScreen == 0 && mouseX >= 120 && mouseX <= 870 && mouseY >= 40 && mouseY <= 428) {
            System.out.println("honked");
            honk.setFramePosition(0);
            honk.start();
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

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        if (currentScreen == 0 && mouseX >= 120 && mouseX <= 870 && mouseY >= 40 && mouseY <= 428) {
            hovered = true;
        } else
            hovered = false;
    }

    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("Goose Graphs");
        Menu panel = new Menu();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setPreferredSize(new Dimension(1000, 800));
        f.setResizable(false);
        f.setIconImage(icon.getImage());
        f.add(panel);
        f.pack();
        f.setVisible(true);
    }
}
