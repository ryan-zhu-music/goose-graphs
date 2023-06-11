import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Goose {
  Vector pos;
  Vector vel;
  Vector initialPos;
  int variation;
  int axis;
  Equation e;
  static boolean fired = false;

  public Goose(double x, double y, double vx, double vy, Equation e) {
    this.pos = new Vector(x, y);
    this.vel = new Vector(vx, vy);
    this.initialPos = new Vector(x, y);
    this.e = e;
  }

  public static void fire() {
    fired = true;
  }

  public void move() {
    if (fired) {
      // axis = (int) (Math.random() * (3 - 1 + 1) + 1);
      // variation = (int) (Math.random() * (4 - 0 + 1));
      if (outOfBounds()) {
        System.out.println("out of bounds");
        fired = false;
        return;
      }
      if (vel.magnitude() < Constants.MAX_VELOCITY)
        vel.add(Constants.GRAVITY);
      if (!checkCollision()) {
        this.pos.add(vel);
        Line l = e.getSegments()[(int) pos.getX() / 10];
        while (Math.abs(l.shortestDistance(pos)) < 9) {
          // System.out.println("moving back" + l.getSlope() + l.perpendicular());
          this.pos.sub(l.perpendicular());
        }
      } else if (e.getEquation().length() > 0 && Equation.isDrawn) {
        Line l = e.getSegments()[(int) pos.getX() / 10];
        Vector slope = l.getSlope();
        this.vel = slope.bounceAngle(this.vel);
        // System.out.println(this.vel + " " + this.vel.angle() + " " +
        // this.vel.magnitude());
        if (vel.magnitude() < 0.08 && Math.abs(vel.angle()) < Math.PI / 2 + 0.1
            && Math.abs(vel.angle()) > Math.PI / 2 - 0.1) {
          System.out.println("stopped");
          fired = false;
        }
        this.pos.add(vel);
      }
    } else {
      this.pos = initialPos;
    }
  }

  public boolean checkCollision() {
    if (outOfBounds())
      return false;
    if (e.getEquation().length() > 0 && Equation.isDrawn) {
      Line l = e.getSegments()[(int) pos.getX() / 10];
      // System.out.println(pos + " " + l);
      return l.collidesWith(pos, vel.magnitude());
    }
    return false;
  }

  private boolean outOfBounds() {
    return pos.getX() < 0 || pos.getX() > 1000 || pos.getY() < 200 || pos.getY() > 800;
  }

  public void draw(Graphics2D g2) {
    g2.fillOval((int) pos.getX() - 10, (int) pos.getY() - 10, 20, 20);
  }

}
