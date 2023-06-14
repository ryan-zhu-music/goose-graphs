import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

public class Equation implements KeyListener {
  private String equation = "";
  private String tempEquation = "";
  private Vector[] points = new Vector[101];
  private Line[] segments = new Line[100];
  static boolean isDrawn = false;
  static boolean error = false;
  static double subX;

  public Equation() {
  }

  public String getEquation() {
    return equation;
  }

  public void setEquation(String tempEquation) {
    this.tempEquation = tempEquation;
  }

  public void add(char c) {
    // only numbers, x, +, -, *, /, ^, (, ), .,
    if (c >= '0' && c <= '9' || c >= '(' && c <= '.' && c != ',') {
      tempEquation += c;
    } else if (c == 'x' || c == 'X') {
      tempEquation += 'x';
    } else if (c == 'e' || c == 'E') {
      tempEquation += 'e';
    } else if (c == '/') {
      tempEquation += "/";
    } else if (c == '^') {
      tempEquation += "^(";
    }
  }

  public void add(String s) {
    tempEquation += s;
  }

  public Vector[] getPoints() {
    return points;
  }

  public Line[] getSegments() {
    return segments;
  }

  // keylistener
  public void keyPressed(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == '\b') { // backspace
      tempEquation = tempEquation.substring(0, tempEquation.length() - 1);
    } else {
      add(e.getKeyChar());
    }
  }

  public String toString() {
    return tempEquation;
  }

  public void reset() {
    tempEquation = "";
  }

  public void setEquation() {
    if (!equation.equals(tempEquation)) {
      equation = tempEquation;
      isDrawn = false;
      Line.setEquation(this);
      for (int i = -50; i <= 50; i++) {
        points[i + 50] = transform(i);
        if (i > -50) {
          segments[i + 49] = new Line(points[i + 49], points[i + 50]);
        }
      }
      isDrawn = true;
    }
  }

  public static String replace(String exp, int i, String x) {
    boolean prevIsOp = i == 0 || i > 0 && "+-*/^()".indexOf(exp.charAt(i - 1)) != -1;
    boolean nextIsOp = i == exp.length() - 1 || i < exp.length() - 1 && "+-*/^()".indexOf(exp.charAt(i + 1)) != -1;
    if (prevIsOp && nextIsOp) {
      exp = exp.substring(0, i) + "(" + x + ")" + exp.substring(i + 1);
    } else if (prevIsOp) {
      if (i == exp.length() - 1) {
        exp = exp.substring(0, i) + x;
      } else {
        exp = exp.substring(0, i) + "(" + x + ")" + "*" + exp.substring(i + 1);
      }
    } else if (nextIsOp) {
      if (i == 0) {
        exp = x + exp.substring(i + 1);
      } else {
        exp = exp.substring(0, i) + "*" + "(" + x + ")" + exp.substring(i + 1);
      }
    } else {
      exp = exp.substring(0, i) + "*" + "(" + x + ")" + "*" + exp.substring(i + 1);
    }
    return exp;
  }

  // replace x with its value
  public static String substitute(String exp, double x) {
    int i = 0;
    while (i < exp.length()) {
      if (exp.charAt(i) == 'x') { // replace x with *x or x* or just x
        exp = replace(exp, i, "" + x);
        i += ("" + x).length() - 1;
      } else if (exp.charAt(i) == 'e') {
        exp = replace(exp, i, "" + Math.E);
        i += ("" + Math.E).length() - 1;
      } else if (exp.charAt(i) == 'p') {
        exp = replace(exp, i, "" + Math.PI);
        i += ("" + Math.PI).length() - 1;
      }
      i++;
    }
    return exp;
  }

  public double evaluate(String exp) {
    if (exp.length() == 0) {
      return 0;
    }
    try {
      return Double.parseDouble(exp);
    } catch (NumberFormatException e) {
    }

    // functions
    for (String function : Constants.functions) {
      while (exp.contains(function)) {
        int i = exp.indexOf(function);
        int j = i + function.length();
        int count = 1;
        while (count > 0) {
          if (exp.charAt(j) == ')') {
            count--;
          } else if (exp.charAt(j) == '(') {
            count++;
          }
          j++;
        }
        String sub = exp.substring(i + function.length(), j - 1);
        String result = String.format("%.5f", Constants.FUNCTIONS.get(function).apply(evaluate(sub)));
        if (result.equals("NaN")) {
          throw new NumberFormatException();
        }
        exp = exp.substring(0, i) + result + exp.substring(j);
      }
    }

    // parentheses
    while (exp.indexOf('(') != -1) {
      int start = exp.indexOf('(');
      int end = exp.indexOf(')');
      int count = 1;
      for (int i = start + 1; i < exp.length(); i++) {
        if (exp.charAt(i) == '(') {
          count++;
        } else if (exp.charAt(i) == ')') {
          count--;
        }
        if (count == 0) {
          end = i;
          break;
        }
      }
      Double subResult = evaluate(exp.substring(start + 1, end));
      // if exponent
      if (end < exp.length() - 1 && exp.charAt(end + 1) == '^') {
        int k = end + 2;
        int l = k + 1;
        int count2 = 1;
        while (count2 > 0 && l < exp.length()) {
          if (exp.charAt(l) == '(') {
            count2++;
          } else if (exp.charAt(l) == ')') {
            count2--;
          }
          l++;
        }
        Double exponent = evaluate(exp.substring(k, l));
        subResult = Math.pow(subResult, exponent);
        end = l - 1;
      }
      // check for *(x) or (x)* or (x)
      boolean prevIsOp = start >= 1 && "+-*/^".indexOf(exp.charAt(start - 1)) != -1;
      boolean nextIsOp = end == exp.length() - 1
          || end < exp.length() - 1 && "+-*/^".indexOf(exp.charAt(end + 1)) != -1;
      String sub = String.format("%.10f", subResult); // avoid E notation
      if (!prevIsOp) {
        sub = "*" + sub;
      }
      if (!nextIsOp) {
        sub = sub + "*";
      }
      exp = exp.substring(0, start) + sub + exp.substring(end + 1);
    }

    // exponents right to left
    int i = exp.length() - 1;
    int j = exp.length(); // end of exponent
    while (i >= 0) {
      if (exp.charAt(i) == '^') {
        // find end of exponent
        int k = i + 1;
        int l = k + 1;
        int count = 1;
        while (count > 0 && l < exp.length()) {
          if (exp.charAt(l) == '(') {
            count++;
          } else if (exp.charAt(l) == ')') {
            count--;
          }
          l++;
        }
        Double exponent = evaluate(exp.substring(i + 1, j));
        // find start of term
        k = i;
        do {
          i--;
        } while (i > 0 && !isOp(exp.charAt(i - 1))
            || i > 1 && !isOp(exp.charAt(i - 1)) && !isOp(exp.charAt(i - 2))); // does not work for negative bases
        Double base = evaluate(exp.substring(i, k));
        exp = exp.substring(0, i) + String.format("%.10f", (Math.pow(base, exponent) * 10000) / 10000.0)
            + exp.substring(j);
      } else if ("+-*/".indexOf(exp.charAt(i)) != -1 && i > 0 && exp.charAt(i - 1) != '^') {
        j = i;
      }
      i--;
    }
    return evalSimple(exp);
  }

  private boolean isOp(char c) {
    return "+-*/^".indexOf(c) != -1;
  }

  // evaluate an expression with only +-*/
  private double evalSimple(String exp) {
    double result = 0;
    int i = 0;
    int j = 0;
    boolean add = true;
    if (exp.startsWith("--") || exp.startsWith("++")) {
      exp = exp.substring(2);
    }
    int k = 0;
    while (k < exp.length() - 2) {
      if (exp.charAt(k) == '-' && exp.charAt(k + 1) == '-') {
        exp = exp.substring(0, k) + "+" + exp.substring(k + 2);
      } else if (exp.charAt(k) == '-' && exp.charAt(k + 1) == '+') {
        exp = exp.substring(0, k) + "-" + exp.substring(k + 2);
      } else if (exp.charAt(k) == '+' && exp.charAt(k + 1) == '-') {
        exp = exp.substring(0, k) + "-" + exp.substring(k + 2);
      } else if (exp.charAt(k) == '+' && exp.charAt(k + 1) == '+') {
        exp = exp.substring(0, k) + "+" + exp.substring(k + 2);
      }
      k++;
    }
    while (j < exp.length()) {
      if (exp.charAt(j) == '+') {
        result += (add ? 1 : -1) * product(exp.substring(i, j));
        add = true;
        i = j + 1;
        j += 2;
        if (j >= exp.length() - 1) {
          result += product(exp.substring(i, exp.length()));
          break;
        }
      } else if (j > 0 && exp.charAt(j) == '-' && "*/".indexOf(exp.charAt(j - 1)) == -1) {
        result += (add ? 1 : -1) * product(exp.substring(i, j));
        add = false;
        i = j + 1;
        j += 2;
        if (j >= exp.length() - 1) {
          result -= product(exp.substring(i, exp.length()));
          break;
        }
      } else {
        j++;
      }
      if (j >= exp.length()) {
        result += product(exp.substring(i, exp.length()));
      }
    }
    return result;
  }

  private double product(String exp) {
    StringTokenizer st = new StringTokenizer(exp, "*/", true);
    double result = 1;
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (token.equals("*")) {
        result *= Double.parseDouble(st.nextToken());
      } else if (token.equals("/")) {
        result /= Double.parseDouble(st.nextToken());
      } else {
        result *= Double.parseDouble(token);
      }
    }
    return result;
  }

  private Vector transform(double x) {
    double y = tryEvaluate(x / 5.0);
    if (isUndefined(y)) {
      y = tryEvaluate(x / 5.0 + 0.000001);
      if (isUndefined(y)) {
        y = tryEvaluate(x / 5.0 - 0.000001);
        if (isUndefined(y)) {
          error = true;
          return new Vector(10 * x + 500, Constants.iNAN);
        } else {
          return new Vector(10 * x + 495, y);
        }
      } else {
        return new Vector(10 * x + 505, y);
      }
    } else {
      return new Vector(10 * x + 500, y);
    }
  }

  private double tryEvaluate(double x) {
    try {
      double y = -50 * evaluate(substitute(this.equation, x)) + 450;
      error = false;
      return y;
    } catch (Exception e) {
      return Constants.iNAN;
    }
  }

  public static boolean isUndefined(double x) {
    return Double.isNaN(x) || Double.isInfinite(x);
  }

  public void draw(Graphics2D g2) {
    g2.setStroke(new BasicStroke(2));
    if (this.equation.length() > 0) {
      for (int i = 0; i < 100; i++) {
        if (!error)
          segments[i].draw(g2);
        // g2.drawLine(points[i].getX(), points[i].getY(), points[i + 1].getX(),
        // points[i + 1].getY());
      }
    }
  }
}
