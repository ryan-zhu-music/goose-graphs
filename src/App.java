import java.util.*;
import javax.swing.*;
import java.awt.*;

public class App extends JPanel implements Runnable {

    Equation e;
    Goose[] geese = new Goose[1];
    Button[] buttons = new Button[7];
    static int level = 1;

    int[] x = { 137, 300, 452 };

    public App() {
        e = new Equation();
        addKeyListener(e);
        for (int i = 0; i < 6; i++) {
            buttons[i] = new Button(x[i % 3], 50 * (i / 3 + 1) + 43, 144, 36, Constants.functions[i], e, false);
            addMouseListener(buttons[i]);
        }
        buttons[6] = new Button(625, 93, 144, 36, "go(ose)", e, true);
        buttons[6].setColor(Constants.COLORS.get("lime"));
        addMouseListener(buttons[6]);
        setFocusable(true);
        // e = new Equation("0.5x^2+2x+sin(x)-4");
        for (int i = 0; i < geese.length; i++) {
            geese[i] = new Goose(500, 250, 0, 1, e);
        }
        for (int i = 0; i < 3; i++) {
            new Bowtie(300 * i + 250, 500);
        }

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            repaint();
            for (Goose goose : geese) {
                goose.move();
                goose.checkBowties();
            }
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
        for (Goose goose : geese) {
            goose.draw(g2);
        }
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
        for (Goose goose : geese) {
            goose.draw(g2);
        }
        // bowties
        for (Bowtie bowtie : Bowtie.bowties) {
            bowtie.draw(g2);
        }

    }

    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("App");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000, 800);
        f.add(new App());
        f.setVisible(true);
    }
}
