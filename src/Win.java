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
    g2.setColor(Constants.COLORS.get("tomato_light"));
    g2.setFont(new Font("Arial", Font.BOLD, 20));
    g2.drawString("Time: " + Level.getTime(false), 380, 480);
    g2.drawString("Best time: " + Level.getTime(true), 540, 480);
  }
}
