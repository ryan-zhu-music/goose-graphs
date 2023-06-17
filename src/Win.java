import java.awt.*;

public class Win {
  public Win() {

  }

  public static void draw(Graphics2D g2) {
    g2.setColor(Color.BLACK);
    g2.fillRect(0, 0, 1000, 800);
    g2.setColor(Color.WHITE);
    g2.setFont(new Font("Arial", Font.BOLD, 50));
    g2.drawString("You Win!", 500, 500);
  }
}
