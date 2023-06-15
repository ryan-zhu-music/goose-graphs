import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Level extends JPanel implements Runnable, Comparable<Level> {
  private static Equation e;
  private static ArrayList<Bowtie> bowties;
  private static int currentLevel;

  private int levelID;
  private Button[] buttons = new Button[8];
  private int GEESE_COUNT = 5;
  private boolean completed = false;
  private boolean win = false;
  private int difficulty;
  Vector gooseStart;
  Vector[] bowtiePos;

  int[] x = { 137, 300, 463 };

  // add frame as parameter and add listeners??
  public Level(int levelID, Vector gooseStart, Vector[] bowtiePos, int difficulty) {
    this.levelID = levelID;
    this.difficulty = difficulty;
    this.gooseStart = gooseStart;
    this.bowtiePos = bowtiePos;
  }

  // called when a new level is initiated, resets all game states except for
  // completed
  public void init() {
    e = new Equation();
    addKeyListener(e);
    for (int i = 0; i < 6; i++) {
      buttons[i] = new Button(x[i % 3], 50 * (i / 3 + 1) + 43, 144, 36, Constants.functions[i], null);
      addMouseListener(buttons[i]);
    }
    buttons[6] = new Button(625, 93, 144, 36, "graph", (x) -> e.setEquation());
    buttons[6].setColor(Constants.COLORS.get("lime"));
    buttons[7] = new Button(625, 143, 144, 36, "go(ose)", (x) -> Goose.fire());
    buttons[7].setColor(Constants.COLORS.get("lime"));
    addMouseListener(buttons[6]);
    addMouseListener(buttons[7]);
    setFocusable(true);
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
    Thread t = new Thread(this);
    t.start();
  }

  public int compareTo(Level l) {
    if (this.completed && !l.isCompleted()) {
      return 1;
    } else if (!this.completed && l.isCompleted()) {
      return -1;
    } else {
      return this.difficulty - l.getDifficulty();
    }
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

  public void run() {
    while (true) {
      repaint();
      Goose.run();
      if (Bowtie.getCount() == bowties.size()) {
        this.completed = true;
        this.win = true;
        Thread.currentThread().interrupt();
      }
      try {
        Thread.sleep(1000 / 60);
      } catch (Exception e) {
      }
    }
  }

  public void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    super.paintComponent(g2);
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

  }

  // for testing
  public static void main(String[] args) throws Exception {
    JFrame f = new JFrame("App");
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    f.setSize(1000, 800);
    f.add(new Level(1, Constants.LEVEL_STARTS.get(0), Constants.LEVEL_BOWTIES.get(0), 1));
    f.setVisible(true);
  }
}
