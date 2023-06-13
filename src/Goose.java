import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Goose {
  Vector pos;
  Vector vel;
  Vector prevVel;
  Vector initialPos;
  int variation;
  int axis;
  Equation e;
  static boolean fired = false;

  public Goose(double x, double y, double vx, double vy, Equation e) {
    this.pos = new Vector(x, y);
    this.vel = new Vector(vx, vy);
    this.prevVel = new Vector(vx, vy);
    this.initialPos = new Vector(x, y);
    this.e = e;
  }

  public static void fire() {
    fired = true;
  }

  public void move() {
    if (fired) {
      prevVel.set(vel);
      // axis = (int) (Math.random() * (3 - 1 + 1) + 1);
      // variation = (int) (Math.random() * (4 - 0 + 1));
      if (outOfBounds()) {
        System.out.println("out of bounds");
        fired = false;
        return;
      }
      if (vel.magnitude() < Constants.MAX_VELOCITY)
        vel.add(Constants.GRAVITY);
      int index = (int) pos.getX() / 10;
      boolean collision1 = checkCollision(index);
      boolean collision2 = checkCollision((int) pos.getX() % 10 < 5 && index > 0 ? index - 1 : index + 1);
      Line l1 = e.getSegments()[(int) pos.getX() / 10];
      Line l2 = e.getSegments()[(int) pos.getX() % 10 < 5 && index > 0 ? index - 1 : index + 1];
      if (!collision1 && !collision2) {
        this.pos.add(vel);
        while (Math.abs(l1.shortestDistance(pos)) < 9) {
          // System.out.println("moving back" + l.getSlope() + l.perpendicular());
          this.pos.sub(l1.perpendicular());
        }
      } else if (e.getEquation().length() > 0 && Equation.isDrawn) {
        if (collision1 && collision2) {
          Vector slope1 = l1.getSlope();
          Vector slope2 = l2.getSlope();
          Vector bounce1 = slope1.bounceAngle(this.vel);
          Vector bounce2 = slope2.bounceAngle(this.vel);
          bounce1.add(bounce2);
          bounce1.multScalar(0.5);
          this.vel = bounce1;
        } else if (collision1) {
          Vector slope = l1.getSlope();
          this.vel = slope.bounceAngle(this.vel);
        } else if (collision2) {
          Vector slope = l2.getSlope();
          this.vel = slope.bounceAngle(this.vel);
        }
        if (vel.magnitude() < 0.1 && Math.abs(Math.abs(vel.angle()) - Math.PI / 2) < 0.1) {
          System.out.println("stopped");
          fired = false;
        }
        this.pos.add(vel);
      }
    } else {
      this.pos = initialPos;
    }
  }

  public boolean checkCollision(int segment) {
    if (outOfBounds())
      return false;
    if (e.getEquation().length() > 0 && Equation.isDrawn) {
      Line l = e.getSegments()[segment];
      // System.out.println(pos + " " + l);
      return l.collidesWith(pos, vel.magnitude());
    }
    return false;
  }

  public void checkBowties() {
    if (outOfBounds())
      return;
    for (Bowtie b : Bowtie.bowties) {
      b.checkCollision(pos);
    }
  }

  private boolean outOfBounds() {
    return pos.getX() < 0 || pos.getX() > 1000 || pos.getY() < 200 || pos.getY() > 800;
  }

  public void draw(Graphics2D g2) {
    g2.fillOval((int) pos.getX() - 10, (int) pos.getY() - 10, 20, 20);
  }

}
