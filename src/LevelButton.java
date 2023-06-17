import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.util.ArrayList;

public class LevelButton implements MouseListener, MouseMotionListener, Comparable<LevelButton> {
    public BufferedImage img, img1, img2;
    public int mouseX, mouseY, width, height, levelID;
    public Vector pos = new Vector(0, 0);
    public boolean hovered = false;
    private boolean challenge;
    public boolean isDrawn = false;
    public static ArrayList<LevelButton> buttons = new ArrayList<>();

    public LevelButton(int levelID, Vector pos, boolean challenge) {
        try {
            this.img = ImageIO.read(new File(levelID + "level.png"));
            this.img1 = ImageIO.read(new File(levelID + "level1.png"));
            this.img2 = ImageIO.read(new File(1 + "win.png"));
            this.levelID = levelID;
            this.pos.set(pos);
            this.width = img.getWidth();
            this.height = img.getHeight();
            this.challenge = challenge;
            buttons.add(this);
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

    public void setPos(Vector pos) {
        this.pos = new Vector(pos);
    }

    public static void draw(Graphics2D g2) {
        if (LevelSelect.currentScreen == 1) {
            for (int i = 0; i < 9; i++) {
                LevelButton b = buttons.get(i);
                int x = (i % 3) * 325 + 32;
                int y = (i / 3) * 195 + 176;
                b.setPos(new Vector(x, y));
                if (Menu.levels[i].isCompleted()) {
                    g2.drawImage(b.img2, x - 5, y - 5, null);
                }
                if (b.hovered) {
                    g2.drawImage(b.img1, x - 5, y - 5, null);
                } else {
                    g2.drawImage(b.img, x, y, null);
                }
                b.isDrawn = true;
            }
        } else {
            for (int i = 9; i < 15; i++) {
                LevelButton b = buttons.get(i);
                int x = ((i - 9) % 3) * 325 + 32;
                int y = ((i - 9) / 3) * 195 + 176;
                b.setPos(new Vector(x, y));
                if (b.hovered) {
                    g2.drawImage(b.img1, x - 5, y - 5, null);
                } else {
                    g2.drawImage(b.img, x, y, null);
                }
                b.isDrawn = true;
            }
        }
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (mouseX >= this.pos.getX() && mouseX <= (this.pos.getX() + this.width) && mouseY >= this.pos.getY()
                && mouseY <= (this.pos.getY() + height)) {
            hovered = true;
        } else {
            hovered = false;
        }
    }

    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        if (mouseX >= this.pos.getX() && mouseX <= (this.pos.getX() + this.width) && mouseY >= this.pos.getY()
                && mouseY <= (this.pos.getY() + height)
                && Menu.currentScreen == 1 && (challenge ^ LevelSelect.currentScreen == 1) && !Level.isRunning()
                && LevelSelect.isDrawn) {
            Menu.levels[this.levelID].init();
            LevelSelect.isDrawn = false;
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
