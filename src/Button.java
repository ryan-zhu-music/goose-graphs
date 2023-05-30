import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Button implements MouseListener {
  private Vector topLeft;
  private Vector bottomRight;
  private String text;

  public Button(int x, int y, int width, int height, String text) {
    this.topLeft = new Vector(x, y);
    this.bottomRight = new Vector(x + width, y + height);
    this.text = text;
  }

  public void draw(Graphics2D g2) {
    g2.setColor(Constants.BUTTON_COLOR);
    g2.fillRect(topLeft.getX(), topLeft.getY(), bottomRight.getX() - topLeft.getX(),
        bottomRight.getY() - topLeft.getY());
    g2.setColor(Constants.BUTTON_TEXT_COLOR);
    g2.drawString(text, topLeft.getX() + 10, topLeft.getY() + 20);
  }

  public boolean isClicked(int x, int y) {
    return x >= topLeft.getX() && x <= bottomRight.getX() && y >= topLeft.getY() && y <= bottomRight.getY();
  }

  public void mouseClicked(MouseEvent e) {
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