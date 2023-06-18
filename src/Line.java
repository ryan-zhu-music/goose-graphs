// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: A line class used to detect collisions between the goose and the graph

import java.awt.*;

public class Line {
    public static Equation e;
    private Vector v1;
    private Vector v2;
    private double A, B, C;

    public Line(Vector v1, Vector v2) {
        this.v1 = v1;
        this.v2 = v2;
        A = (v2.getY() - v1.getY()) * 1.0 / (v2.getX() - v1.getX());
        B = -1.0;
        C = v1.getY() - A * v1.getX();
    }

    public Vector getPoint1() {
        return v1;
    }

    public Vector getPoint2() {
        return v2;
    }

    public static void setEquation(Equation eq) {
        e = eq;
    }

    public void draw(Graphics2D g2) {
        double y1 = v1.getY();
        double y2 = v2.getY();
        if (y1 > -5000 && y1 < 5000 && y2 > -5000 && y2 < 5000 && !Equation.isUndefined(y1)
                && !Equation.isUndefined(y2)) {
            g2.drawLine((int) v1.getX(), (int) y1, (int) v2.getX(), (int) y2);
        }
    }

    // checks if the goose has collided with the line
    // @param: v - the position of the goose
    // @param: radius - the radius of the goose that will register a collision
    // @return: true if the goose has collided with the line, false otherwise
    public boolean collidesWith(Vector v, double radius) {
        double d = shortestDistance(v);
        // check that the point is within the bounds of the line
        Vector p = closestPoint(v);
        if (d > -10 - radius && d < 10 + radius && !Double.isNaN(p.getX()) && !Double.isNaN(p.getY())) {
            if (p.getX() < Math.min(v1.getX(), v2.getX()) - 2 || p.getX() > Math.max(v1.getX(), v2.getX()) + 2) {
                return false;
            }
            if (p.getY() < Math.min(v1.getY(), v2.getY()) - 2 || p.getY() > Math.max(v1.getY(), v2.getY()) + 2)
                return false;
            return true;
        }
        return false;
    }

    // calculates the shortest (perpendicular) distance from the line to a point
    // @param: v - the point
    // @return: the shortest distance from the line to the point
    public double shortestDistance(Vector v) {
        double d = (A * v.getX() + B * v.getY() + C) / Math.sqrt(A * A + B * B);
        return d;
    }

    // calculates the closest point on the line to a point
    // @param: v - the point
    // @return: the closest point on the line to the point
    public Vector closestPoint(Vector v) {
        double x = (A * v.getY() + v.getX() - A * C) / (A * A + 1);
        double y = (v.getX() - x) / A + v.getY();
        if (A == 0) {
            x = v.getX();
            y = v1.getY();
        }
        return new Vector(x, y);
    }

    // returns the perpendicular vector of the line
    public Vector perpendicular() {
        Vector v = new Vector(v1.getY() - v2.getY(), v2.getX() - v1.getX());
        v.normalize();
        v.multScalar(0.5);
        return v;
    }

    // returns the line as a single vector
    public Vector getSlope() {
        return new Vector(v2.getX() - v1.getX(), v2.getY() - v1.getY());
    }

    public String toString() {
        return v1.toString() + " " + v2.toString();
    }

}
