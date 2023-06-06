import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Goose {
  Vector pos;
  Vector vel;
  int variation;
  int axis;

  public Goose(double x, double y, double vx, double vy) {
    pos = new Vector(x, y);
    vel = new Vector(vx, vy);
  }

  public void move() {
    axis = (int)(Math.random()*(3-1+1)+1);
    variation = (int)(Math.random()*(4-0+1));
  
    pos.add(vel);
    if(axis == 1) 
      pos.addX(variation);
    else 
      pos.addY(variation);
    vel.add(new Vector(0, Constants.GRAVITY));
  }

  public void draw(Graphics2D g2) {
    g2.fillOval(pos.getX(), pos.getY(), 20, 20);
  }

}
