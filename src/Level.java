import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Level extends JPanel implements Runnable {

    Equation e;
    Button[] buttons = new Button[8];
    static int level = 1;
    static final int GEESE_COUNT = 5;

    int[] x = { 137, 300, 463 };

    public Level(Vector gooseStart, Vector[] bowtiePos) {
        e = new Equation();
        addKeyListener(e);
        for (int i = 0; i < 6; i++) {
            buttons[i] = new Button(x[i % 3], 50 * (i / 3 + 1) + 43, 144, 36, Constants.functions[i], e, null);
            addMouseListener(buttons[i]);
        }
        buttons[6] = new Button(625, 93, 144, 36, "graph", e, (x) -> e.setEquation());
        buttons[6].setColor(Constants.COLORS.get("lime"));
        buttons[7] = new Button(625, 143, 144, 36, "go(ose)", e, (x) -> Goose.fire());
        buttons[7].setColor(Constants.COLORS.get("lime"));
        addMouseListener(buttons[6]);
        addMouseListener(buttons[7]);
        setFocusable(true);
        for (int i = 0; i < GEESE_COUNT; i++) {
            new Goose(gooseStart.getX(), gooseStart.getY(), 0, 1, e);
        }
        for (Vector v : bowtiePos) {
            new Bowtie((int) v.getX(), (int) v.getY());
        }

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            repaint();
            Goose.run();
            try {
                Thread.sleep(1000 / 60);
            } catch (Exception e) {
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        // bg
        g2.setColor(Constants.COLORS.get("ivory"));
        g2.fillRect(0, 0, 1000, 800);
        // gridlines
        g2.setColor(Color.BLACK);
        for (int i = 0; i < 1000; i += 50) {
            g2.drawLine(i, 0, i, 800);
        }
        for (int i = 0; i < 800; i += 50) {
            g2.drawLine(0, i, 1000, i);
        }
        // axes
        g2.setStroke(new BasicStroke(3));
        g2.drawLine(500, 0, 500, 800);
        g2.drawLine(0, 450, 1000, 450);
        g2.setColor(Color.RED);
        // equation
        e.draw(g2);
        // menu
        g2.setColor(Constants.COLORS.get("pink"));
        g2.fillRect(0, 0, 1000, 200);
        // level number
        g2.setColor(Constants.COLORS.get("maroon"));
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("" + level, 12, 23);
        // equation
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString("y=" + e.toString(), 137, 19);
        // buttons
        for (Button button : buttons) {
            button.draw(g2);
        }
        // geese
        Goose.drawAll(g2);
        // bowties
        for (Bowtie bowtie : Bowtie.bowties) {
            bowtie.draw(g2);
        }

    }

    // public static void main(String[] args) throws Exception {
    // JFrame f = new JFrame("App");
    // f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // f.setSize(1000, 800);
    // f.add(new Level());
    // f.setVisible(true);
    // }
}
