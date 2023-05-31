import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;
import java.util.StringTokenizer;

public class test {
  static String expression = "4.0x^2+(x-2)^3+3x^2-sin(x+4)+x"; // 1221
  // static String expression = "x^2+2x+sin(x)-4"; // 115.455978889
  // static String expression = "tan(x)/log(3x(x+4)^(-1/2))"; // 0.71716789713
  // static String expression = "px";
  // static String expression = "sqrt(x/(4x+2)^2)+cos(sin(tan(abs(x))))"; // 0.898
  // static String expression = "x^2";

  static final String[] functions = {
      "sin(", "cos(", "tan(", "ln(", "log(", "sqrt(", "abs(" };
  static final HashMap<String, Function<Double, Double>> FUNCTIONS = new HashMap<>();

  static {
    FUNCTIONS.put("sin(", (x) -> Math.sin((double) x));
    FUNCTIONS.put("cos(", (x) -> Math.cos((double) x));
    FUNCTIONS.put("tan(", (x) -> Math.tan((double) x));
    FUNCTIONS.put("ln(", (x) -> Math.log((double) x));
    FUNCTIONS.put("log(", (x) -> Math.log10((double) x));
    FUNCTIONS.put("sqrt(", (x) -> Math.sqrt((double) x));
    FUNCTIONS.put("abs(", (x) -> Math.abs((double) x));
  }

  public static void main(String[] args) {
    // System.out.println(evalSimple("-1/2"));
    System.out.println(evaluate(substitute(expression, -10)));
    // System.out.println(evalSimple("4+3.0*4/2-4*-0.8*3+5+10.0/2"));
    // for (double i = -50; i < 50; i++) {
    // System.out.println(6 * i + 300 + " " + transform(i));
    // }

  }

  private static int transform(double x) {
    try {
      // return (int) (-0.001 * evaluate(substitute(expression, x - 50)) + 300);
      return (int) evaluate(substitute(expression, x));
    } catch (Exception e) {
      return 0;
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

  public static double evaluate(String exp) {
    System.out.println("e:" + exp);
    if (exp.length() == 0) {
      return 0;
    }
    try {
      // does not work for super small exponents because of E notation
      return Double.parseDouble(exp);
    } catch (NumberFormatException e) {
    }

    // functions
    for (String function : functions) {
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
        exp = exp.substring(0, i) + FUNCTIONS.get(function).apply(evaluate(sub)) + exp.substring(j);
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
        int l = k;
        while (l < exp.length() && !isOp(exp.charAt(l))) {
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
        } while (i > 0 && !isOp(exp.charAt(i - 1))
            || i > 1 && !isOp(exp.charAt(i - 1)) && !isOp(exp.charAt(i - 2))); // does not work for negative bases
        Double base = evaluate(exp.substring(i, k));
        exp = exp.substring(0, i) + Math.pow(base, exponent) + exp.substring(j);
      } else if ("+-*/".indexOf(exp.charAt(i)) != -1 && i > 0 && exp.charAt(i - 1) != '^') {
        j = i;
      }
      i--;
    }

    return evalSimple(exp);
  }

  private static boolean isOp(char c) {
    return "+-*/^".indexOf(c) != -1;
  }

  // evaluate an expression with only +-*/
  public static double evalSimple(String exp) {
    double result = 0;
    int i = 0;
    int j = 0;
    boolean add = true;
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
    System.out.println(exp + " = " + result);
    return result;
  }

  public static double product(String exp) {
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
    System.out.println("p:" + exp + " = " + result);
    return result;
  }
}
