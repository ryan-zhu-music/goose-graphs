import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class Win {
  public static BufferedImage winScreen;

  public Win() {
    try {
      winScreen = ImageIO.read(new File("winScreen.png"));
    } catch (FileNotFoundException e) {
      System.out.println("File not found!");
    } catch (IOException e) {
      System.out.println("Reading Error!");
    }
  }

  public static void draw(Graphics2D g2) {
    g2.drawImage(winScreen, 0, 0, null);

    if (Menu.goose) {
      g2.drawImage(About.purpleGoose1, 800, 600, null);
      g2.drawImage(About.pinkGoose1, 200, 600, null);
    } else if (!Menu.goose) {
      g2.drawImage(About.purpleGoose2, 800, 600, null);
      g2.drawImage(About.pinkGoose2, 200, 600, null);
    }
  }
}
