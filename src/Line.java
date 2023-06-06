import java.awt.*;
import java.awt.event.*;

public class Line {
    Equation e;
    Vector v1;
    Vector v2;
    Double A, B, C;

    public Line(Vector v1, Vector v2, Equation e) {
        this.v1 = v1;
        this.v2 = v2;
        this.e = e;
        A = (v2.getY() - v1.getY()) * 1.0 / (v2.getX() - v1.getX());
        B = -1.0;
        C = v1.getY() - A * v1.getX();
    }

    public void draw(Graphics2D g2) {
        if (v1.getY() > -500 && v2.getY() > -500 && v1.getY() < 1300 && v2.getY() < 1300)
            g2.drawLine((int) v1.getX(), (int) v1.getY(), (int) v2.getX(), (int) v2.getY());
    }

    public boolean collidesWith(Vector v) {
        // shortest distance from point v to line
        double d = (A * v.getX() + B * v.getY() + C) / Math.sqrt(A * A + B * B);
        return d > 0 && d < 10;
    }

    public String toString() {
        return v1.toString() + " " + v2.toString();
    }

}
