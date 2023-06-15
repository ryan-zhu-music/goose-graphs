import java.awt.*;

public class Bowtie {
  private Vector pos;
  private boolean obtained;
  private static int count = 0;

  public Bowtie(int x, int y) {
    this.pos = new Vector(x, y);
    this.obtained = false;
  }

  public static void reset() {
    count = 0;
  }

  public static int getCount() {
    return count;
  }

  public boolean checkCollision(Vector v) {
    if (pos.distance(v) < 20) {
      if (!obtained) {
        count++;
        obtained = true;
      }
      return true;
    }
    return false;
  }

  public void draw(Graphics g2) {
    if (!obtained) {
      g2.fillOval((int) pos.getX() - 10, (int) pos.getY() - 10, 20, 20);
    }
  }

}
