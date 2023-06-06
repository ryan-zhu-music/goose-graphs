import java.awt.*;
import java.awt.event.*;

public class Line {
    Equation e;
    
    public Line(Equation e) {
        this.e = e;
    }

    private int transform(double x) {
        return (int) - e.evaluate((x - 500) / 25.0) + 300;
    }
    
    public void draw(Graphics2D g2) {
        g2.setStroke(new BasicStroke(2));
        for (int i = 0; i < 1000; i++) {
            g2.drawLine(i, transform(i), i + 1, transform(i + 1));
        }
    }
}
