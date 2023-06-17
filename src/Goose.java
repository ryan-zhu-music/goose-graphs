import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
//import javax.swing.*;
import java.util.*;
import java.util.Queue;

public class Goose {
  private Vector pos;
  private Vector vel;
  private Vector initialPos;
  private boolean fired = false;
  private Queue<Vector> vels = new LinkedList<>();
  public static BufferedImage goose;

  public static LinkedList<Goose> geese = new LinkedList<>();

  public Goose(double x, double y, double vx, double vy) {
    this.pos = new Vector(x, y);
    this.vel = new Vector(vx, vy);
    this.initialPos = new Vector(x, y);
    geese.add(this);

    try {
      goose = ImageIO.read(new File("goose.png"));
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    } catch (IOException e) {
      System.out.println("Reading Error!");
    }
  }

  public static boolean fire() {
    for (Goose g : geese) {
      g.reset();
      g.setFired(true);
    }
    Bowtie.reset();
    return true;
  }

  public static void stop() {
    for (Goose g : geese) {
      g.setFired(false);
    }
  }

  public void reset() {
    this.fired = false;
    this.pos.set(initialPos);
    this.vel.set(new Vector(0, 1));
    this.vels.clear();
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
        fired = false;
        return;
      }
      if (vel.magnitude() < Constants.MAX_VELOCITY)
        vel.add(Constants.GRAVITY);
      int index = (int) pos.getX() / 10;
      boolean collision1 = checkCollision(index);
      boolean collision2 = checkCollision((int) pos.getX() % 10 < 5 && index > 0 ? index - 1 : index + 1);
      Line l1 = Level.getEquation().getSegments()[(int) pos.getX() / 10];
      Line l2 = Level.getEquation().getSegments()[(int) pos.getX() % 10 < 5 && index > 0 ? index - 1 : index + 1];
      if ((collision1 || collision2) && Level.getEquation().getEquation().length() > 0 && Equation.isDrawn) {
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
        Vector perp1 = new Vector(this.pos);
        perp1.sub(l1.closestPoint(pos));
        Vector perp2 = new Vector(this.pos);
        perp2.sub(l2.closestPoint(pos));
        perp1.add(perp2);
        perp1.normalize();
        perp1.multScalar(0.5);
        this.pos.add(perp1);
      } else {
        while (Math.abs(l1.shortestDistance(pos)) < 9) {
          Vector perp = new Vector(this.pos);
          perp.sub(l1.closestPoint(pos));
          perp.normalize();
          perp.multScalar(0.5);
          this.pos.add(perp);
        }
      }
    }
  }

  public boolean checkCollision(int segment) {
    if (outOfBounds() || segment < 0 || segment >= Level.getEquation().getSegments().length)
      return false;
    if (Level.getEquation().getEquation().length() > 0 && Equation.isDrawn) {
      Line l = Level.getEquation().getSegments()[segment];
      return l.collidesWith(pos, vel.magnitude());
    }
    return false;
  }

  public void checkBowties() {
    if (outOfBounds())
      return;
    for (Bowtie b : Level.getBowties()) {
      b.checkCollision(pos);
    }
  }

  private boolean outOfBounds() {
    return (pos.getX() <= 5 || pos.getX() >= 995 || pos.getY() <= 200 || pos.getY() >= 800) && fired;
  }

  public void draw(Graphics2D g2) {
    g2.drawImage(goose, (int) pos.getX() - 22, (int) pos.getY() - 30, null);
  }

  public static void drawAll(Graphics2D g2) {
    for (Goose g : geese) {
      g.draw(g2);
    }
  }

  public static void update() {
    for (Goose g : geese) {
      g.move();
      g.checkBowties();
    }
  }

}
