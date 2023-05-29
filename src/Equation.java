import java.util.StringTokenizer;
import java.awt.*;

public class Equation {
  String equation;

  public Equation(String equation) {
    this.equation = equation;
  }

  public double eval(double x) {
    return Math.pow(Math.E, Math.PI * x / 2.0)
        * Math.sin(x * x * x) / Math.atan(Math.log(63));
  }

  public int transform(double x) {
    return (int) -eval((x - 500) / 25.0) + 300;
  }

  public void draw(Graphics2D g2) {
    g2.setStroke(new BasicStroke(2));
    for (int i = 0; i < 1000; i++) {
      g2.drawLine(i, transform(i), i + 1, transform(i + 1));
    }
  }

}
