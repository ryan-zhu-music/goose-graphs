import java.util.*;
import javax.swing.*;
import java.awt.*;

public class App extends JPanel implements Runnable {

    Equation e;
    LinkedList<Goose> geese = new LinkedList<Goose>();

    public App() {
        e = new Equation();
        addKeyListener(e);
        setFocusable(true);

        // e = new Equation("0.5x^2+2x+sin(x)-4");
        for (int i = 0; i < 1; i++) {
            geese.add(new Goose(500, 150, 5, -8));
        }

        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(1000 / 60);
            } catch (Exception e) {
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 1000, 600);
        g.setColor(Color.WHITE);
        // e.draw(g2);
        for (Goose goose : geese) {
            goose.move();
            goose.draw(g2);
        }

        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString(e.toString(), 10, 30);
    }

    public static void main(String[] args) throws Exception {
        JFrame f = new JFrame("App");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(1000, 600);
        f.add(new App());
        f.setVisible(true);
    }
}
