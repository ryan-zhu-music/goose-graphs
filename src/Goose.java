import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Goose {
  private Vector pos;
  private Vector vel;
  private Vector initialPos;
  private Equation e;
  private boolean fired = false;
  private Queue<Vector> vels = new LinkedList<>();

  public static LinkedList<Goose> geese = new LinkedList<>();

  public Goose(double x, double y, double vx, double vy, Equation e) {
    this.pos = new Vector(x, y);
    this.vel = new Vector(vx, vy);
    this.initialPos = new Vector(x, y);
    this.e = e;
    geese.add(this);
  }

  public static boolean fire() {
    System.out.println("fire");
    for (Goose g : geese) {
      g.setFired(true);
    }
    return true;
  }

  public void setFired(boolean fired) {
    this.fired = fired;
  }

  public void move() {
    if (fired) {
      this.pos.add(vel);
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
      if ((collision1 || collision2) && e.getEquation().length() > 0 && Equation.isDrawn) {
        vel.multScalar(Constants.FRICTION);
        Vector bounce;
        if (collision1 && collision2) {
          Line l3 = new Line(l1.getPoint1(), l2.getPoint2());
          if ((int) pos.getX() % 10 < 5 && index > 0) {
            l3 = new Line(l2.getPoint1(), l1.getPoint2());
          }
          Vector slope = l3.getSlope();
          bounce = slope.bounceAngle(this.vel);
          this.vel.set(bounce);
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
        // check if ball is stopped
        if (Math.abs(bounce.angle() + Math.PI / 2) < 0.05 && bounce.magnitude() < 0.5) {
          double avgVel = 0;
          for (Vector v : vels) {
            avgVel += v.magnitude();
          }
          avgVel /= vels.size();
          if (avgVel < 0.6) {
            System.out.println("stopped");
            fired = false;
          }
        } else {
          // randomness
          this.vel.add(new Vector(Math.random() * 0.1 - 0.05, Math.random() * 0.1 - 0.05));
        }
      }
      // check if goose is too close to line
      if (Math.abs(l1.shortestDistance(pos)) < 9 && Math.abs(l2.shortestDistance(pos)) < 9) {
        Vector perp = new Vector(this.pos);
        perp.sub(l1.closestPoint(pos));
        perp.sub(l2.closestPoint(pos));
        perp.normalize();
        perp.multScalar(0.5);
        this.pos.add(perp);
      } else {
        while (Math.abs(l1.shortestDistance(pos)) < 9) {
          Vector perp = new Vector(this.pos);
          perp.sub(l1.closestPoint(pos));
          perp.normalize();
          perp.multScalar(0.5);
          this.pos.add(perp);
          while (Math.abs(l2.shortestDistance(pos)) < 9) {
            perp = new Vector(this.pos);
            perp.sub(l2.closestPoint(pos));
            perp.normalize();
            perp.multScalar(0.5);
            this.pos.add(perp);
          }
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

  public static void drawAll(Graphics2D g2) {
    for (Goose g : geese) {
      g.draw(g2);
    }
  }

  public static void run() {
    for (Goose g : geese) {
      g.move();
      g.checkBowties();
    }
  }

}
