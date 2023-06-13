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
        double y1 = v1.getY();
        double y2 = v2.getY();
        if (y1 > -5000 && y1 < 5000 && y2 > -5000 && y2 < 5000 && !Equation.isUndefined(y1)
                && !Equation.isUndefined(y2)) {
            g2.drawLine((int) v1.getX(), (int) y1, (int) v2.getX(), (int) y2);
        }
    }

    public boolean collidesWith(Vector v, double radius) {
        // shortest distance from point v to line
        double d = shortestDistance(v);
        // check that the point is within the bounds of the line
        double x = (A * v.getY() + v.getX() - A * C) / (A * A + 1);
        double y = (v.getX() - x) / A + v.getY();
        if (A == 0) {
            x = v.getX();
            y = v1.getY();
        }
        if (d > -10 - radius && d < 10 + radius && !Double.isNaN(x) && !Double.isNaN(y)) {
            if (x < Math.min(v1.getX(), v2.getX()) || x > Math.max(v1.getX(), v2.getX())) {
                return false;
            }
            if (y < Math.min(v1.getY(), v2.getY()) || y > Math.max(v1.getY(), v2.getY()))
                return false;
            return true;
        }
        return false;
    }

    public double shortestDistance(Vector v) {
        double d = (A * v.getX() + B * v.getY() + C) / Math.sqrt(A * A + B * B);
        return d;
    }

    public Vector perpendicular() {
        Vector v = new Vector(v1.getY() - v2.getY(), v2.getX() - v1.getX());
        v.normalize();
        v.multScalar(0.5);
        return v;
    }

    public Vector getSlope() {
        return new Vector(v2.getX() - v1.getX(), v2.getY() - v1.getY());
    }

    public String toString() {
        return v1.toString() + " " + v2.toString();
    }

}
