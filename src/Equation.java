import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

public class Equation implements KeyListener {
  private String equation = "";
  private String tempEquation = "";

  public Equation() {
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
      tempEquation += "^";
    }
  }

  public void add(String s) {
    tempEquation += s;
  }

  // keylistener
  public void keyPressed(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == '\n') { // enter
      equation = tempEquation;
    } else if (e.getKeyChar() == '\b') { // backspace
      tempEquation = tempEquation.substring(0, tempEquation.length() - 1);
    } else {
      add(e.getKeyChar());
    }
    System.out.println(tempEquation);
  }

  public String toString() {
    return tempEquation;
  }

  public static String replace(String exp, int i, String x) {
    boolean prevIsOp = i == 0 || i > 0 && "+-*/^()".indexOf(exp.charAt(i - 1)) != -1;
    boolean nextIsOp = i == exp.length() - 1 || i < exp.length() - 1 && "+-*/^()".indexOf(exp.charAt(i + 1)) != -1;
    if (prevIsOp && nextIsOp) {
      exp = exp.substring(0, i) + x + exp.substring(i + 1);
    } else if (prevIsOp) {
      if (i == exp.length() - 1) {
        exp = exp.substring(0, i) + x;
      } else {
        exp = exp.substring(0, i) + x + "*" + exp.substring(i + 1);
      }
    } else if (nextIsOp) {
      if (i == 0) {
        exp = x + exp.substring(i + 1);
      } else {
        exp = exp.substring(0, i) + "*" + x + exp.substring(i + 1);
      }
    } else {
      exp = exp.substring(0, i) + "*" + x + "*" + exp.substring(i + 1);
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

  public static double evaluate(String exp) throws Exception {
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
        exp = exp.substring(0, i) + Constants.FUNCTIONS.get(function).apply(evaluate(sub)) + exp.substring(j);
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
      // check for *(x) or (x)* or (x)
      boolean prevIsOp = start >= 1 && "+-*/^".indexOf(exp.charAt(start - 1)) != -1;
      boolean nextIsOp = end == exp.length() - 1
          || end < exp.length() - 1 && "+-*/^".indexOf(exp.charAt(end + 1)) != -1;
      String sub = "" + subResult;
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
        Double exponent = evaluate(exp.substring(i + 1, j));
        // find start of term
        int k = i;
        do {
          i--;
        } while (i > 0 && "+-*/^".indexOf(exp.charAt(i - 1)) == -1 && exp.charAt(i - 1) != '(');
        Double base = evaluate(exp.substring(i, k));
        exp = exp.substring(0, i) + Math.pow(base, exponent) + exp.substring(j);
      } else if ("+-*/".indexOf(exp.charAt(i)) != -1 && i > 0 && exp.charAt(i - 1) != '^') {
        j = i;
      }
      i--;
    }

    // MDAS left to right
    double result = 0;
    i = 0;
    j = 0;
    boolean add = true;
    while (j < exp.length()) {
      if (exp.charAt(j) == '+') {
        if (add) {
          result += evaluate(exp.substring(i, j));
        } else {
          result -= evaluate(exp.substring(i, j));
        }
        add = true;
        i = j + 1;
        j += 2;
        if (j >= exp.length() - 1) {
          if (add) {
            result += evaluate(exp.substring(i, exp.length()));
          } else {
            result -= evaluate(exp.substring(i, exp.length()));
          }
          break;
        }
      } else if (j > 0 && exp.charAt(j) == '-' && "*/".indexOf(exp.charAt(j - 1)) == -1) {
        if (add) {
          result += evaluate(exp.substring(i, j));
        } else {
          result -= evaluate(exp.substring(i, j));
        }
        add = false;
        i = j + 1;
        j += 2;
        if (j >= exp.length() - 1) {
          if (add) {
            result += evaluate(exp.substring(i, exp.length()));
          } else {
            result -= evaluate(exp.substring(i, exp.length()));
          }
          break;
        }
      } else {
        j++;
      }
      if (i > 0 && j == exp.length()) {
        if (add) {
          result += evaluate(exp.substring(i, exp.length()));
        } else {
          result -= evaluate(exp.substring(i, exp.length()));
        }
      }
    }
    if (result != 0) {
      return result;
    }
    double result2 = 1;
    StringTokenizer st = new StringTokenizer(exp, "*/", true);
    while (st.hasMoreTokens()) {
      String token = st.nextToken();
      if (token.equals("*")) {
        result2 *= evaluate(st.nextToken());
      } else if (token.equals("/")) {
        result2 /= evaluate(st.nextToken());
      } else {
        result2 *= evaluate(token);
      }
    }
    result += result2;
    return result;
  }

  private int transform(double x) {
    try {
      int y = (int) (-0.001 * evaluate(substitute(this.equation, x - 50)) + 300);
      // System.out.println(y);
      return y;
    } catch (Exception e) {
      return 0;
    }
  }

  public void draw(Graphics2D g2) {
    g2.setStroke(new BasicStroke(2));
    for (int i = 0; i < 100; i++) {
      g2.drawLine(6 * i + 300, transform(i), 6 * i + 306, transform(i + 1));
    }
  }

}
