import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Level {
  private static Equation e;
  private static ArrayList<Bowtie> bowties;
  private static int currentLevel = -1;

  private int levelID;
  private Button[] buttons = new Button[8];
  private final int GEESE_COUNT = 5;
  private boolean completed = false;
  private boolean win = false;
  private int difficulty;
  private Vector gooseStart;
  private Vector[] bowtiePos;
  private JPanel panel;
  public static boolean running = false;

  int[] x = { 137, 300, 463 };

  public Level(int levelID, Vector gooseStart, Vector[] bowtiePos, int difficulty, JPanel panel) {
    this.levelID = levelID;
    this.difficulty = difficulty;
    this.gooseStart = gooseStart;
    this.bowtiePos = bowtiePos;
    this.panel = panel;
  }

  // called when a new level is initiated, resets all game states except for
  // completed
  public void init() {
    e = new Equation();
    panel.addKeyListener(e);
    for (int i = 0; i < 6; i++) {
      buttons[i] = new Button(x[i % 3], 50 * (i / 3 + 1) + 43, 144, 36, Constants.functions[i], null);
      panel.addMouseListener(buttons[i]);
    }
    buttons[6] = new Button(625, 93, 144, 36, "graph", (x) -> e.setEquation());
    buttons[6].setColor(Constants.COLORS.get("lime"));
    buttons[7] = new Button(625, 143, 144, 36, "go(ose)", (x) -> Goose.fire());
    buttons[7].setColor(Constants.COLORS.get("lime"));
    panel.addMouseListener(buttons[6]);
    panel.addMouseListener(buttons[7]);
    panel.setFocusable(true);
    for (int i = 0; i < GEESE_COUNT; i++) {
      new Goose(gooseStart.getX(), gooseStart.getY(), 0, 1);
    }
    bowties = new ArrayList<>();
    for (Vector v : bowtiePos) {
      bowties.add(new Bowtie((int) v.getX(), (int) v.getY()));
    }
    Bowtie.reset();
    this.win = false;
    currentLevel = this.levelID;
    running = true;
  }

  public int getDifficulty() {
    return this.difficulty;
  }

  public static Equation getEquation() {
    return e;
  }

  public static ArrayList<Bowtie> getBowties() {
    return bowties;
  }

  public static int getCurrentLevel() {
    return currentLevel;
  }

  public boolean getWin() {
    return this.win;
  }

  public boolean isCompleted() {
    return this.completed;
  }

  public static boolean isRunning() {
    return running;
  }

  public void update() {
    if (running) {
      Goose.update();
      if (Bowtie.getCount() == bowties.size()) {
        this.completed = true;
        this.win = true;
        Thread.currentThread().interrupt();
      }
    }
  }

  public void draw(Graphics2D g2) {
    if (running) {
      // bg
      g2.setColor(Constants.COLORS.get("ivory"));
      g2.fillRect(0, 0, 1000, 800);
      // gridlines
      g2.setColor(Color.BLACK);
      for (int i = 0; i < 1000; i += 50) {
        g2.drawLine(i, 0, i, 800);
      }
      for (int i = 0; i < 800; i += 50) {
        g2.drawLine(0, i, 1000, i);
      }
      // axes
      g2.setStroke(new BasicStroke(3));
      g2.drawLine(500, 0, 500, 800);
      g2.drawLine(0, 450, 1000, 450);
      g2.setColor(Color.RED);
      // equation
      e.draw(g2);
      // menu
      g2.setColor(Constants.COLORS.get("pink"));
      g2.fillRect(0, 0, 1000, 200);
      // equation
      g2.setColor(Color.BLACK);
      g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
      g2.drawString("y=" + e.toString(), 137, 19);
      // buttons
      for (Button button : buttons) {
        button.draw(g2);
      }
      // geese
      Goose.drawAll(g2);
      // bowties
      for (Bowtie bowtie : bowties) {
        bowtie.draw(g2);
      }

      if (Equation.error) {
        g2.setColor(Color.RED);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g2.drawString("Invalid equation", 137, 27);
      }
    }
  }

  // for testing
  public static void main(String[] args) throws Exception {
    JFrame f = new JFrame("App");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(1000, 800);
    JPanel p = new JPanel();
    p.setPreferredSize(new Dimension(1000, 800));
    Level l = new Level(1, Constants.LEVEL_STARTS.get(0), Constants.LEVEL_BOWTIES.get(0), 1, p);
    f.add(p);
    f.setVisible(true);
  }
}
