import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Goose {
  Vector pos;
  Vector vel;
  int variation;
  int axis;
  Equation e;

  public Goose(double x, double y, double vx, double vy, Equation e) {
    this.pos = new Vector(x, y);
    this.vel = new Vector(vx, vy);
    this.e = e;
  }

  public void move() {
    axis = (int) (Math.random() * (3 - 1 + 1) + 1);
    variation = (int) (Math.random() * (4 - 0 + 1));

    pos.add(vel);
    if (axis == 1)
      pos.addX(variation);
    else
      pos.addY(variation);
    vel.add(Constants.GRAVITY);
  }

  public void checkCollision() {
    Vector[] points = e.getPoints();
    int x = (int) pos.getX();
    int segment = (int) (x / 10);

  }

  public void draw(Graphics2D g2) {
    g2.fillOval(pos.getX(), pos.getY(), 20, 20);
  }

}
