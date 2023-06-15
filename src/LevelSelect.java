import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelSelect implements MouseListener {
    public static BufferedImage normalBg;
    public static BufferedImage challengeBg;
    public static int currentScreen;

    public static ArrayList<Level> levels = new ArrayList<>();
    // when displaying levels, sort by completion status (put completed at the end,
    // sort by difficulty)

    public LevelSelect() {

    }

    public void mouseClicked(MouseEvent e) {
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
