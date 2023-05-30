import java.awt.*;
import javax.swing.*;

public class Goose {
  private Vector pos;
  private Vector vel;

  public Goose(double x, double y, double vx, double vy) {
    pos = new Vector(x, y);
    vel = new Vector(vx, vy);
  }

  public void move() {
    pos.add(vel);
    vel.add(Constants.GRAVITY);
  }

  public void draw(Graphics2D g2) {
    g2.fillOval(pos.getX(), pos.getY(), 20, 20);
  }

}
