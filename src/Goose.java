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
  Queue<Vector> vels = new LinkedList<>();

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
      // save most recent velocities
      vels.offer(new Vector(vel.getX(), vel.getY()));
      if (vels.size() > 10) {
        vels.poll();
      }
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
          if (Math.abs(l2.shortestDistance(pos)) < 9) {
            this.pos.sub(l2.perpendicular());
          }
        }
      } else if (e.getEquation().length() > 0 && Equation.isDrawn) {
        vel.multScalar(Constants.FRICTION);
        Vector bounce;
        if (collision1 && collision2) {
          Vector slope1 = l1.getSlope();
          Vector slope2 = l2.getSlope();
          bounce = slope1.bounceAngle(this.vel);
          bounce.add(slope2.bounceAngle(this.vel));
          bounce.multScalar(0.5);
          this.vel.set(bounce);
          if (Math.abs(vel.angle() - Math.PI / 2) < 0.1) { // ball gets stuck
            this.vel.normalize();
            this.vel.multScalar(0.1);
          }
        } else {
          Vector slope;
          if (collision1) {
            slope = l1.getSlope();
          } else {
            slope = l2.getSlope();
          }
          bounce = slope.bounceAngle(this.vel);
          this.vel.set(bounce);
        }
        if (Math.abs(bounce.angle() + Math.PI / 2) < 0.05 && bounce.magnitude() < 0.5) {
          System.out.println("stopped");
          fired = false;
        } else {
          this.pos.add(vel);
        }
      }
    } else {
      this.pos = initialPos;
    }
  }

  public boolean checkCollision(int segment) {
    if (outOfBounds() || segment < 0 || segment >= e.getSegments().length)
      return false;
    if (e.getEquation().length() > 0 && Equation.isDrawn) {
      Line l = e.getSegments()[segment];
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
    return pos.getX() <= 5 || pos.getX() >= 995 || pos.getY() <= 200 || pos.getY() >= 800;
  }

  public void draw(Graphics2D g2) {
    g2.fillOval((int) pos.getX() - 10, (int) pos.getY() - 10, 20, 20);
  }

}
