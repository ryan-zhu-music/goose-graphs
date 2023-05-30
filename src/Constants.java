import java.awt.Color;
import java.util.HashMap;
import java.util.function.Function;

public class Constants {
  static final double GRAVITY = 0.4;
  static final Color BUTTON_COLOR = new Color(255, 255, 255, 100);
  static final Color BUTTON_TEXT_COLOR = new Color(0, 0, 0, 100);
  static final String[] functions = {
      "sin(", "cos(", "tan(", "ln(", "log(", "sqrt(", "abs(", "e", "pi" };
  static final HashMap<String, Function<Double, Double>> FUNCTIONS = new HashMap<>();

  static {
    FUNCTIONS.put("sin(", (x) -> Math.sin((double) x));
    FUNCTIONS.put("cos(", (x) -> Math.cos((double) x));
    FUNCTIONS.put("tan(", (x) -> Math.tan((double) x));
    FUNCTIONS.put("ln(", (x) -> Math.log((double) x));
    FUNCTIONS.put("log(", (x) -> Math.log10((double) x));
    FUNCTIONS.put("sqrt(", (x) -> Math.sqrt((double) x));
    FUNCTIONS.put("abs(", (x) -> Math.abs((double) x));
    FUNCTIONS.put("e", (x) -> Math.E);
    FUNCTIONS.put("pi", (x) -> Math.PI);
  }

}
