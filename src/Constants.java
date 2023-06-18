// Name: Delin Gu and Ryan Zhu
// Date: June 17th, 2021
// Assignment: FINAL ISU!!!
// Description: various constants used throught the program, including each level

import java.awt.Color;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.function.Function;

public class Constants {
  static final Vector GRAVITY = new Vector(0, 0.15);
  static final double FRICTION = 0.999;
  static final int MAX_VELOCITY = 10;
  static final Color BUTTON_COLOR = new Color(255, 255, 255, 100);
  static final Color BUTTON_TEXT_COLOR = new Color(0, 0, 0, 100);
  static final double iNAN = Math.log(-1);

  // functions
  static final String[] functions = {
      "sin(", "cos(", "ln(", "log(", "sqrt(", "abs(" };
  static final HashMap<String, Function<Double, Double>> FUNCTIONS = new HashMap<>();
  static {
    FUNCTIONS.put("sin(", (x) -> Math.sin((double) x));
    FUNCTIONS.put("cos(", (x) -> Math.cos((double) x));
    FUNCTIONS.put("ln(", (x) -> Math.log((double) x));
    FUNCTIONS.put("log(", (x) -> Math.log10((double) x));
    FUNCTIONS.put("sqrt(", (x) -> Math.sqrt((double) x));
    FUNCTIONS.put("abs(", (x) -> Math.abs((double) x));
  }

  // color themes
  static final HashMap<String, Color> COLORS = new HashMap<>();
  static {
    COLORS.put("ivory", new Color(255, 255, 240));
    COLORS.put("pink", new Color(252, 208, 208));
    COLORS.put("maroon", new Color(194, 131, 131));
    COLORS.put("lime", new Color(139, 194, 124));
    COLORS.put("beige", new Color(220, 207, 185));
    COLORS.put("tomato", new Color(213, 103, 81));
    COLORS.put("tomato_light", new Color(225, 120, 98));
  }

  static ArrayList<Vector> LEVEL_STARTS = new ArrayList<>();
  static ArrayList<Vector[]> LEVEL_BOWTIES = new ArrayList<>();

  static int[][][] positions = {
      { { 754, 235 }, { 453, 460 }, { 222, 563 }, { 342, 513 } }, // 0.5x
      { { 372, 235 }, { 500, 598 }, { 547, 525 }, { 576, 429 } }, // x^2-3
      { { 500, 235 }, { 420, 496 }, { 266, 353 }, { 106, 495 } }, // 1.5*sin(x)
      { { 519, 235 }, { 41, 426 }, { 222, 426 }, { 485, 396 } }, // 5^x
      { { 800, 235 }, { 20, 750 }, { 643, 264 }, { 326, 304 }, { 102, 436 } }, // ln(x+8)+1
      { { 250, 300 }, { 500, 550 }, { 500, 650 }, { 500, 750 } }, // -2/x^2
      { { 500, 235 }, { 166, 664 }, { 500, 436 }, { 725, 605 } }, // -0.7*abs(x)
      { { 800, 235 }, { 782, 495 }, { 671, 497 }, { 698, 608 }, { 765, 608 } }, // (x-4.5)^4-4
      { { 100, 435 }, { 352, 459 }, { 500, 515 }, { 648, 459 } }, // -9/(x^2+5)
      { { 200, 235 }, { 364, 402 }, { 492, 369 }, { 673, 553 }, { 823, 533 }, { 951, 679 } }, // -0.5x+cos(x)
      { { 500, 235 }, { 500, 423 }, { 193, 595 }, { 250, 725 }, { 745, 721 }, { 800, 604 } }, // 0.01*(x-7)*(x+7)*x^2
      { { 384, 235 }, { 806, 576 }, { 613, 651 }, { 476, 674 }, { 407, 478 }, { 948, 508 } }, // x^2/(2x+6)-5
      { { 850, 235 }, { 605, 570 }, { 476, 597 }, { 406, 686 }, { 845, 443 }, { 776, 649 } }, // 0.8*(x-5)^(x-5)-5
      { { 100, 235 }, { 170, 503 }, { 321, 369 }, { 543, 475 }, { 944, 413 } }, // -cos(4*sqrt(x+9))/log(x+11)
      { { 70, 300 }, { 181, 577 }, { 345, 380 }, { 449, 581 }, { 574, 699 } }, // -3(2^x-2)/(cos(x)+2)-5
  };

  // adds the positions to the arraylists
  static {
    for (int i = 0; i < positions.length; i++) {
      LEVEL_STARTS.add(new Vector(positions[i][0][0], positions[i][0][1]));
      LEVEL_BOWTIES.add(new Vector[positions[i].length - 1]);
      for (int j = 1; j < positions[i].length; j++) {
        LEVEL_BOWTIES.get(i)[j - 1] = new Vector(positions[i][j][0], positions[i][j][1]);
      }
    }
  }

}
