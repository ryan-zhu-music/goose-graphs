// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: Goose class that represents the goose that is fired and bounces off the graph

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.*;
import java.util.Queue;

public class Goose {
  private Vector pos;
  private Vector vel;
  private Vector initialPos;
  private boolean fired = false;
  private Queue<Vector> vels = new LinkedList<>();
  private static BufferedImage goose;
  public static LinkedList<Goose> geese = new LinkedList<>();

  public Goose(double x, double y, double vx, double vy) {
    this.pos = new Vector(x, y);
    this.vel = new Vector(vx, vy);
    this.initialPos = new Vector(x, y);
    geese.add(this);

    try {
      goose = ImageIO.read(new File("assets/goose.png"));
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    } catch (IOException e) {
      System.out.println("Reading Error!");
    }
  }

  // sets all geese in free fall
  // @return: true if goose was fired
  public static boolean fire() {
    if (Level.getEquation().getEquation().length() == 0) {
      return false;
    }
    for (Goose g : geese) {
      g.reset();
      g.setFired(true);
    }
    Level.startTimer();
    Bowtie.reset();
    return true;
  }

  // stops all geese from moving
  public static void stop() {
    for (Goose g : geese) {
      g.setFired(false);
    }
  }

  // resets goose back to their starting position
  public void reset() {
    this.fired = false;
    this.pos.set(initialPos);
    this.vel.set(new Vector(0, 1));
    this.vels.clear();
  }

  public void setFired(boolean fired) {
    this.fired = fired;
  }

  // moves a goose each frame according to natural simulation
  public void move() {
    if (fired) {
      this.pos.add(vel);
      // save most recent velocities
      vels.offer(new Vector(vel.getX(), vel.getY()));
      if (vels.size() > 10) {
        vels.poll();
      }
      if (outOfBounds()) {
        this.fired = false;
        return;
      }
      if (vel.magnitude() < Constants.MAX_VELOCITY)
        vel.add(Constants.GRAVITY);
      // check for collisions
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

  // checks if a goose collides with a line segment
  // @param: segment - the index of the line segment to check
  // @return: true if the goose collides with the line segment
  public boolean checkCollision(int segment) {
    if (outOfBounds() || segment < 0 || segment >= Level.getEquation().getSegments().length)
      return false;
    if (Level.getEquation().getEquation().length() > 0 && Equation.isDrawn) {
      Line l = Level.getEquation().getSegments()[segment];
      return l.collidesWith(pos, vel.magnitude());
    }
    return false;
  }

  // checks if a goose collides with a bowtie
  public void checkBowties() {
    if (outOfBounds())
      return;
    for (Bowtie b : Level.getBowties()) {
      b.checkCollision(pos);
    }
  }

  // checks if a goose is out of bounds
  // @return: true if the goose is out of bounds
  private boolean outOfBounds() {
    return (pos.getX() <= 5 || pos.getX() >= 995 || pos.getY() <= 200 || pos.getY() >= 800) && fired;
  }

  // draws a goose
  public void draw(Graphics2D g2) {
    g2.drawImage(goose, (int) pos.getX() - 22, (int) pos.getY() - 30, null);
  }

  // draws all geese
  public static void drawAll(Graphics2D g2) {
    for (Goose g : geese) {
      g.draw(g2);
    }
  }

  // updates all geese
  public static void update() {
    for (Goose g : geese) {
      g.move();
      g.checkBowties();
    }
  }

}
