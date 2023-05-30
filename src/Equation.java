import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;

public class Equation implements KeyListener {
  String equation = "";

  public Equation() {
  }

  public void setEquation(String equation) {
    this.equation = equation;
  }

  public void add(char c) {
    // only numbers, x, +, -, *, /, ^, (, ), .,
    if (c >= '0' && c <= '9' || c >= '(' && c <= '.' && c != ',') {
      equation += c;
    } else if (c == 'x' || c == 'X') {
      equation += 'x';
    } else if (c == 'e' || c == 'E') {
      equation += 'e';
    } else if (c == '/') {
      equation += "/";
    } else if (c == '^') {
      equation += "^";
    }
  }

  public void add(String s) {
    equation += s;
  }

  // keylistener
  public void keyPressed(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }

  public void keyTyped(KeyEvent e) {
    if (e.getKeyChar() == '\b') // backspace
      equation = equation.substring(0, equation.length() - 1);
    add(e.getKeyChar());
    System.out.println(equation);
  }

  public String toString() {
    return equation;
  }

  public double evaluate(double x) {
    String exp = equation;
    if (exp.length() == 0) {
      return 0;
    } else if (exp.length() == 1) {
      if (exp.charAt(0) == 'x') {
        return x;
      } else {
        return Double.parseDouble(exp);
      }
    }
    for (int i = 0; i < exp.length(); i++) {
      if (exp.charAt(i) == 'x') { // replace x with *x or x* or just x
        if (i == 0) {
          if ("+-*/^".indexOf(exp.charAt(i + 1)) == -1) { // if next char is not an operator
            exp = "" + x + exp.substring(i + 1);
          } else {
            exp = "" + x + exp;
          }
        } else if (i == exp.length() - 1) {
          if ("+-*/^".indexOf(exp.charAt(i - 1)) == -1) { // if previous char is not an operator
            exp = exp.substring(0, i) + x;
          } else {
            exp = exp + x;
          }
        } else {
          if ("+-*/^".indexOf(exp.charAt(i - 1)) == -1 && "+-*/^".indexOf(exp.charAt(i + 1)) == -1) {
            exp = exp.substring(0, i) + "*" + x + "*" + exp.substring(i + 1);
          } else if ("+-*/^".indexOf(exp.charAt(i - 1)) == -1) {
            exp = exp.substring(0, i) + "*" + x + exp.substring(i);
          } else if ("+-*/^".indexOf(exp.charAt(i + 1)) == -1) {
            exp = exp.substring(0, i + 1) + x + "*" + exp.substring(i + 1);
          } else {
            exp = exp.substring(0, i) + x + exp.substring(i);
          }
        }

      }
    }

    for (String function : Constants.functions) {
      while (exp.contains(function)) {
        int i = exp.indexOf(function);
        int j = i + function.length();
        int count = 1;
        while (count > 0) {
          if (exp.charAt(j) == '(') {
            count++;
          } else if (exp.charAt(j) == ')') {
            count--;
          }
          j++;
        }
        String sub = exp.substring(i + function.length(), j - 1);
        exp = exp.substring(0, i) + Constants.FUNCTIONS.get(function).apply(evalSimple(sub))
            + exp.substring(j);
      }
    }

    return evalSimple(exp);
  }

  // evaluates an expression with only numbers, +-*/
  public double evalSimple(String exp) {
    StringTokenizer terms = new StringTokenizer(exp, "+-", true);
    double result = 0;
    while (terms.hasMoreTokens()) {
      String term = terms.nextToken();
      if (term.equals("+")) {
        result += evalTerm(terms.nextToken());
      } else if (term.equals("-")) {
        result -= evalTerm(terms.nextToken());
      } else {
        result = evalTerm(term);
      }
    }
    return result;
  }

  // evaluates an expression with only numbers, */
  public double evalTerm(String exp) {
    StringTokenizer factors = new StringTokenizer(exp, "*/", true);
    double product;
    if (factors.countTokens() == 1) {
      product = Double.parseDouble(factors.nextToken());
    } else {
      product = 1;
      while (factors.hasMoreTokens()) {
        String factor = factors.nextToken();
        if (factor.equals("*")) {
          product *= Double.parseDouble(factors.nextToken());
        } else if (factor.equals("/")) {
          product /= Double.parseDouble(factors.nextToken());
        }
      }
    }
    return product;
  }

  private int transform(double x) {
    return (int) -evaluate((x - 500) / 25.0) + 300;
  }

  public void draw(Graphics2D g2) {
    g2.setStroke(new BasicStroke(2));
    for (int i = 0; i < 1000; i++) {
      g2.drawLine(i, transform(i), i + 1, transform(i + 1));
    }
  }

}
