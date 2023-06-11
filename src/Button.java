import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Button implements MouseListener {
  private Vector topLeft;
  private Vector bottomRight;
  private int width;
  private int height;
  private String text;
  private Equation e;
  private boolean goButton = false;
  private Color color = Constants.BUTTON_COLOR;

  public Button(int x, int y, int width, int height, String text, Equation e, boolean goButton) {
    this.topLeft = new Vector(x, y);
    this.bottomRight = new Vector(x + width, y + height);
    this.width = width;
    this.height = height;
    this.text = text;
    this.e = e;
    this.goButton = goButton;
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
    if (isClicked(event.getX(), event.getY())) {
      if (goButton) {
        System.out.println("goose");
        e.setEquation();
        Goose.fire();
      } else {
        e.add(text);
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