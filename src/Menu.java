import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Menu extends JPanel implements MouseListener, Runnable {
    public static BufferedImage bg, logo, goose1, goose2;
    public static MenuButton playButton, aboutButton, helpButton, exitButton;
    public static boolean goose = false;
    public static int mouseX, mouseY, currentScreen = 0;
    public static Help helpScreen;
    public static About aboutScreen;

    public Menu() {
        setPreferredSize(new Dimension(1000, 800));

        playButton = new MenuButton("play.png", "play1.png", 244, 500, 1);
        aboutButton = new MenuButton("about.png", "about1.png", 460, 500, 2);
        helpButton = new MenuButton("help.png", "help1.png", 685, 500, 3);
        exitButton = new MenuButton("exit.png", "exit1.png", 15, 15, 0);
        addMouseListener(exitButton);
        addMouseMotionListener(exitButton);
        addMouseListener(playButton);
        addMouseListener(aboutButton);
        addMouseListener(helpButton);
        addMouseMotionListener(playButton);
        addMouseMotionListener(aboutButton);
        addMouseMotionListener(helpButton);

        helpScreen = new Help();
        aboutScreen = new About();

        try {
            logo = ImageIO.read(new File("logo.png"));
            bg = ImageIO.read(new File("bg.png"));
            goose1 = ImageIO.read(new File("goose1.png"));
            goose2 = ImageIO.read(new File("goose2.png"));
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
        super.paintComponent(g);
        if(currentScreen == 0) {
            g.drawImage(bg, 0, 0, null);
            g.drawImage(logo, 120, 40, null);
            playButton.draw(g);
            aboutButton.draw(g);
            helpButton.draw(g);

            if (!goose) {
                g.drawImage(goose1, 40, 500, null);
                goose = !goose;
            } else if (goose) {
                g.drawImage(goose2, 40, 500, null);
                goose = !goose;
            }
        }
        else if(currentScreen == 2) {
            About.draw(g);
            exitButton.draw(g);
            goose = !goose;
        }
        else if(currentScreen == 3) {
            Help.draw(g);
            exitButton.draw(g);
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
        while(true) {
            repaint();
            try {
                Thread.sleep(300);
            } 
            catch (Exception e) {
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
