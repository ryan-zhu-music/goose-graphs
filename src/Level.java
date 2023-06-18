// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: This class is used to create instances of each of the 15 levels. It stores unique states for each game.

import java.util.*;
import javax.swing.*;
import java.awt.*;

public class Level {
  private static Equation e;
  private static ArrayList<Bowtie> bowties;
  public static int currentLevel = -1;

  private int levelID;
  private Button[] buttons = new Button[8];
  private final int GEESE_COUNT = 5;
  private boolean completed = false;
  private int difficulty;
  private Vector gooseStart;
  private Vector[] bowtiePos;
  private long bestTime = Long.MAX_VALUE;
  private long time;
  private static long startTime;
  private static boolean running = false;

  int[] x = { 137, 300, 463 };

  public Level(int levelID, Vector gooseStart, Vector[] bowtiePos, int difficulty, JPanel panel) {
    this.levelID = levelID;
    this.difficulty = difficulty;
    this.gooseStart = gooseStart;
    this.bowtiePos = bowtiePos;
    e = new Equation();
    panel.addKeyListener(e);
    for (int i = 0; i < 6; i++) {
      buttons[i] = new Button(x[i % 3], 50 * (i / 3 + 1) + 43, 144, 36, Constants.functions[i], null, levelID);
      panel.addMouseListener(buttons[i]);
    }
    buttons[6] = new Button(625, 93, 144, 36, "graph", (x) -> e.setEquation(), levelID);
    buttons[6].setColor(Constants.COLORS.get("lime"));
    buttons[7] = new Button(625, 143, 144, 36, "go(ose)", (x) -> Goose.fire(), levelID);
    buttons[7].setColor(Constants.COLORS.get("lime"));
    panel.addMouseListener(buttons[6]);
    panel.addMouseListener(buttons[7]);
    panel.setFocusable(true);
  }

  // called when a new level is initiated; resets relevant properties
  public void init() {
    e.clear();
    for (int i = 0; i < GEESE_COUNT; i++) {
      new Goose(gooseStart.getX(), gooseStart.getY(), 0, 1);
    }
    bowties = new ArrayList<>();
    for (Vector v : bowtiePos) {
      bowties.add(new Bowtie((int) v.getX(), (int) v.getY()));
    }
    Bowtie.reset();
    time = 0;
    currentLevel = this.levelID;
    running = true;
  }

  // called when the game is exited (not the program)
  public static void halt() {
    running = false;
    Goose.geese.clear();
  }

  // called when the geese are fired to begin timing the level
  public static void startTimer() {
    startTime = System.currentTimeMillis();
  }

  // returns the time in seconds
  // @param: best - whether to return the best time or the current time
  // @return: the time in seconds
  public String getTime(boolean best) {
    if (this.bestTime == Long.MAX_VALUE) {
      return "N/A";
    }
    return String.format("%d.%03ds", best ? this.bestTime / 1000 : this.time / 1000,
        best ? this.bestTime % 1000 : this.time % 1000);
  }

  // getters
  public int getDifficulty() {
    return this.difficulty;
  }

  public int getLevelID() {
    return this.levelID;
  }

  public static Equation getEquation() {
    return e;
  }

  public static ArrayList<Bowtie> getBowties() {
    return bowties;
  }

  public boolean isCompleted() {
    return this.completed;
  }

  public static boolean isRunning() {
    return running;
  }

  // updates the level each frame
  public void update() {
    if (running) {
      Goose.update();
      if (Bowtie.getCount() == bowties.size()) { // win
        this.completed = true;
        time = System.currentTimeMillis() - startTime;
        if (time < bestTime) {
          bestTime = time;
        }
        Level.halt();
        Menu.currentScreen = 4;
      }
    }
  }

  // draws the level each frame
  public void draw(Graphics2D g2) {
    if (running) {
      // bg
      g2.setColor(Constants.COLORS.get("ivory"));
      g2.fillRect(0, 0, 1000, 800);
      // gridlines
      g2.setFont(new Font("Monospace", Font.BOLD, 10));
      for (int i = 0; i < 1000; i += 50) {
        g2.setColor(Constants.COLORS.get("beige"));
        g2.drawLine(i, 0, i, 800);
        if (i != 500) {
          g2.setColor(Constants.COLORS.get("tomato_light"));
          g2.drawString(((i - 10) - ((i / 50) * 49)) + "", i - 3, 465);
        }
      }
      for (int i = 0; i < 600; i += 50) {
        g2.setColor(Constants.COLORS.get("beige"));
        g2.drawLine(0, i + 200, 1000, i + 200);
        if (i != 250) {
          g2.setColor(Constants.COLORS.get("tomato_light"));
          g2.drawString(((-i / 50) + 5) + "", 505, i + 204);
        }
      }
      // axes
      g2.setColor(Constants.COLORS.get("tomato_light"));
      g2.setStroke(new BasicStroke(3));
      g2.drawLine(500, 0, 500, 800);
      g2.drawLine(0, 450, 1000, 450);
      g2.setColor(Color.RED);
      // equation
      e.draw(g2);
      // menu
      g2.setColor(Constants.COLORS.get("beige"));
      g2.fillRect(0, 0, 1000, 200);
      // equation
      g2.setColor(Constants.COLORS.get("tomato"));
      g2.setFont(new Font("Monospace", Font.BOLD, 30));
      g2.drawString("y = " + e.toString(), 137, 40);
      // error
      if (Equation.error) {
        g2.setColor(Constants.COLORS.get("tomato"));
        g2.setFont(new Font("Monospace", Font.BOLD, 20));
        g2.drawString("Invalid equation", 137, 75);
      }
      // buttons
      g2.setFont(new Font("Monospace", Font.PLAIN, 20));
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
  }

  public String toString() {
    return "Level " + this.levelID + " (Difficulty: " + this.difficulty + ")" + (this.completed ? " (Completed)" : "");
  }
}
