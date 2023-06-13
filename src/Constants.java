import java.awt.Color;
import java.util.HashMap;
import java.util.function.Function;

public class Constants {
  static final Vector GRAVITY = new Vector(0, 0.2);
  static final double FRICTION = 0.99;
  static final int MAX_VELOCITY = 10;
  static final Color BUTTON_COLOR = new Color(255, 255, 255, 100);
  static final Color BUTTON_TEXT_COLOR = new Color(0, 0, 0, 100);
  static final double iNAN = Math.log(-1);
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

  static final HashMap<String, Color> COLORS = new HashMap<>();

  static {
    COLORS.put("ivory", new Color(255, 255, 240));
    COLORS.put("pink", new Color(252, 208, 208));
    COLORS.put("maroon", new Color(194, 131, 131));
    COLORS.put("lime", new Color(162, 229, 165));
  }

}
