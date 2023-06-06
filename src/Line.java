import java.awt.*;
import java.awt.event.*;

public class Line {
    Equation e;
    Vector v1;
    Vector v2;

    public Line(Vector v1, Vector v2, Equation e) {
        this.v1 = v1;
        this.v2 = v2;
        this.e = e;
    }

    public void draw(Graphics2D g2) {
        if (v1.getY() > 0 && v2.getY() > 0 && v1.getY() < 800 && v2.getY() < 800)
            g2.drawLine((int) v1.getX(), (int) v1.getY(), (int) v2.getX(), (int) v2.getY());
    }

    public String toString() {
        return v1.toString() + " " + v2.toString();
    }

}
