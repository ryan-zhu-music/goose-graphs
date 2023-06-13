import java.awt.*;
import java.util.ArrayList;

public class Bowtie {
  private Vector pos;
  private boolean obtained;
  public static ArrayList<Bowtie> bowties = new ArrayList<>();

  public Bowtie(int x, int y) {
    this.pos = new Vector(x, y);
    this.obtained = false;
    bowties.add(this);
  }

  public boolean checkCollision(Vector v) {
    if (pos.distance(v) < 20) {
      obtained = true;
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
