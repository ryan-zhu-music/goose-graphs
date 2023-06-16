import java.awt.*;
import java.awt.event.*;

import javax.lang.model.type.NullType;
import javax.swing.*;
import java.util.function.Function;

public class Button implements MouseListener {
  private Vector topLeft;
  private Vector bottomRight;
  private int width;
  private int height;
  private String text;
  private Function<NullType, Boolean> f;
  private Color color = Constants.BUTTON_COLOR;

  public Button(int x, int y, int width, int height, String text, Function<NullType, Boolean> f) {
    this.topLeft = new Vector(x, y);
    this.bottomRight = new Vector(x + width, y + height);
    this.width = width;
    this.height = height;
    this.text = text;
    this.f = f;
  }

  public void draw(Graphics2D g2) {
    g2.setColor(color);
    g2.fillRect((int) topLeft.getX(), (int) topLeft.getY(), width, height);
    g2.setColor(Constants.BUTTON_TEXT_COLOR);
    g2.drawString(text, (int) topLeft.getX() + 10, (int) topLeft.getY() + 20);
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public boolean isClicked(int x, int y) {
    return x >= topLeft.getX() && x <= bottomRight.getX() && y >= topLeft.getY() && y <= bottomRight.getY();
  }

  public void mouseClicked(MouseEvent event) {
    if (this.text.startsWith("sin")) {
      System.out.println(event.getX() + " " + event.getY());
    }
    if (isClicked(event.getX(), event.getY())) {
      if (f != null) {
        f.apply(null);
      } else {
        Level.getEquation().add(text);
      }
    }
  }

  public void mousePressed(MouseEvent e) {
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }
}